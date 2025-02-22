package com.bigbrother.bottleStore.repository;

import com.bigbrother.bottleStore.model.SalePayment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SalePaymentRepository extends JpaRepository<SalePayment, Long> {
}
