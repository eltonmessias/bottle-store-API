package com.bigbrother.bottleStore.user;

import java.util.UUID;

public record UserResponse(
        UUID id,
        String username,
        String fullName,
        String email,
        String phone,
        String role
) {
}
