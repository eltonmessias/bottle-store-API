package com.bigbrother.bottleStore.product.Category;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record CategoryResponse(
        UUID id,
        String name,
        String description
) {
}
