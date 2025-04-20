package com.bigbrother.bottleStore.product;


import com.bigbrother.bottleStore.product.Category.Category;
import jakarta.persistence.*;
import lombok.*;

@Getter @Setter
@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
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
