package com.bigbrother.bottleStore.customer;


import com.bigbrother.bottleStore.exceptions.CustomerNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository repository;
    private final CustomerMapper mapper;

    public CustomerResponse createCustomer(@Valid CustomerRequest request) {
        var customer = repository.save(mapper.toCustomer(request));
        return mapper.fromCustomer(customer);
    }

    public List<CustomerResponse> findAllCustomers() {
        return repository.findAll().stream().map(mapper::fromCustomer).collect(Collectors.toList());
    }


    public CustomerResponse findCustomerById(UUID customerId) {
        var customer = repository.findById(customerId).orElseThrow(()-> new CustomerNotFoundException("Customer not found"));
        return mapper.fromCustomer(customer);
    }
}
