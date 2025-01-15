package com.groww.growwclone.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class TradeDTO {
    @NotNull(message="User Id should not be Not Null")
    private Long user_id;

    private TradeType trade_type;
    private Long quantity;
    private Long stock_id;

    public enum TradeType{
        BUY,
        SELL
    }
}
