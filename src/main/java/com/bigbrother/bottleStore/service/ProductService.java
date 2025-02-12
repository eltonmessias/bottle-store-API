package com.bigbrother.bottleStore.service;

import com.bigbrother.bottleStore.dto.ProductDTO;
import com.bigbrother.bottleStore.exceptions.BottleStoreException;
import com.bigbrother.bottleStore.exceptions.CategoryNotFoundException;
import com.bigbrother.bottleStore.exceptions.ProductNotFoundException;
import com.bigbrother.bottleStore.model.Category;
import com.bigbrother.bottleStore.model.Product;
import com.bigbrother.bottleStore.repository.CategoryRepository;
import com.bigbrother.bottleStore.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryRepository categoryRepository;

    private ProductDTO convertToProductDTO(Product product) {

        return new ProductDTO(
                product.getId(),
                product.getName(),
                product.getSellingPrice(),
                product.getPurchasePrice(),
                product.getStockQuantity(),
                product.getUnitType(),
                product.getCategory().getName(),
                product.getCategory().getId()
        );
    }

    public List<ProductDTO> getAllProducts() {
        List<Product> products = productRepository.findAll();
        if (products.isEmpty()) {
            throw new ProductNotFoundException("Nenhum produto encontrado!");
        }
        return products.stream().map(this::convertToProductDTO).collect(Collectors.toList());
    }

    public ProductDTO createProduct(ProductDTO productDTO) {
        try {
            Category category = categoryRepository.findById(productDTO.categoryId()).orElseThrow(() -> new CategoryNotFoundException("Category not found"));
            Product product = new Product();
            product.setName(productDTO.name());
            product.setSellingPrice(productDTO.sellingPrice());
            product.setPurchasePrice(productDTO.purchasePrice());
            product.setStockQuantity(productDTO.stockQuantity());
            product.setCategory(category);
            productRepository.save(product);
            return convertToProductDTO(product);
        } catch (Exception e) {
            throw new BottleStoreException("Failed to create product");
        }
    }

    public ProductDTO updateProduct(ProductDTO productDTO, Long productId) {
        Product product = productRepository.findById(productId).orElseThrow(() -> new ProductNotFoundException("Product not found"));
        product.setName(productDTO.name());
        product.setSellingPrice(productDTO.sellingPrice());
        product.setPurchasePrice(productDTO.purchasePrice());
        product.setStockQuantity(productDTO.stockQuantity());
        productRepository.save(product);
        return convertToProductDTO(product);
    }

    public ProductDTO updateProductQuantity(Long productId, int quantity) {
        Product product = productRepository.findById(productId).orElseThrow(() -> new ProductNotFoundException("Product not found"));
        product.setStockQuantity(product.getStockQuantity() + quantity);
        return convertToProductDTO(product);
    }

    public void deleteProduct(Long productId) {
        Product product = productRepository.findById(productId).orElseThrow(() -> new ProductNotFoundException("Product not found"));
        productRepository.delete(product);
    }

}
