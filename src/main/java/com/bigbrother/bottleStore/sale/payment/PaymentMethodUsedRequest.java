package com.bigbrother.bottleStore.sale.payment;

public record PaymentMethodUsedRequest(
        long id,
        long saleId,
        String type,
        double amount
) {
}
