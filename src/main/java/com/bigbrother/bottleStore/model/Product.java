package com.bigbrother.bottleStore.model;


import com.bigbrother.bottleStore.enums.ProductUnitType;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Entity
@Data
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private double sellingPrice;
    private double purchasePrice;
    private int stockQuantity;
    private ProductUnitType unitType;
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;


}
