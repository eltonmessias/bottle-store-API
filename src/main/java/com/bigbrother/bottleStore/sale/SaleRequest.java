package com.bigbrother.bottleStore.sale;

import com.bigbrother.bottleStore.sale.payment.PaymentMethodUsedRequest;
import com.bigbrother.bottleStore.sale.saleItem.SaleItemRequest;
import jakarta.validation.constraints.Null;

import java.util.List;
import java.util.UUID;

public record SaleRequest(
        UUID id,
        UUID sellerId,
        @Null
        UUID customerId,
        List<SaleItemRequest> items,
        List<PaymentMethodUsedRequest> paymentMethods
) {
}
