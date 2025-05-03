package com.bigbrother.bottleStore.product;

import java.math.BigDecimal;
import java.util.Set;
import java.util.UUID;

public record ProductResponse(
        UUID id,
        String productCode,
        String name,
        String categoryName,
        BigDecimal packPrice,
        BigDecimal boxPrice,
        BigDecimal bottlePrice,
        float quantity,
        int bottlesPerBox,
        int bottlesPerPack,
        Set<SaleUnit> saleUnits

) {
}
