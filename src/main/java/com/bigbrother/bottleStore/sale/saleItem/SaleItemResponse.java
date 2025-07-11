package com.bigbrother.bottleStore.sale.saleItem;

import java.math.BigDecimal;
import java.util.UUID;

public record SaleItemResponse(
        UUID id,
        UUID saleId,
        UUID productId,
        int quantity,
        BigDecimal unitPrice,
        BigDecimal totalPrice
) {
}
