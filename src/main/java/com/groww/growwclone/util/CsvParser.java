package com.groww.growwclone.util;

import com.groww.growwclone.entity.Stock;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@Component
public class CsvParser {

    public List<Stock> parseStockData(MultipartFile file) throws IOException {
        List<Stock> stocks = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()));
             CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withHeader())) {

            for (CSVRecord record : csvParser) {
                String stockName = record.get("SYMBOL");
                Long openPrice = Long.parseLong(record.get("OPEN"));
                Long closePrice = Long.parseLong(record.get("CLOSE"));
                Long lowPrice = Long.parseLong(record.get("LOW"));
                Long highPrice = Long.parseLong(record.get("HIGH"));
                Long settlementPrice = Long.parseLong(record.get("SETTLEMENT_PRICE"));

                Stock stock = Stock.builder()
                        .stock_name(stockName)
                        .open_price(openPrice)
                        .close_price(closePrice)
                        .low_price(lowPrice)
                        .high_price(highPrice)
                        .settlement_price(settlementPrice)
                        .build();
                stocks.add(stock);
            }
        }
        return stocks;
    }
}
