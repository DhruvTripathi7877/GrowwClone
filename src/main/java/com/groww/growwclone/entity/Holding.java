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
    private String stock_id;
    private Long quantity;
    private Long buy_price;
    private Long current_price;
    private Double gainNloss;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false) // Foreign key column
    private User user;
}
