package com.bigbrother.bottleStore.sale.saleItem;

import com.bigbrother.bottleStore.product.SaleUnit;

import java.util.UUID;

public record SaleItemRequest(
        UUID saleId,
        UUID productId,
        SaleUnit saleUnit,
        int quantity
) {
}
