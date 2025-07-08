package com.bigbrother.bottleStore.sale;


import com.bigbrother.bottleStore.auth.AuthService;
import com.bigbrother.bottleStore.customer.CustomerRepository;
import com.bigbrother.bottleStore.sale.payment.PaymentMethod;
import com.bigbrother.bottleStore.sale.payment.PaymentMethodUsedRequest;
import com.bigbrother.bottleStore.sale.payment.SalePaymentRepository;
import com.bigbrother.bottleStore.exceptions.*;
import com.bigbrother.bottleStore.product.Product;
import com.bigbrother.bottleStore.product.ProductRepository;
import com.bigbrother.bottleStore.sale.saleItem.*;
import com.bigbrother.bottleStore.user.User;
import com.bigbrother.bottleStore.user.UserRepository;
import com.bigbrother.bottleStore.user.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SaleService {


    private final SaleMapper mapper;

    private final SaleItemMapper itemMapper;

    private  final CustomerRepository customerRepository;

    private final SaleRepository saleRepository;

    private final SaleItemRepository saleItemRepository;

    private final UserRepository userRepository;

    private final ProductRepository productRepository;

    private final AuthService authService;

    private final SalePaymentRepository salePaymentRepository;
    private final UserService userService;


    @Transactional
    protected Sale saveSale(Sale sale, List<SaleItemRequest> items) {

        BigDecimal totalAmount = BigDecimal.ZERO;

        for(SaleItemRequest item : items) {
            Product product = productRepository
                    .findById(item.productId()).orElseThrow(() -> new ProductNotFoundException("The product with id " + item.productId() + " does not exist"));


            double boxesToSubtract = switch (item.saleUnit()) {
                case BOX -> item.quantity();
                case PACK -> (double) (item.quantity() * product.getBottlesPerPack()) / product.getBottlesPerBox();
                case BOTTLE -> (double) item.quantity() / product.getBottlesPerBox();
            };
            if(product.getStockQuantity() < boxesToSubtract) {
                throw new IllegalArgumentException("Insufficient stock quantity");
            }
            BigDecimal subtotal = switch (item.saleUnit()) {
                case BOX -> product.getBoxPrice().multiply(BigDecimal.valueOf(item.quantity()));
                case PACK -> product.getPackPrice().multiply(BigDecimal.valueOf(item.quantity()));
                case BOTTLE -> product.getUnitPrice().multiply(BigDecimal.valueOf(item.quantity()));
            };


            product.setStockQuantity(product.getStockQuantity() - boxesToSubtract);
            productRepository.save(product);

            var saleItem = itemMapper.toSaleItem(sale, product, item, subtotal);

            totalAmount = totalAmount.add(saleItem.getSubtotal());

        }
        sale.setTotalAmount(totalAmount);
        return saleRepository.save(sale);
    }


    private String generateSaleCode() {
        String number;
        do {
            int random = (int) (Math.random() * 90000) + 10000;
            number = String.valueOf(random);
        } while (saleRepository.existsBySaleCode(number));
        return number;
    }



    @Transactional
    public SaleResponse createSale(List<SaleItemRequest> items,
                                   List<PaymentMethodUsedRequest> paymentMethods, UUID customerId) {
        Sale sale = new Sale();
        User seller = userService.getLoggedUser();
        if(customerId != null) {
            var customer = customerRepository.findById(customerId).orElseThrow(() -> new CustomerNotFoundException("Customer not found"));
            sale.setCustomer(customer);
        }

        sale.setSeller(seller);
        sale.setSaleDate(LocalDateTime.now());
        sale.setSaleCode(generateSaleCode());

        Sale savedSale = saveSale(sale, items);

        List<PaymentMethod> paymentList = paymentMethods .stream().map(paymentDTO -> {
            PaymentMethod payment = new PaymentMethod();
            payment.setSale(savedSale);
            payment.setPaymentMethod(paymentDTO.type());
            payment.setAmount(paymentDTO.amount());
            return payment;
        }).toList();

        salePaymentRepository.saveAll(paymentList);
        savedSale.setPaymentMethods(paymentList);

        return mapper.fromSale(savedSale);
    }




    @Transactional
    public SaleResponse updateSale(SaleRequest request, UUID id) {
        Sale sale = saleRepository.findById(id).orElseThrow(() -> new SaleNotFoundException("Sale does not exists"));

        for (SaleItem oldItem: sale.getProducts()){
            Product product = oldItem.getProduct();
            product.setStockQuantity(product.getStockQuantity() + oldItem.getQuantity());
            productRepository.save(product);
        }
        sale.getProducts().clear();


        return mapper.fromSale(saveSale(sale, request.items()));
    }


    public SaleResponse getSaleById(UUID id) {
        Sale sale = saleRepository.findById(id).orElseThrow(() -> new SaleNotFoundException("Sale not found"));
        return mapper.fromSale(sale);
    }

    public List<SaleResponse> getAllSales() {
        List<Sale> saleList = saleRepository.findAll();
        return saleList.stream().map(mapper::fromSale).collect(Collectors.toList());
    }


    public List<SaleResponse> getSalesBySellerId(Long sellerId) {
        User user = userRepository.findById(sellerId).orElseThrow(() -> new UserNotFoundException("User not found"));
        List<Sale> saleList = saleRepository.findBySellerId(user.getId());
        return saleList.stream().map(mapper::fromSale).collect(Collectors.toList());
    }

    public List<SaleItemResponse> getItemsBySaleId(UUID saleId) {
        Sale sale = saleRepository.findById(saleId).orElseThrow(() -> new SaleNotFoundException("Sale not found"));
        List<SaleItem> saleItemList = saleItemRepository.findBySaleId(sale.getId());
        return saleItemList.stream().map(itemMapper::toSaleItemResponse).collect(Collectors.toList());
    }

    public List<SaleResponse> getSalesByDate(LocalDate saleDate) {
        return getSaleDTOS(saleDate, saleDate);
    }

    public List<SaleResponse> getSalesByDateBetween(LocalDate startDate, LocalDate endDate) {
        return getSaleDTOS(startDate, endDate);
    }

    private List<SaleResponse> getSaleDTOS(LocalDate startDate, LocalDate endDate) {
        LocalDateTime startOfDay = startDate.atStartOfDay();
        LocalDateTime endOfDay = endDate.plusDays(1).atStartOfDay();
        List<Sale> sales = saleRepository.findSaleBySaleDateBetween(startOfDay, endOfDay);
        if (sales.isEmpty()) {
            throw new SaleNotFoundException("Sales not found");
        }
        return sales.stream().map(mapper::fromSale).collect(Collectors.toList());
    }


    public void deleteSale(UUID saleId) {
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


    public List<SaleResponse> getLastNSales(int count) {
        PageRequest pageable = PageRequest.of(0, count, Sort.by("saleDate").descending());
        return saleRepository.findAll(pageable).getContent().stream()
                .map(mapper::fromSale)
                .toList();
    }
}
