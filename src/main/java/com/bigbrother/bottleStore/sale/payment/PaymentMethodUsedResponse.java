package com.bigbrother.bottleStore.sale.payment;

public record PaymentMethodUsedResponse(
        String type,
        double amount
) {
}
