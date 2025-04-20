package com.bigbrother.bottleStore.sale;

import com.bigbrother.bottleStore.sale.payment.PaymentMethodUsedRequest;
import com.bigbrother.bottleStore.sale.payment.PaymentMethodUsedResponse;
import com.bigbrother.bottleStore.saleItem.SaleItemDTO;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Null;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record SaleRequest(
        long id,
        long sellerId,
        @JsonFormat(pattern = "dd/MM/YYY HH:mm")
        LocalDateTime saleDate,
        @Null
        UUID customerId,
        Double totalAmount,
        Double totalProfit,
        List<SaleItemDTO> items,
        List<PaymentMethodUsedRequest> paymentMethods
) {
}
