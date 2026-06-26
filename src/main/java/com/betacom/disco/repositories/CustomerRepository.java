package com.betacom.disco.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.betacom.disco.entities.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    long countByName(String name);

    // SELECT * from customer where name = name and surname
    Optional<Customer> findByNameAndSurname(String name, String surname);

    @Query(value = "SELECT * from customer", nativeQuery=true)
    List<Customer> findAllNative();
}
