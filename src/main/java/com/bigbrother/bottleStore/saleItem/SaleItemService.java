package com.bigbrother.bottleStore.saleItem;

import com.bigbrother.bottleStore.sale.SaleItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SaleItemService {

    @Autowired
    private SaleItemRepository saleItemRepository;



}
