package com.bigbrother.bottleStore.auth;

public record AuthRequest(
        String username,
        String password
) {
}
