package com.rimal.petstore.dao;

import com.rimal.petstore.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerDao extends JpaRepository<Customer, Long> {


}
