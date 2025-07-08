package com.bigbrother.bottleStore.customer;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CustomerRepositoryTest {

    @Autowired
    private CustomerRepository customerRepository;

    @Test
    @DisplayName("Should save a customer")
    @Order(1)
    void saveCustomer() {

        Customer customer = Customer.builder()
                .firstName("Elton")
                .lastName("Guambe")
                .email("eltonmessias10@gmail.com")
                .phone("844264898")
                .build();

        customerRepository.save(customer);
        Assertions.assertNotNull(customer.getId());
        Assertions.assertNotNull(customer.getFirstName());
        Assertions.assertNotNull(customer.getLastName());
        Assertions.assertNotNull(customer.getEmail());
        Assertions.assertNotNull(customer.getPhone());
    }

    @Test
    @Order(2)
    void getCustomerTest() {
        Customer customer = customerRepository.findByEmail("eltonmessias10@gmail.com").orElseThrow(null);
        assertNotNull(customer);
        assertEquals("Elton", customer.getFirstName());
    }

    @Test
    @Order(3)
    void getListOfCustomerTest() {
        List<Customer> customers = customerRepository.findAll();
        assertNotNull(customers);
        assertThat(customers.size()).isGreaterThan(0);

    }




}