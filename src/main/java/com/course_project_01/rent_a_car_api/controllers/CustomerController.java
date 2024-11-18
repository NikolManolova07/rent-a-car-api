package com.course_project_01.rent_a_car_api.controllers;

import com.course_project_01.rent_a_car_api.dtos.CustomerDTO;
import com.course_project_01.rent_a_car_api.entities.Customer;
import com.course_project_01.rent_a_car_api.services.CustomerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    public ResponseEntity<List<Customer>> getAllCustomers() {
        List<Customer> response = customerService.getCustomers();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{customerId}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable int customerId) {
        Customer response = customerService.getCustomerById(customerId);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<Customer> createCustomer(@RequestBody CustomerDTO customer) {
        Customer response = customerService.createCustomer(customer);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{customerId}")
    public ResponseEntity<Customer> updateCustomer(@PathVariable int customerId, @RequestBody CustomerDTO customer) {
        Customer response = customerService.updateCustomer(customerId, customer);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{customerId}")
    public ResponseEntity<Boolean> deleteCustomer(@PathVariable int customerId) {
        boolean response = customerService.deleteCustomer(customerId);
        return ResponseEntity.ok(response);
    }
}
