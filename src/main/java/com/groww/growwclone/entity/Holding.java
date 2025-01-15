package com.groww.growwclone.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class Holding {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long holdingId;

    private String stockName;
    private Long stockId;
    private Long quantity;
    private Long buyPrice;
    private Long currentPrice;
    private Long gainLoss;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
