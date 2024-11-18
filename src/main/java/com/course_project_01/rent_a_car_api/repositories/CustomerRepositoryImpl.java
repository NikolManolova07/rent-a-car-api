package com.course_project_01.rent_a_car_api.repositories;

import com.course_project_01.rent_a_car_api.entities.Customer;
import com.course_project_01.rent_a_car_api.mappers.CustomerRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class CustomerRepositoryImpl implements CustomerRepository {

    private final JdbcTemplate jdbcTemplate;
    private final CustomerRowMapper customerRowMapper = new CustomerRowMapper();

    public CustomerRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Customer> findAll() {
        String selectSql = "SELECT * FROM customers WHERE is_active = 1";
        return jdbcTemplate.query(selectSql, customerRowMapper);
    }

    @Override
    public Optional<Customer> findById(int customerId) {
        String selectSql = "SELECT * FROM customers WHERE customer_id = ? AND is_active = 1";
        List<Customer> customers = jdbcTemplate.query(selectSql, customerRowMapper, customerId);
        return customers.stream().findFirst();
    }

    @Override
    public Customer create(Customer customer) {
        String insertSql = "INSERT INTO customers (first_name, last_name, address, phone, age, has_accidents, is_active) VALUES (?, ?, ?, ?, ?, ?, 1)";
        jdbcTemplate.update(insertSql, customer.getFirstName(), customer.getLastName(), customer.getAddress(),
                customer.getPhone(), customer.getAge(), customer.getHasAccidents());
        String selectSql = "SELECT * FROM customers ORDER BY customer_id DESC LIMIT 1";
        return jdbcTemplate.queryForObject(selectSql, customerRowMapper);
    }

    @Override
    public Customer update(Customer customer) {
        String updateSql = "UPDATE customers SET first_name = ?, last_name = ?, address = ?, phone = ?, age = ?, has_accidents = ? WHERE customer_id = ? AND is_active = 1";
        jdbcTemplate.update(updateSql, customer.getFirstName(), customer.getLastName(), customer.getAddress(),
                customer.getPhone(), customer.getAge(), customer.getHasAccidents(), customer.getCustomerId());
        return customer;
    }

    @Override
    public boolean delete(int customerId) {
        String updateSql = "UPDATE customers SET is_active = 0 WHERE customer_id = ?";
        jdbcTemplate.update(updateSql, customerId);
        return true;
    }
}
