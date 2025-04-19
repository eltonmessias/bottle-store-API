package com.bigbrother.bottleStore.sale;

import com.bigbrother.bottleStore.saleItem.SaleItemDTO;
import com.bigbrother.bottleStore.sale.payment.SalePaymentDTO;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;
import java.util.List;

public record SaleDTO(
        long id,
        long sellerId,
        @JsonFormat(pattern = "dd/MM/YYY HH:mm")
        LocalDateTime saleDate,
        long customerId,
        Double totalAmount,
        Double totalProfit,
        List<SaleItemDTO> items,
        List<SalePaymentDTO> payments

) {
}
