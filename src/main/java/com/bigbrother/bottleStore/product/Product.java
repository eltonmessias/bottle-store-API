package com.bigbrother.bottleStore.product;


import com.bigbrother.bottleStore.Category.Category;
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
    @Enumerated(EnumType.STRING)
    private ProductUnitType unitType;
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;


}
