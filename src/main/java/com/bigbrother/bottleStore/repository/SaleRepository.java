package com.bigbrother.bottleStore.repository;

import com.bigbrother.bottleStore.model.Sale;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SaleRepository extends JpaRepository<Sale, Long> {
}
