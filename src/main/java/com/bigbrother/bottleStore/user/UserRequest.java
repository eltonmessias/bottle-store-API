package com.bigbrother.bottleStore.user;

import lombok.RequiredArgsConstructor;

import java.util.UUID;


public record UserRequest(
        UUID id,
        String username,
        String fullName,
        String email,
        String phone,
        ROLE role
) {
}
