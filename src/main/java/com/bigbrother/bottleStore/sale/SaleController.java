package com.bigbrother.bottleStore.sale;

import com.bigbrother.bottleStore.saleItem.SaleRepository;
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
    @Autowired
    private SaleRepository saleRepository;

    @GetMapping("/latest")
    public ResponseEntity<List<SaleResponse>>getLastNSales(@RequestParam(defaultValue = "5") int count) {
        return new ResponseEntity<>(saleService.getLastNSales(count), HttpStatus.OK);
    }


    @PostMapping("")
    public ResponseEntity<SaleResponse> createSale(@RequestBody SaleRequest request) {

        return new ResponseEntity<>(saleService.createSale(request.saleDate(), request.items(), request.paymentMethods(), request.customerId()), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SaleResponse> getSale(@PathVariable long id) {
        return new ResponseEntity<>(saleService.getSaleById(id), HttpStatus.OK);
    }

    @GetMapping("")
    public ResponseEntity<List<SaleResponse>> getAllSales() {
        return new ResponseEntity<>(saleService.getAllSales(), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SaleResponse> updateSale(@RequestBody SaleRequest request, @PathVariable long id) {
        return new ResponseEntity<>(saleService.updateSale(request, id), HttpStatus.CREATED);
    }

    @GetMapping("/seller/{id}")
    public ResponseEntity<List<SaleResponse>> getSalesBySellerId(@PathVariable long id) {
        return new ResponseEntity<>(saleService.getSalesBySellerId(id), HttpStatus.OK);
    }

    @GetMapping("/date/{saleDate}")
    public ResponseEntity<List<SaleResponse>> getSalesBySaleDate(@PathVariable String saleDate) {
        LocalDate date = LocalDate.parse(saleDate);
        return new ResponseEntity<>(saleService.getSalesByDate(date), HttpStatus.OK);
    }


    @GetMapping("/date/between")
    public ResponseEntity<List<SaleResponse>> getSalesByDateBetween(@RequestParam String startDate, @RequestParam String endDate){
        LocalDate start = LocalDate.parse(startDate);
        LocalDate end = LocalDate.parse(endDate);

        List<SaleResponse> sales = saleService.getSalesByDateBetween(start, end);
        return new ResponseEntity<>(sales, HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteSale(@PathVariable Long id) {
        saleService.deleteSale(id);
        return ResponseEntity.noContent().build();
    }


}
