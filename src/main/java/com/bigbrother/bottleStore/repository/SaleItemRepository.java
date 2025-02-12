package com.bigbrother.bottleStore.repository;

import com.bigbrother.bottleStore.model.SaleItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SaleItemRepository extends JpaRepository<SaleItem, Long> {
}
