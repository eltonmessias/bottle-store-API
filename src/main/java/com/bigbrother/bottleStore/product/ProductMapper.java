package com.bigbrother.bottleStore.product;

import com.bigbrother.bottleStore.product.Category.Category;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductMapper {

    private final ProductRepository productRepository;

    private String generateProductCode() {
        String number;
        do {
            int random = (int) (Math.random() * 90000) + 10000;
            number = String.valueOf(random);
        } while (productRepository.existsByproductCode(number));
        return number;
    }

    public ProductResponse toProductResponse(Product product) {
        return new ProductResponse(
                product.getId(),
                product.getProductCode(),
                product.getName(),
                product.getCategory().getName(),
                product.getPackPrice(),
                product.getBoxPrice(),
                product.getUnitPrice(),
                (float) product.getStockQuantity(),
                product.getBottlesPerBox(),
                product.getBottlesPerPack(),
                product.getSaleUnits()
        );
    }

    public Product toProduct(@Valid ProductRequest request) {
        return Product.builder()
                .productCode(generateProductCode())
                .name(request.name())
                .packPrice(request.packPrice())
                .boxPrice(request.boxPrice())
                .unitPrice(request.unitPrice())
                .stockQuantity(request.quantity())
                .bottlesPerPack(request.bottlesPerPack())
                .bottlesPerBox(request.bottlesPerBox())
                .saleUnits(request.saleUnits())
                .category(
                        Category.builder()
                                .id(request.categoryId())
                                .build()
                )
                .build();
    }
}
