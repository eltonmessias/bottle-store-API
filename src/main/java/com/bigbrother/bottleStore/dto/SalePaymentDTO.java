package com.bigbrother.bottleStore.dto;

public record SalePaymentDTO(
        long id,
        long saleId,
        String type,
        double amount
) {
}
