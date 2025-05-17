package com.bigbrother.bottleStore.sale;

import com.bigbrother.bottleStore.customer.Customer;
import com.bigbrother.bottleStore.sale.saleItem.SaleItem;
import com.bigbrother.bottleStore.sale.payment.PaymentMethod;
import com.bigbrother.bottleStore.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Getter @Setter
@Data
@Entity
public class Sale {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(unique = true, nullable = false)
    private String saleCode;

    @ManyToOne
    private User seller;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = true)
    private Customer customer;

    @OneToMany(mappedBy = "sale", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PaymentMethod> paymentMethods = new ArrayList<>();

    private LocalDateTime saleDate;
    private BigDecimal totalAmount;
    private Double totalProfit;

    @OneToMany(mappedBy = "sale", cascade = CascadeType.ALL)
    private List<SaleItem> products = new ArrayList<>();

    @PrePersist
    public void prePersist(){
        if(saleDate == null){
            saleDate = LocalDateTime.now();
        }
    }

}
