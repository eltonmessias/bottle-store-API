package com.bigbrother.bottleStore.product;

import com.bigbrother.bottleStore.Category.Category;
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
                product.getSellingPrice(),
                product.getPurchasePrice(),
                product.getStockQuantity(),
                product.getUnitType(),
                product.getCategory().getName(),
                product.getCategory().getId()
        );
    }

    public Product toProduct(@Valid ProductRequest request) {
        return Product.builder()
                .productCode(generateProductCode())
                .name(request.name())
                .sellingPrice(request.sellingPrice())
                .purchasePrice(request.purchasePrice())
                .stockQuantity(request.quantity())
                .unitType(request.unitType())
                .category(
                        Category.builder()
                                .id(request.categoryId())
                                .build()
                )
                .build();
    }
}
