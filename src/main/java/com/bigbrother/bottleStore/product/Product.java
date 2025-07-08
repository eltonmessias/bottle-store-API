package com.bigbrother.bottleStore.product;


import com.bigbrother.bottleStore.product.Category.Category;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.Set;
import java.util.UUID;

@Getter @Setter
@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(unique = true, nullable = false)
    private String productCode;
    private String name;


    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
    private BigDecimal packPrice;
    private BigDecimal boxPrice;
    private BigDecimal unitPrice;
    private double stockQuantity;
    private int bottlesPerBox;
    private int bottlesPerPack;
    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    private Set<SaleUnit> saleUnits;


}
