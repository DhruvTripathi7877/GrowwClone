package com.groww.growwclone.service.impl;

import com.groww.growwclone.dto.StockDTO;
import com.groww.growwclone.entity.Stock;
import com.groww.growwclone.repository.StockRepository;
import com.groww.growwclone.service.StockService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class StockServiceImpl implements StockService {
    private final StockRepository stockRepository;

    @Override
    public Stock findStock(StockDTO stock_dto) {
        Long stock_id=stock_dto.getStock_id();
        Optional<Stock> stock = stockRepository.findById(stock_id);

        if(stock.isPresent())
        {
            Stock stockEntity=stock.get();
            log.info("Stock is Present");
            return stockEntity;
        }
        else
        {
            log.info("Stock is not Present");

            return null;
        }
    }
}
