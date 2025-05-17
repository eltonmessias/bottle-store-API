package com.bigbrother.bottleStore.product;

import com.bigbrother.bottleStore.product.Category.Category;
import com.bigbrother.bottleStore.product.Category.CategoryRepository;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private EntityManager entityManager;
    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    @DisplayName("Should create product")
    void saveProduct() {
        Category category = new Category();
        category.setName("Whisky");
        categoryRepository.save(category);


        ProductRequest request = new ProductRequest(
                "Celler",
                category.getId(),
                new BigDecimal(500),
                new BigDecimal(2000),
                new BigDecimal(100),
                200,
                24,
                6,
                Set.of(SaleUnit.BOX, SaleUnit.PACK)
        );


        Product savedProduct = createProduct(request);

        // Testar
        assertNotNull(savedProduct.getId());
    }


    @Test
    void existsByproductCode() {
    }

    private Product createProduct(ProductRequest request) {
        Product newProduct = new Product();
        newProduct.setProductCode("55514"); // ou gere automaticamente se necessário
        newProduct.setPackPrice(request.packPrice());
        newProduct.setBoxPrice(request.boxPrice());
        newProduct.setBottlePrice(request.bottlePrice());
        newProduct.setStockQuantity(request.quantity());
        newProduct.setBottlesPerBox(request.bottlesPerBox());
        newProduct.setBottlesPerPack(request.bottlesPerPack());
        newProduct.setSaleUnits(request.saleUnits());

        Category category = categoryRepository.findById(request.categoryId())
                .orElseThrow(() -> new RuntimeException("Categoria não encontrada"));
        newProduct.setCategory(category);

        return productRepository.save(newProduct);
    }
}