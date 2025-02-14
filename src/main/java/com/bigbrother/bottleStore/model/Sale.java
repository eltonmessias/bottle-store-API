package com.bigbrother.bottleStore.model;

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
