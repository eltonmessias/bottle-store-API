package com.bigbrother.bottleStore.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class SalePayment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    private Sale sale;

    private String paymentMethod;
    private double amount;
}
