package com.bigbrother.bottleStore.controller;

import com.bigbrother.bottleStore.dto.SaleItemDTO;
import com.bigbrother.bottleStore.service.SaleItemService;
import com.bigbrother.bottleStore.service.SaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/bigbrother/api/sale-items")
public class SaleItemController {

    @Autowired
    private SaleItemService saleItemService;

    @Autowired
    private SaleService saleService;

    @GetMapping("/sale/{id}")
    public ResponseEntity<List<SaleItemDTO>> getSaleItemsBySaleId(@PathVariable Long id) {
        return new ResponseEntity<>(saleService.getItemsBySaleId(id), HttpStatus.OK);
    }
}
