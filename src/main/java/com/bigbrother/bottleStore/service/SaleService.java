package com.bigbrother.bottleStore.service;


import com.bigbrother.bottleStore.dto.ProductDTO;
import com.bigbrother.bottleStore.dto.SaleDTO;
import com.bigbrother.bottleStore.dto.SaleItemDTO;
import com.bigbrother.bottleStore.exceptions.ProductNotFoundException;
import com.bigbrother.bottleStore.exceptions.SaleNotFoundException;
import com.bigbrother.bottleStore.exceptions.UserNotFoundException;
import com.bigbrother.bottleStore.model.Product;
import com.bigbrother.bottleStore.model.Sale;
import com.bigbrother.bottleStore.model.SaleItem;
import com.bigbrother.bottleStore.model.User;
import com.bigbrother.bottleStore.repository.ProductRepository;
import com.bigbrother.bottleStore.repository.SaleRepository;
import com.bigbrother.bottleStore.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SaleService {

    @Autowired
    private SaleRepository saleRepository;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProductRepository productRepository;

    private SaleDTO convertToSaleDTO(Sale sale) {

        List<SaleItemDTO> saleItemDTO = sale.getProducts().stream().map(item -> new SaleItemDTO(
                item.getId(),
                item.getSale().getId(),
                item.getProduct().getId(),
                item.getQuantity(),
                item.getUnitPrice(),
                item.getTotalPrice(),
                item.getProfit()
        )).toList();

        return new SaleDTO(
                sale.getId(),
                sale.getSeller().getId(),
                sale.getSaleDate(),
                sale.getTotalAmount(),
                sale.getTotalProfit(),
                saleItemDTO
        );
    }
    public List<SaleItemDTO> convertToSaleItemDTO(List<SaleItem> saleItems) {
        return saleItems.stream()
                .map(item -> new SaleItemDTO(
                        item.getId(),
                        item.getSale().getId(),
                        item.getProduct().getId(),
                        item.getQuantity(),
                        item.getUnitPrice(),
                        item.getTotalPrice(),
                        item.getProfit()
                ))
                .collect(Collectors.toList());
    }




    @Transactional
    public SaleDTO createSale(Long sellerId, LocalDateTime saleDate, List<SaleItemDTO> items) {
        Sale sale = new Sale();
        User seller = userRepository.findById(sellerId).orElseThrow(() -> new UserNotFoundException("Seller not found"));
        sale.setSeller(seller);
        sale.setSaleDate(saleDate != null ? saleDate : LocalDateTime.now());



        for (SaleItemDTO itemDTO : items) {
            Product product = productRepository.findById(itemDTO.productId()).orElseThrow(() -> new ProductNotFoundException("Product not found"));

            SaleItem saleItem = new SaleItem();
            saleItem.setSale(sale);
            saleItem.setProduct(product);
            saleItem.setQuantity(itemDTO.quantity());
            saleItem.setUnitPrice(product.getSellingPrice());
            saleItem.calculateTotalPrice();
            saleItem.calculateProfit();
            sale.getProducts().add(saleItem);
        }

        sale.updateTotals();
        return convertToSaleDTO(saleRepository.save(sale));
    }

    public SaleDTO getSaleById(Long id) {
        Sale sale = saleRepository.findById(id).orElseThrow(() -> new SaleNotFoundException("Sale not found"));
        return convertToSaleDTO(sale);
    }
}
