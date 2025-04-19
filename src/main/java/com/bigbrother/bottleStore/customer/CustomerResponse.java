package com.bigbrother.bottleStore.customer;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record CustomerResponse(
        UUID id,
        String firstName,
        String lastName,
        String email,
        String phone
) {
}
