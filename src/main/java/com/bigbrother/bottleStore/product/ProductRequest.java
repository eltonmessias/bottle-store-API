package com.bigbrother.bottleStore.product;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.Set;
import java.util.UUID;

public record ProductRequest(
        @NotNull(message = "The name of the product is required")
        String name,
        UUID categoryId,
        @Min(value = 1, message = "the price must be higher or equal to 1")
        BigDecimal packPrice,
        BigDecimal boxPrice,
        BigDecimal bottlePrice,
        @Min(value = 1, message = "The quantity can't be negative")
        int quantity,
        int bottlesPerBox,
        int bottlesPerPack,
        Set<SaleUnit> saleUnits

) {

}
