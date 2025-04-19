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
    public ResponseEntity<List<SaleDTO>>getLastNSales(@RequestParam(defaultValue = "5") int count) {
        return new ResponseEntity<>(saleService.getLastNSales(count), HttpStatus.OK);
    }


    @PostMapping("")
    public ResponseEntity<SaleDTO> createSale(@RequestBody SaleDTO saleDTO) {

        return new ResponseEntity<>(saleService.createSale(saleDTO.saleDate(), saleDTO.items(), saleDTO.payments()), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SaleDTO> getSale(@PathVariable long id) {
        return new ResponseEntity<>(saleService.getSaleById(id), HttpStatus.OK);
    }

    @GetMapping("")
    public ResponseEntity<List<SaleDTO>> getAllSales() {
        return new ResponseEntity<>(saleService.getAllSales(), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SaleDTO> updateSale(@RequestBody SaleDTO saleDTO, @PathVariable long id) {
        return new ResponseEntity<>(saleService.updateSale(saleDTO, id), HttpStatus.CREATED);
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


    @GetMapping("/date/between")
    public ResponseEntity<List<SaleDTO>> getSalesByDateBetween(@RequestParam String startDate, @RequestParam String endDate){
        LocalDate start = LocalDate.parse(startDate);
        LocalDate end = LocalDate.parse(endDate);

        List<SaleDTO> sales = saleService.getSalesByDateBetween(start, end);
        return new ResponseEntity<>(sales, HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteSale(@PathVariable Long id) {
        saleService.deleteSale(id);
        return ResponseEntity.noContent().build();
    }


}
