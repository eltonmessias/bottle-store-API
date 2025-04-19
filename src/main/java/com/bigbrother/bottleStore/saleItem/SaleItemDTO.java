package com.bigbrother.bottleStore.saleItem;

public record SaleItemDTO(
        long id,
        long saleId,
        long productId,
        int quantity,
        Double unitPrice,
        Double totalPrice,
        Double profit

) {
}
