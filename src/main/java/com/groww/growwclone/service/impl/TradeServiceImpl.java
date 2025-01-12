package com.groww.growwclone.service.impl;

import com.groww.growwclone.dto.TradeDTO;
import com.groww.growwclone.entity.Holding;
import com.groww.growwclone.entity.Stock;
import com.groww.growwclone.repository.HoldingRepository;
import com.groww.growwclone.repository.StockRepository;
import com.groww.growwclone.service.TradeService;
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

//    @Override
//    @Transactional
//    public void createTransaction(CreateTransactionRequest request) {
//        Transaction transaction = Transaction.builder()
//                .date(request.getDate())
//                .transactionType(request.getTransactionType())
//                .category(request.getCategory())
//                .amount(request.getAmount())
//                .build();
//        transactionRepository.save(transaction);
//    }

    @Override
    public void executeTrade(TradeDTO trade_dto)
    {
        Long stock_id = trade_dto.getStock_id();
        Optional<Stock> stock = stockRepository.findById(stock_id);

        if (stock.isPresent())
        {
            log.info("Stock Found");
        }
        else
        {
            log.info("Stock Not Found");
        }
    }
}
