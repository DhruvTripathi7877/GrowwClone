package com.groww.growwclone.controller;

import com.groww.growwclone.service.UpdateService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/stocks")
public class UpdateController {

    private final UpdateService updateService;

    @PostMapping("/update")
    public ResponseEntity<String> updateStocks(@RequestParam("file") MultipartFile file) {
        try {
            updateService.updateStocksFromCsv(file);
            return ResponseEntity.ok("Stocks updated successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("File upload failed: " + e.getMessage());
        }
    }
}