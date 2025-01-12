package com.groww.growwclone.service.impl;

import com.groww.growwclone.dto.TradeDTO;
import com.groww.growwclone.entity.Holding;
import com.groww.growwclone.entity.Stock;
import com.groww.growwclone.entity.User;
import com.groww.growwclone.repository.HoldingRepository;
import com.groww.growwclone.repository.StockRepository;
import com.groww.growwclone.repository.UserRepository;
import com.groww.growwclone.service.TradeService;
import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Slf4j
@Service
@RequiredArgsConstructor
public class TradeServiceImpl implements TradeService {
    private final HoldingRepository holdingRepository;
    private final StockRepository stockRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public void executeTrade(TradeDTO trade_dto)
    {
        Long stock_id = trade_dto.getStock_id();
        Optional<Stock> stock = stockRepository.findById(stock_id);
        if (stock.isPresent())
        {
            Stock stockEntity = stock.get();
            Long g_l = stockEntity.getClose_price() - stockEntity.getOpen_price();

            Long user_id = trade_dto.getUser_id();
            Optional<User> user = userRepository.findById(user_id);

            User userEntity;

            if (user.isPresent())
            {
                userEntity=user.get();
            }
            else{
                userEntity = new User();
                userEntity.setUser_id(user_id);
                userRepository.save(userEntity);
                log.info("New User Created with ID: {}",user_id);
            }

            Holding holding = Holding.builder()
                    .stock_name(stockEntity.getStock_name())
                    .stock_id(stockEntity.getStock_id())
                    .quantity(trade_dto.getQuantity())
                    .buy_price(stockEntity.getOpen_price())
                    .current_price(stockEntity.getClose_price())
                    .gain_loss(g_l)
                    .user(userEntity)
                    .build();

            holdingRepository.save(holding);
            log.info("Holding Save for Stock: {}",stockEntity.getStock_name());
        }
        else
        {
            log.info("Stock Not Found");
        }
    }
}
