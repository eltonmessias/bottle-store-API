package com.bigbrother.bottleStore.sale.saleItem;

import com.bigbrother.bottleStore.product.Product;
import com.bigbrother.bottleStore.sale.Sale;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class SaleItemMapper {


    public SaleItem toSaleItem(Sale sale, Product product, SaleItemRequest item, BigDecimal subtotal) {
        return SaleItem.builder()
                .sale(sale)
                .product(product)
                .saleUnit(item.saleUnit())
                .quantity(item.quantity())
                .subtotal(subtotal)
                .build();
    }

    public SaleItemResponse toSaleItemResponse(SaleItem saleItem) {
        return new SaleItemResponse(
                saleItem.getId(),
                saleItem.getSale().getId(),
                saleItem.getProduct().getId(),
                saleItem.getQuantity(),
                saleItem.getUnitPrice(),
                saleItem.getSubtotal()
        );
    }
}
