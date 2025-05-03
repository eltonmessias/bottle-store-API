package com.bigbrother.bottleStore.product;

import com.bigbrother.bottleStore.exceptions.BottleStoreException;
import com.bigbrother.bottleStore.exceptions.ProductNotFoundException;
import com.bigbrother.bottleStore.product.Category.CategoryRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductMapper mapper;

    private final ProductRepository productRepository;

    private final CategoryRepository categoryRepository;


    public List<ProductResponse> getAllProducts() {
        List<Product> products = productRepository.findAll();
        if (products.isEmpty()) {
            throw new ProductNotFoundException("Nenhum produto encontrado!");
        }
        return products.stream().map(mapper::toProductResponse).collect(Collectors.toList());
    }


    public ProductResponse createProduct(@Valid ProductRequest request) {
        try {
            var product = mapper.toProduct(request);
            productRepository.save(product);
            return mapper.toProductResponse(product);
        } catch (Exception e) {
            throw new BottleStoreException("Failed to create product");
        }
    }

    public ProductResponse getProductById(UUID id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException("Product not found"));
        return mapper.toProductResponse(product);

    }

//    public ProductResponse updateProduct(ProductRequest request, UUID productId) {
//        Product product = productRepository.findById(productId).orElseThrow(() -> new ProductNotFoundException("Product not found"));
//        product.setName(request.name());
//        product.setSellingPrice(request.sellingPrice());
//        product.setPurchasePrice(request.purchasePrice());
//        product.setStockQuantity(request.quantity());
//        productRepository.save(product);
//        return mapper.toProductResponse(product);
//    }

    public ProductResponse updateProductQuantity(UUID productId, int quantity) {
        Product product = productRepository.findById(productId).orElseThrow(() -> new ProductNotFoundException("Product not found"));
        product.setStockQuantity(product.getStockQuantity() + quantity);
        return mapper.toProductResponse(product);
    }

    public void deleteProduct(UUID productId) {
        Product product = productRepository.findById(productId).orElseThrow(() -> new ProductNotFoundException("Product not found"));
        productRepository.delete(product);
    }

}
