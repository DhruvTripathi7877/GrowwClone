package com.groww.growwclone.service;

import com.groww.growwclone.dto.StockDTO;
import com.groww.growwclone.entity.Stock;

public interface StockService {
    Stock findStock(StockDTO stock_dto);
}
