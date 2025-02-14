package com.bigbrother.bottleStore.repository;

import com.bigbrother.bottleStore.model.Sale;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SaleRepository extends JpaRepository<Sale, Long> {
    List<Sale> findBySellerId(Long sellerId);
}
