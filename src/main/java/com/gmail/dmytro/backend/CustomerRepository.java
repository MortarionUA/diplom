package com.gmail.dmytro.backend;

import com.gmail.dmytro.backend.data.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
