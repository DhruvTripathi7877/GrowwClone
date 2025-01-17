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
    public void executeTrade(TradeDTO tradeDTO)
    {
        try {
            Long stockId = tradeDTO.getStockId();
            Optional<Stock> stock = stockRepository.findById(stockId);

            if (tradeDTO.getQuantity() <= 0) {
                log.info("Quantity must be greater than 0");
                throw new Exception("Quantity must be greater than 0");
            }

            if (stock.isPresent()) {
                Stock stockEntity = stock.get();

                Long userId = tradeDTO.getUserId();
                Optional<User> user = userRepository.findById(userId);

                User userEntity;

                if (user.isPresent()) {
                    userEntity = user.get();
                } else {
                    userEntity = new User();
//                    userEntity.setUserId(userId);
                    userRepository.save(userEntity);
                    log.info("New User Created with ID: {}", userId);
                }


                if (tradeDTO.getTrade_type() == TradeDTO.TradeType.BUY) {
                    buyStock(stockEntity,tradeDTO,userEntity);
                }
                else if(tradeDTO.getTrade_type()== TradeDTO.TradeType.SELL)
                {
                    sellStock(userEntity,stockEntity,tradeDTO);
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

    private void buyStock(Stock stockEntity,TradeDTO tradeDTO,User userEntity)
    {
        Long userId=userEntity.getUserId();
        Long stockId=stockEntity.getStockId();
        Long gain_loss = stockEntity.getClosePrice() - stockEntity.getOpenPrice();

        Holding holding = Holding.builder()
                .stockName(stockEntity.getStockName())
                .stockId(stockEntity.getStockId())
                .quantity(tradeDTO.getQuantity())
                .buyPrice(stockEntity.getOpenPrice())
                .currentPrice(stockEntity.getClosePrice())
                .gainLoss(gain_loss)
                .user(userEntity)
                .build();

        holdingRepository.save(holding);
        log.info("Holding Save for Stock: {}", stockEntity.getStockName());

        Long quantity = tradeDTO.getQuantity();
        TradeHistory tradeHistory = TradeHistory.builder()
                .userId(userId)
                .stockId(stockId)
                .quantity(quantity)
                .sellPrice(stockEntity.getClosePrice())
                .sellTimestamp(LocalDateTime.now())
                .build();

        tradeHistoryRepository.save(tradeHistory);
    }

    private void sellStock(User user,Stock stock,TradeDTO tradeDTO) throws IllegalAccessException {
        Long userId=user.getUserId();
        Long stockId=stock.getStockId();
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
                .sellPrice(stock.getClosePrice())
                .sellTimestamp(LocalDateTime.now())
                .build();

        tradeHistoryRepository.save(tradeHistory);
    }
}
