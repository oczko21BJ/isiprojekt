package com.systemzarzadzaniaapteka.controller;

import com.systemzarzadzaniaapteka.model.Customer;
import com.systemzarzadzaniaapteka.repository.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomerControllerTest {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerController customerController;

    @Test
    void getAllCustomers() {
        // Given
        Customer customer1 = new Customer();
        Customer customer2 = new Customer();
        when(customerRepository.findAll()).thenReturn(Arrays.asList(customer1, customer2));

        // When
        List<Customer> result = customerController.getAllCustomers();

        // Then
        assertEquals(2, result.size());
    }

    @Test
    void getCustomerById() {
        // Given
        Long id = 1L;
        Customer customer = new Customer();
        when(customerRepository.findById(id)).thenReturn(Optional.of(customer));

        // When
        Customer result = customerController.getCustomerById(id);

        // Then
        assertEquals(customer, result);
    }

    @Test
    void createCustomer() {
        // Given
        Customer customer = new Customer();
        when(customerRepository.save(any(Customer.class))).thenReturn(customer);

        // When
        Customer result = customerController.createCustomer(customer);

        // Then
        assertEquals(customer, result);
    }

    @Test
    void updateCustomer() {
        // Given
        Long id = 1L;
        Customer existingCustomer = new Customer();
        Customer updatedDetails = new Customer();
        updatedDetails.setName("Updated Name");
        updatedDetails.setEmail("updated@example.com");
        
        when(customerRepository.findById(id)).thenReturn(Optional.of(existingCustomer));
        when(customerRepository.save(any(Customer.class))).thenReturn(existingCustomer);

        // When
        Customer result = customerController.updateCustomer(id, updatedDetails);

        // Then
        assertEquals("Updated Name", result.getName());
        assertEquals("updated@example.com", result.getEmail());
    }

    @Test
    void deleteCustomer() {
        // Given
        Long id = 1L;

        // When
        customerController.deleteCustomer(id);

        // Then
        verify(customerRepository).deleteById(id);
    }
}