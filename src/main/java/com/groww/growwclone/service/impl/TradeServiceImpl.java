package com.groww.growwclone.service.impl;

import com.groww.growwclone.dto.TradeDTO;
import com.groww.growwclone.entity.Holding;
import com.groww.growwclone.entity.Stock;
import com.groww.growwclone.entity.TradeHistory;
import com.groww.growwclone.entity.User;
import com.groww.growwclone.repository.HoldingRepository;
import com.groww.growwclone.repository.StockRepository;
import com.groww.growwclone.repository.TradeHistoryRepository;
import com.groww.growwclone.repository.UserRepository;
import com.groww.growwclone.service.TradeService;
import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@Slf4j
@Service
@RequiredArgsConstructor
public class TradeServiceImpl implements TradeService {
    private final HoldingRepository holdingRepository;
    private final StockRepository stockRepository;
    private final UserRepository userRepository;
    private final TradeHistoryRepository tradeHistoryRepository;

    @Override
    @Transactional
    public void executeTrade(TradeDTO trade_dto)
    {
        try {
            Long stock_id = trade_dto.getStock_id();
            Optional<Stock> stock = stockRepository.findById(stock_id);

            if (trade_dto.getQuantity() <= 0) {
                log.info("Quantity must be greater than 0");
                throw new Exception("Quantity must be greater than 0");
            }

            if (stock.isPresent()) {
                Stock stockEntity = stock.get();

                Long user_id = trade_dto.getUser_id();
                Optional<User> user = userRepository.findById(user_id);

                User userEntity;

                if (user.isPresent()) {
                    userEntity = user.get();
                } else {
                    userEntity = new User();
                    userEntity.setUser_id(user_id);
                    userRepository.save(userEntity);
                    log.info("New User Created with ID: {}", user_id);
                }


                if (trade_dto.getTrade_type() == TradeDTO.TradeType.BUY) {
                    buyStock(stockEntity,trade_dto,userEntity);
                }
                else if(trade_dto.getTrade_type()== TradeDTO.TradeType.SELL)
                {

                }
                else
                {
                    log.info("Invalid Trade Type");
                    throw new IllegalAccessException("Invalid Trade Type");
                }

            } else {
                log.info("Stock Not Found");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void buyStock(Stock stockEntity,TradeDTO trade_dto,User userEntity)
    {
        Long userId=userEntity.getUser_id();
        Long stockId=stockEntity.getStock_id();
        Long gain_loss = stockEntity.getClose_price() - stockEntity.getOpen_price();

        Holding holding = Holding.builder()
                .stock_name(stockEntity.getStock_name())
                .stock_id(stockEntity.getStock_id())
                .quantity(trade_dto.getQuantity())
                .buy_price(stockEntity.getOpen_price())
                .current_price(stockEntity.getClose_price())
                .gain_loss(gain_loss)
                .user(userEntity)
                .build();

        holdingRepository.save(holding);
        log.info("Holding Save for Stock: {}", stockEntity.getStock_name());

        Long quantity = trade_dto.getQuantity();
        TradeHistory tradeHistory = TradeHistory.builder()
                .userId(userId)
                .stockId(stockId)
                .quantity(quantity)
                .sellPrice(stockEntity.getClose_price())
                .sellTimestamp(LocalDateTime.now())
                .build();

        tradeHistoryRepository.save(tradeHistory);
    }

    private void sellStock(User user,Stock stock,TradeDTO tradeDTO) throws IllegalAccessException {
        Long userId=user.getUser_id();
        Long stockId=stock.getStock_id();
        Long remainingQuantity=tradeDTO.getQuantity();

        List<Holding> holdings = holdingRepository.findAllHoldingsForStock(userId, stockId);

        Long totalAvailableQuantity = holdings.stream().mapToLong(Holding::getQuantity).sum();

        if(totalAvailableQuantity<remainingQuantity)
        {
            throw new IllegalAccessException("Insufficient stock to sell");
        }

        for(Holding holding:holdings)
        {
            if(remainingQuantity==null)
                break;

            Long availableQuantity=holding.getQuantity();

            if(availableQuantity>=remainingQuantity)
            {
                holding.setQuantity(availableQuantity-remainingQuantity);
                remainingQuantity=null;
            }
            else
            {
                remainingQuantity-=availableQuantity;
                holding.setQuantity(null);
            }

            holdingRepository.save(holding);
        }

        // Remove holdings with zero quantity
        holdings.stream()
                .filter(holding -> holding.getQuantity() == 0)
                .forEach(holdingRepository::delete);

        TradeHistory tradeHistory = TradeHistory.builder()
                .userId(userId)
                .stockId(stockId)
                .quantity(remainingQuantity)
                .sellPrice(stock.getClose_price())
                .sellTimestamp(LocalDateTime.now())
                .build();

        tradeHistoryRepository.save(tradeHistory);
    }
}
