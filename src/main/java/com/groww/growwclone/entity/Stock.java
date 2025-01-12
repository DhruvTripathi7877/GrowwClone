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
    private Long stock_id;

    private String stock_name;
    private Long open_price;
    private Long close_price;
    private Long low_price;
    private Long high_price;
    private Long settlement_price;
}
