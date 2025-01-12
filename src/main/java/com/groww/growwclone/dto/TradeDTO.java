package com.groww.growwclone.dto;

import lombok.Data;

@Data
public class TradeDTO {
    private Long user_id;
    private Character trade_type;
    private Long quantity;
    private Long stock_id;
}
