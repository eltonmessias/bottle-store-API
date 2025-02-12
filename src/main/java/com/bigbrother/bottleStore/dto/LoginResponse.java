package com.bigbrother.bottleStore.dto;

public record LoginResponse(String accessToken, String refreshToken, String message) {
}
