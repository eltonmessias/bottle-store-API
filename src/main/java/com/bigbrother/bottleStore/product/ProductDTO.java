package com.bigbrother.bottleStore.product;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record ProductDTO(
        Long id,
        @NotNull(message = "The name of the product is required")
        String name,
        @Min(value = 1, message = "the price must be higher or equal to 1")
        double sellingPrice,
        double purchasePrice,
        @Min(value = 1, message = "The quantity can't be negative")
        int quantity,
        ProductUnitType unitType,
        String categoryName,
        Long categoryId
) {
}
