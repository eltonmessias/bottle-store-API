package com.bigbrother.bottleStore.product;


import com.bigbrother.bottleStore.Category.Category;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Entity
@Data
@Builder
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(unique = true, nullable = false)
    private String productCode;
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
