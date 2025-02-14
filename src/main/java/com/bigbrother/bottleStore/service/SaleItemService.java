package com.bigbrother.bottleStore.service;

import com.bigbrother.bottleStore.repository.SaleItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SaleItemService {

    @Autowired
    private SaleItemRepository saleItemRepository;



}
