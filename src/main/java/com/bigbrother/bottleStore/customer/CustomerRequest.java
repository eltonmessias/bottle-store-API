package com.bigbrother.bottleStore.customer;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record CustomerRequest(
        UUID id,
        @NotNull(message = "Customer firstname is required")
        String firstName,
        @NotNull(message = "Customer lastname is required")
        String lastName,
        String email,
        String phone
) {
}
