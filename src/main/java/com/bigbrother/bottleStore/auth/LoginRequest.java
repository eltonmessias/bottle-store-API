package com.bigbrother.bottleStore.auth;

public record LoginRequest(
        String username,
        String password
) {
}
