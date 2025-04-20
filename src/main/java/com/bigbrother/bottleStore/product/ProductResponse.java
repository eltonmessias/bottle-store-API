package com.bigbrother.bottleStore.product;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record ProductResponse(
        Long id,
        String productCode,
        String name,
        double sellingPrice,
        double purchasePrice,
        int quantity,
        ProductUnitType unitType,
        String categoryName,
        UUID categoryId
) {
}
