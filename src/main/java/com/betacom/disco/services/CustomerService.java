package com.betacom.disco.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.betacom.disco.entities.Customer;
import com.betacom.disco.repositories.CustomerRepository;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    //

    public List<Customer> getAll() {
        return customerRepository.findAll();
    }

    public Optional<Customer> getById(Long id) {
        return customerRepository.findById(id);
    }

    //1

    public long countByCustomerName(String name) {
        return customerRepository.countByName(name);
    }

    //2

    public Optional<Customer> findByNameAndSurname(String name, String surname) {
        return customerRepository.findByNameAndSurname(name, surname);
    }

    //3

    public List<Customer> getAllNative() {
        return customerRepository.findAllNative();
    }

    public Customer createUser(Customer newCustomer) {
        return customerRepository.save(newCustomer);
    }
}
