package com.course_project_01.rent_a_car_api.repositories;

import com.course_project_01.rent_a_car_api.entities.Customer;

import java.util.List;
import java.util.Optional;

public interface CustomerRepository {

    List<Customer> findAll();
    Optional<Customer> findById(int customerId);
    Customer create(Customer customer);
    Customer update(Customer customer);
    boolean delete(int customerId);
}
