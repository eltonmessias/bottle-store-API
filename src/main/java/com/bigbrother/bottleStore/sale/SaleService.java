package com.bigbrother.bottleStore.sale;


import com.bigbrother.bottleStore.auth.AuthService;
import com.bigbrother.bottleStore.sale.payment.SalePayment;
import com.bigbrother.bottleStore.sale.payment.SalePaymentRepository;
import com.bigbrother.bottleStore.saleItem.SaleItemDTO;
import com.bigbrother.bottleStore.sale.payment.SalePaymentDTO;
import com.bigbrother.bottleStore.exceptions.*;
import com.bigbrother.bottleStore.product.Product;
import com.bigbrother.bottleStore.product.ProductRepository;
import com.bigbrother.bottleStore.saleItem.SaleItem;
import com.bigbrother.bottleStore.saleItem.SaleRepository;
import com.bigbrother.bottleStore.user.User;
import com.bigbrother.bottleStore.user.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SaleService {

    @Autowired
    private SaleRepository saleRepository;
    @Autowired
    private SaleItemRepository saleItemRepository;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private AuthService authService;
    @Autowired
    private SalePaymentRepository salePaymentRepository;


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

        List<SalePaymentDTO> salePaymentDTO = sale.getPayments().stream().map(salePayment -> new SalePaymentDTO(
                salePayment.getId(),
                salePayment.getSale().getId(),
                salePayment.getPaymentMethod(),
                salePayment.getAmount()
        )).toList();

        return new SaleDTO(
                sale.getId(),
                sale.getSeller().getId(),
                sale.getSaleDate(),
                sale.getCustomer().getId(),
                sale.getTotalAmount(),
                sale.getTotalProfit(),
                saleItemDTO,
                salePaymentDTO
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


    private Sale saveSale(Sale sale, List<SaleItemDTO> items) {
        for (SaleItemDTO itemDTO : items) {
            Product product = productRepository.findById(itemDTO.productId())
                    .orElseThrow(() -> new ProductNotFoundException("Product not found"));

            // Verifica se há estoque suficiente
            if (product.getStockQuantity() < itemDTO.quantity()) {
                throw new InsufficientStockException("Not enough stock for product ID: " + itemDTO.productId());
            }

            // Atualiza o estoque do produto
            product.setStockQuantity(product.getStockQuantity() - itemDTO.quantity());
            productRepository.save(product);

            // Cria um novo SaleItem
            SaleItem saleItem = new SaleItem();
            saleItem.setSale(sale);
            saleItem.setProduct(product);
            saleItem.setQuantity(itemDTO.quantity());
            saleItem.setUnitPrice(product.getSellingPrice());
            saleItem.calculateTotalPrice();
            saleItem.calculateProfit();

            sale.getProducts().add(saleItem);
        }

        // Atualiza os totais
        sale.updateTotals();

        return saleRepository.save(sale);
    }




    @Transactional
    public SaleDTO createSale(LocalDateTime saleDate, List<SaleItemDTO> items, List<SalePaymentDTO> payments) {
        Sale sale = new Sale();
        User seller = userRepository.findByUsername(authService.getLoggedInUsername());
        sale.setSeller(seller);
        sale.setSaleDate(saleDate != null ? saleDate : LocalDateTime.now());

        Sale savedSale = saveSale(sale, items);

        List<SalePayment> paymentList = payments.stream().map(paymentDTO -> {
            SalePayment payment = new SalePayment();
            payment.setSale(savedSale);
            payment.setPaymentMethod(paymentDTO.type());
            payment.setAmount(paymentDTO.amount());
            return payment;
        }).toList();

        salePaymentRepository.saveAll(paymentList);
        savedSale.setPayments(paymentList);

        return convertToSaleDTO(savedSale);
    }




    @Transactional
    public SaleDTO updateSale(SaleDTO saleDTO, Long id) {
        Sale sale = saleRepository.findById(id).orElseThrow(() -> new SaleNotFoundException("Sale does not exists"));

        for (SaleItem oldItem: sale.getProducts()){
            Product product = oldItem.getProduct();
            product.setStockQuantity(product.getStockQuantity() + oldItem.getQuantity());
            productRepository.save(product);
        }

        sale.getProducts().clear();

        if(saleDTO.saleDate() != null) {
            sale.setSaleDate(saleDTO.saleDate());
        }

        return convertToSaleDTO(saveSale(sale, saleDTO.items()));
    }


    public SaleDTO getSaleById(Long id) {
        Sale sale = saleRepository.findById(id).orElseThrow(() -> new SaleNotFoundException("Sale not found"));
        return convertToSaleDTO(sale);
    }

    public List<SaleDTO> getAllSales() {
        List<Sale> saleList = saleRepository.findAll();
        return saleList.stream().map(this::convertToSaleDTO).collect(Collectors.toList());
    }


    public List<SaleDTO> getSalesBySellerId(Long sellerId) {
        User user = userRepository.findById(sellerId).orElseThrow(() -> new UserNotFoundException("User not found"));
        List<Sale> saleList = saleRepository.findBySellerId(user.getId());
        return saleList.stream().map(this::convertToSaleDTO).collect(Collectors.toList());
    }

    public List<SaleItemDTO> getItemsBySaleId(Long saleId) {
        Sale sale = saleRepository.findById(saleId).orElseThrow(() -> new SaleNotFoundException("Sale not found"));
        List<SaleItem> saleItemList = saleItemRepository.findBySaleId(sale.getId());
        return convertToSaleItemDTO(saleItemList);
    }

    public List<SaleDTO> getSalesByDate(LocalDate saleDate) {
        return getSaleDTOS(saleDate, saleDate);
    }

    public List<SaleDTO> getSalesByDateBetween(LocalDate startDate, LocalDate endDate) {
        return getSaleDTOS(startDate, endDate);
    }

    private List<SaleDTO> getSaleDTOS(LocalDate startDate, LocalDate endDate) {
        LocalDateTime startOfDay = startDate.atStartOfDay();
        LocalDateTime endOfDay = endDate.plusDays(1).atStartOfDay();
        List<Sale> sales = saleRepository.findSaleBySaleDateBetween(startOfDay, endOfDay);
        if (sales.isEmpty()) {
            throw new SaleNotFoundException("Sales not found");
        }
        return sales.stream().map(this::convertToSaleDTO).collect(Collectors.toList());
    }


    public void deleteSale(Long saleId) {
        Sale sale = saleRepository.findById(saleId).orElseThrow(() -> new SaleNotFoundException("Sale not found with ID: "+ saleId));

        try {
            for (SaleItem saleItem: sale.getProducts()){
                Product product = saleItem.getProduct();
                if(product == null) {
                    throw new StockUpdateException("Product not found for sale item ID: " + saleItem.getId());
                }
                product.setStockQuantity(product.getStockQuantity() + saleItem.getQuantity());
                productRepository.save(product);
            }
            saleRepository.delete(sale);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Cannot delete sale ID: "+ saleId + " due to database constraints");
        } catch (Exception e) {
            throw new DatabaseException("Unexpected error while deleting sale ID: " + saleId);
        }

    }


    public List<SaleDTO> getLastNSales(int count) {
        PageRequest pageable = PageRequest.of(0, count, Sort.by("saleDate").descending());
        return saleRepository.findAll(pageable).getContent().stream()
                .map(this::convertToSaleDTO)
                .toList();
    }
}
