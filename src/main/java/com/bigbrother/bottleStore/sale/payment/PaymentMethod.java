package com.bigbrother.bottleStore.sale.payment;

import jakarta.persistence.*;
import lombok.Data;
import com.bigbrother.bottleStore.sale.Sale;

@Entity
@Data
public class PaymentMethod {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String paymentMethod;
    private double amount;
    @ManyToOne
    private Sale sale;


}
