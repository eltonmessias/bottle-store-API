package com.bigbrother.bottleStore.auth;

public record LoginResponse(String accessToken, String refreshToken, String message) {
}
