package com.bigbrother.bottleStore.dto;

import java.time.LocalDateTime;
import java.util.List;

public record SaleDTO(
        long id,
        long sellerId,
        LocalDateTime saleDate,
        Double totalAmount,
        Double totalProfit,
        List<SaleItemDTO> items

) {
}
