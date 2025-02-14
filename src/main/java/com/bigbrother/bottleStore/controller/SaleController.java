package com.bigbrother.bottleStore.controller;

import com.bigbrother.bottleStore.dto.SaleDTO;
import com.bigbrother.bottleStore.dto.SaleItemDTO;
import com.bigbrother.bottleStore.model.Sale;
import com.bigbrother.bottleStore.service.SaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/bigbrother/api/sales")
public class SaleController {


    @Autowired
    private SaleService saleService;


    @PostMapping("")
    public ResponseEntity<SaleDTO> createSale(@RequestBody SaleDTO saleDTO) {

        return new ResponseEntity<>(saleService.createSale(saleDTO.sellerId(), saleDTO.saleDate(), saleDTO.items()), HttpStatus.CREATED);
    }
}
