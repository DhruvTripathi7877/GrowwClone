package com.groww.growwclone.controller;


import com.groww.growwclone.dto.StockDTO;
import com.groww.growwclone.entity.Stock;
import com.groww.growwclone.service.StockService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/stock")
public class StockController {
    private final StockService stockService;

    @PostMapping(value="/details",consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Stock> findStock(@RequestBody StockDTO stock_dto)
    {
        Stock stock = stockService.findStock(stock_dto);
        return ResponseEntity.ok(stock);
    }
}
