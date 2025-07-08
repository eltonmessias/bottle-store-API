package com.bigbrother.bottleStore.product.Category;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record CategoryRequest(
        UUID id,
        @NotNull(message = "The name is missing")
        String name,
        String description
) {
}
