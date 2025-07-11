package com.bigbrother.bottleStore.sale.saleItem;

import com.bigbrother.bottleStore.sale.SaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/bigbrother/sale-items")
public class SaleItemController {

    @Autowired
    private SaleItemService saleItemService;

    @Autowired
    private SaleService saleService;

    @GetMapping("/sale/{id}")
    public ResponseEntity<List<SaleItemResponse>> getSaleItemsBySaleId(@PathVariable UUID id) {
        return new ResponseEntity<>(saleService.getItemsBySaleId(id), HttpStatus.OK);
    }
}
