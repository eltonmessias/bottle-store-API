package com.bigbrother.bottleStore.product.Category;

import jakarta.validation.constraints.NotNull;

public record CategoryRequest(
        Long id,
        @NotNull(message = "The name is missing")
        String name,
        String description
) {
}
