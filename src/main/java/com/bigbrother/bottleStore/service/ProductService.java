package com.bigbrother.bottleStore.service;

import com.bigbrother.bottleStore.dto.ProductDTO;
import com.bigbrother.bottleStore.exceptions.BottleStoreException;
import com.bigbrother.bottleStore.exceptions.ProductNotFoundException;
import com.bigbrother.bottleStore.model.Product;
import com.bigbrother.bottleStore.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    private ProductDTO convertToProductDTO(Product product) {
        return new ProductDTO(
                product.getId(),
                product.getName(),
                product.getSellPrice(),
                product.getBuyPrice(),
                product.getQuantity(),
                product.getCategory()
        );
    }

    public List<ProductDTO> getAllProducts(){
        try {
            return  productRepository.findAll().stream().map(this::convertToProductDTO).collect(Collectors.toList());
        } catch (Exception e) {
            throw new ProductNotFoundException("Products not found");
        }
    }

    public ProductDTO createProduct(ProductDTO productDTO) {
        try {
            Product product = new Product();
            product.setName(productDTO.name());
            product.setSellPrice(productDTO.sellPrice());
            product.setBuyPrice(productDTO.buyPrice());
            product.setQuantity(productDTO.quantity());
            productRepository.save(product);
            return convertToProductDTO(product);
        } catch (Exception e) {
            throw new BottleStoreException("Failed to create product");
        }
    }

    public ProductDTO updateProduct(ProductDTO productDTO, Long productId) {
        Product product = productRepository.findById(productId).orElseThrow(() -> new ProductNotFoundException("Product not found"));
        product.setName(productDTO.name());
        product.setSellPrice(productDTO.sellPrice());
        product.setBuyPrice(productDTO.buyPrice());
        product.setQuantity(productDTO.quantity());
        productRepository.save(product);
        return convertToProductDTO(product);
    }

    public ProductDTO updateProductQuantity(Long productId, int quantity) {
        Product product = productRepository.findById(productId).orElseThrow(() -> new ProductNotFoundException("Product not found"));
        product.setQuantity(product.getQuantity() + quantity);
        return convertToProductDTO(product);
    }

    public void deleteProduct(Long productId) {
        Product product = productRepository.findById(productId).orElseThrow(() -> new ProductNotFoundException("Product not found"));
        productRepository.delete(product);
    }

}
