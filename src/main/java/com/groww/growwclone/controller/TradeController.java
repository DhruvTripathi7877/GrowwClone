package com.groww.growwclone.controller;

import com.groww.growwclone.dto.TradeDTO;
import com.groww.growwclone.service.TradeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static java.rmi.server.LogStream.log;

@Slf4j
@RestController
@RequestMapping("/trade")
@RequiredArgsConstructor
public class TradeController {
    private final TradeService tradeService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> trade( @RequestBody TradeDTO trade_dto)
    {
        log.info("Trade is getting started");
        tradeService.executeTrade(trade_dto);
        log.info("Trade finished");

        return ResponseEntity.ok("Trade executed successfully");
    }
}
