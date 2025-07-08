package com.bigbrother.bottleStore.sale;

import com.bigbrother.bottleStore.sale.payment.PaymentMethodUsedResponse;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record SaleResponse(
        UUID id,
        String saleCode,
        UUID sellerId,
        @JsonFormat(pattern = "dd/MM/YYY HH:mm")
        LocalDateTime saleDate,
        String customerName,
        BigDecimal totalAmount,
        List<PaymentMethodUsedResponse> payments
) {
}
