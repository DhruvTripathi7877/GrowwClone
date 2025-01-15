package com.groww.growwclone.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TradeHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long userId;
    private Long stockId;
    private Long quantity;
    private Long sellPrice;
    private LocalDateTime sellTimestamp;
}
