package com.groww.growwclone.controller;

import com.groww.growwclone.dto.TradeDTO;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/trade")
public class TradeController {

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> trade(@RequestBody TradeDTO trade_dto)
    {
        
    }
}
