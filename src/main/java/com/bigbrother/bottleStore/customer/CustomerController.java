package com.bigbrother.bottleStore.customer;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/bigbrother/customer")
@RequiredArgsConstructor
public class CustomerController {


    private final CustomerService service;


    @PostMapping
    public ResponseEntity<CustomerResponse> createCustomer(@RequestBody @Valid CustomerRequest request) {
        return new ResponseEntity<>(service.createCustomer(request), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<CustomerResponse>> findAllCustomers() {
        return new ResponseEntity<>(service.findAllCustomers(), HttpStatus.OK);
    }

    @GetMapping("/{customer-id}")
    public ResponseEntity<CustomerResponse> findCustomerById(@PathVariable("customer-id") UUID customerId) {
        return ResponseEntity.ok(service.findCustomerById(customerId));
    }

    @PutMapping("/{customer-id}")
    public ResponseEntity<CustomerResponse> updateCustomer(@PathVariable("customer-id") UUID customerId, @RequestBody @Valid CustomerRequest request) {
        return ResponseEntity.ok(service.updateCustomer(customerId, request));
    }

    @DeleteMapping("/{customer-id}")
    public ResponseEntity<?> deleteCustomer(@PathVariable("customer-id") UUID customerId) {
        service.deleteCustomer(customerId);
        return ResponseEntity.noContent().build();
    }
}

