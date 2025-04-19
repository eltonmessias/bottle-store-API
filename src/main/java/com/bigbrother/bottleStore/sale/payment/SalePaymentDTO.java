package com.bigbrother.bottleStore.sale.payment;

public record SalePaymentDTO(
        long id,
        long saleId,
        String type,
        double amount
) {
}
