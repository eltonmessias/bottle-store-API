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


    @PrePersist
    @PreUpdate
    public void calculateTotalPrice() {
        this.profit = calculateProfit();  // Calcula o lucro antes de usá-lo
        this.totalPrice = this.unitPrice * this.quantity;
    }



    public double calculateProfit() {
        double costPrice = product.getPurchasePrice();
        return quantity * (unitPrice - costPrice);
    }

}
