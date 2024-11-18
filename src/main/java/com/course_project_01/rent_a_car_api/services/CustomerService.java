package com.course_project_01.rent_a_car_api.services;

import com.course_project_01.rent_a_car_api.dtos.CustomerDTO;
import com.course_project_01.rent_a_car_api.entities.Customer;
import com.course_project_01.rent_a_car_api.exceptions.ResourceNotFoundException;
import com.course_project_01.rent_a_car_api.repositories.CustomerRepository;
import com.course_project_01.rent_a_car_api.repositories.OfferRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final OfferRepository offerRepository;

    public CustomerService(CustomerRepository customerRepository, OfferRepository offerRepository) {
        this.customerRepository = customerRepository;
        this.offerRepository = offerRepository;
    }

    public Customer getCustomerById(int customerId) {
        return customerRepository.findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with id: " + customerId));
    }

    public List<Customer> getCustomers() {
        return customerRepository.findAll();
    }

    public Customer createCustomer(CustomerDTO createCustomerDTO) {
        if (createCustomerDTO.getFirstName() != null && createCustomerDTO.getLastName() != null && createCustomerDTO.getAddress() != null &&
                createCustomerDTO.getPhone() != null && createCustomerDTO.getAge() != null && createCustomerDTO.isHasAccidents() != null) {
            validateAge(createCustomerDTO.getAge());
            Customer customer = new Customer();
            customer.setFirstName(createCustomerDTO.getFirstName());
            customer.setLastName(createCustomerDTO.getLastName());
            customer.setAddress(createCustomerDTO.getAddress());
            customer.setPhone(createCustomerDTO.getPhone());
            customer.setAge(createCustomerDTO.getAge());
            customer.setHasAccidents(createCustomerDTO.isHasAccidents());
            return customerRepository.create(customer);
        }
        else {
            throw new IllegalArgumentException("Partial data provided");
        }
    }

    public Customer updateCustomer(int customerId, CustomerDTO updateCustomerDTO) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with id: " + customerId));
        if (updateCustomerDTO.getFirstName() != null) {
            customer.setFirstName(updateCustomerDTO.getFirstName());
        }
        if (updateCustomerDTO.getLastName() != null) {
            customer.setLastName(updateCustomerDTO.getLastName());
        }
        if (updateCustomerDTO.getAddress() != null) {
            customer.setAddress(updateCustomerDTO.getAddress());
        }
        if (updateCustomerDTO.getPhone() != null) {
            customer.setPhone(updateCustomerDTO.getPhone());
        }
        if (updateCustomerDTO.getAge() != null) {
            validateAge(updateCustomerDTO.getAge());
            customer.setAge(updateCustomerDTO.getAge());
        }
        if (updateCustomerDTO.isHasAccidents() != null) {
            customer.setHasAccidents(updateCustomerDTO.isHasAccidents());
        }
        return customerRepository.update(customer);
    }

    public boolean deleteCustomer(int customerId) {
        customerRepository.findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with id: " + customerId));
        if (offerRepository.isCustomerInAcceptedOffer(customerId)) {
            throw new IllegalStateException("Cannot delete a customer in accepted offers");
        }
        offerRepository.archiveUnacceptedOffersForCustomer(customerId);
        customerRepository.delete(customerId);
        return true;
    }

    private void validateAge(int age) {
        if (age < 18) {
            throw new IllegalArgumentException("Age must be greater or equal to 18");
        }
    }

    public boolean existsCustomerById(int customerId) {
        return getCustomerById(customerId) != null;
    }
}
