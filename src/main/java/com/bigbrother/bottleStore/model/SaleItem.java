package com.bigbrother.bottleStore.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class SaleItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    private Sale sale;

    @ManyToOne
    private Product product;

    private Integer quantity;
    private Double unitPrice;
    private Double totalPrice;
    private Double profit;

}
