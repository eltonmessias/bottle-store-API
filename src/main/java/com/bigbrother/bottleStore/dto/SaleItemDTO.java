package com.bigbrother.bottleStore.dto;

import com.bigbrother.bottleStore.model.Product;

public record SaleItemDTO(
        long id,
        long saleId,
        Product product,
        long productId,
        int quantity,
        Double unitPrice,
        Double totalPrice,
        Double profit

) {
}
