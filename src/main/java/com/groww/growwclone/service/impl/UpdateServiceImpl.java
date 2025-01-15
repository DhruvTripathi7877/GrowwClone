package com.groww.growwclone.service.impl;

import com.groww.growwclone.entity.Stock;
import com.groww.growwclone.repository.StockRepository;
import com.groww.growwclone.service.UpdateService;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

@Slf4j
@Service
@RequiredArgsConstructor
public class UpdateServiceImpl implements UpdateService {

    private final StockRepository stockRepository;

    @Override
    public void updateStocksFromCsv(MultipartFile file) {
        try (CSVReader csvReader = new CSVReader(new InputStreamReader(file.getInputStream()))) {
            String[] nextLine;

            csvReader.readNext();

            while ((nextLine = csvReader.readNext()) != null) {
                if (nextLine.length < 7) {
                    log.warn("Skipping invalid row with insufficient columns: {}", Arrays.toString(nextLine));
                    continue;
                }

                try {
                    // Clean and parse values before saving them
                    String security = nextLine[1].trim();
                    long openPrice = scaleToLong(parseCsvValue(nextLine[3]));
                    long highPrice = scaleToLong(parseCsvValue(nextLine[4]));
                    long lowPrice = scaleToLong(parseCsvValue(nextLine[5]));
                    long closePrice = scaleToLong(parseCsvValue(nextLine[6]));

                    log.info("Security: {}", security);
                    log.info("Open price: {}", openPrice);
                    log.info("High Price: {}", highPrice);
                    log.info("Low Price: {}", lowPrice);
                    log.info("Close Price: {}", closePrice);

                    // Build and save the Stock entity
                    Stock stock = Stock.builder()
                            .stockName(security)
                            .openPrice(openPrice)
                            .highPrice(highPrice)
                            .lowPrice(lowPrice)
                            .closePrice(closePrice)
                            .settlementPrice(closePrice)
                            .build();

                    stockRepository.save(stock);

                } catch (NumberFormatException e) {
                    log.error("Error processing row: " + Arrays.toString(nextLine), e);
                }
            }
        } catch (IOException | CsvValidationException e) {
            log.error("Failed to process CSV file", e);
            throw new RuntimeException("Failed to process CSV file", e);
        }
    }

    private String parseCsvValue(String value) {
        return value.trim().replaceAll(",", "");
    }

    private long scaleToLong(String value) {
        try {
            return Math.round(Double.parseDouble(value) * 100);
        } catch (NumberFormatException e) {
            return 0L;
        }
    }
}
