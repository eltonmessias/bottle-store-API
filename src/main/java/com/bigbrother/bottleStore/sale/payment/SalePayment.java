package com.bigbrother.bottleStore.sale.payment;

import jakarta.persistence.*;
import lombok.Data;
import com.bigbrother.bottleStore.sale.Sale;

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
