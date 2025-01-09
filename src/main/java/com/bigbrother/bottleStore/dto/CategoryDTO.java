package com.bigbrother.bottleStore.dto;

import jakarta.validation.constraints.NotNull;

public record CategoryDTO(
        Long id,
        @NotNull(message = "The name is missing")
        String name,
        String description
) {
}
