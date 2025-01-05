package com.bigbrother.bottleStore.dto;

public record ProductDTO(
        Long id,
        String name,
        double price,
        int quantity
) {
}
