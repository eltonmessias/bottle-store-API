package com.bigbrother.bottleStore.sale;

import com.bigbrother.bottleStore.customer.Customer;
import com.bigbrother.bottleStore.saleItem.SaleItem;
import com.bigbrother.bottleStore.sale.payment.SalePayment;
import com.bigbrother.bottleStore.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Getter @Setter
@Data
@Entity
public class Sale {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    private User seller;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @OneToMany(mappedBy = "sale", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SalePayment> payments = new ArrayList<>();

    private LocalDateTime saleDate;
    private Double totalAmount;
    private Double totalProfit;

    @OneToMany(mappedBy = "sale", cascade = CascadeType.ALL)
    private List<SaleItem> products = new ArrayList<>();

    @PrePersist
    public void prePersist(){
        if(saleDate == null){
            saleDate = LocalDateTime.now();
        }
    }



    @PreUpdate
    public void updateTotals() {
        totalAmount = products.stream().mapToDouble(SaleItem::getTotalPrice).sum();

        totalProfit = products.stream().mapToDouble(SaleItem::calculateProfit).sum();
    }
}
