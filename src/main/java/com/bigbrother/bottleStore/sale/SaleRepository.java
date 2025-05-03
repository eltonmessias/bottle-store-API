package com.bigbrother.bottleStore.sale;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface SaleRepository extends JpaRepository<Sale, UUID> {
    List<Sale> findBySellerId(UUID sellerId);

    List<Sale> findSaleBySaleDateBetween(LocalDateTime start, LocalDateTime end);

    boolean existsBySaleCode(String saleCode);
}
