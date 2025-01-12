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

    private String stock_name;
    private Long stock_id;
    private Long quantity;
    private Long buy_price;
    private Long current_price;
    private Long gain_loss;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false) // Foreign key column
    private User user;
}
