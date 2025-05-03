package com.bigbrother.bottleStore.saleItem;

import com.bigbrother.bottleStore.product.Product;
import com.bigbrother.bottleStore.product.SaleUnit;
import jakarta.persistence.*;
import lombok.*;
import com.bigbrother.bottleStore.sale.Sale;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SaleItem {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    private Sale sale;

    @ManyToOne
    private Product product;

    @Enumerated(EnumType.STRING)
    private SaleUnit saleUnit;
    private Integer quantity;
    private BigDecimal unitPrice;
    private BigDecimal subtotal;



}
