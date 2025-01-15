package com.groww.growwclone.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Builder(toBuilder = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Stock {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long stockId;
    private String stockName;
    private Long openPrice;
    private Long closePrice;
    private Long lowPrice;
    private Long highPrice;
    private Long settlementPrice;
}
