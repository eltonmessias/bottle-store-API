package com.bigbrother.bottleStore.saleItem;

import com.bigbrother.bottleStore.sale.Sale;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface SaleRepository extends JpaRepository<Sale, Long> {
    List<Sale> findBySellerId(Long sellerId);

    List<Sale> findSaleBySaleDateBetween(LocalDateTime start, LocalDateTime end);
}
