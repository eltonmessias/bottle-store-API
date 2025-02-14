package com.bigbrother.bottleStore.controller;

import com.bigbrother.bottleStore.dto.SaleDTO;
import com.bigbrother.bottleStore.service.SaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/bigbrother/api/sales")
public class SaleController {


    @Autowired
    private SaleService saleService;


    @PostMapping("")
    public ResponseEntity<SaleDTO> createSale(@RequestBody SaleDTO saleDTO) {

        return new ResponseEntity<>(saleService.createSale(saleDTO.sellerId(), saleDTO.saleDate(), saleDTO.items()), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SaleDTO> getSale(@PathVariable long id) {
        return new ResponseEntity<>(saleService.getSaleById(id), HttpStatus.OK);
    }

    @GetMapping("")
    public ResponseEntity<List<SaleDTO>> getAllSales() {
        return new ResponseEntity<>(saleService.getAllSales(), HttpStatus.OK);
    }

    @GetMapping("/seller/{id}")
    public ResponseEntity<List<SaleDTO>> getSalesBySellerId(@PathVariable long id) {
        return new ResponseEntity<>(saleService.getSalesBySellerId(id), HttpStatus.OK);
    }

    @GetMapping("/date/{saleDate}")
    public ResponseEntity<List<SaleDTO>> getSalesBySaleDate(@PathVariable String saleDate) {
        LocalDate date = LocalDate.parse(saleDate);
        return new ResponseEntity<>(saleService.getSalesByDate(date), HttpStatus.OK);
    }

}
