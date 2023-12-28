package com.rimal.petstore.dao;


import com.rimal.petstore.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeDao extends JpaRepository<Employee, Long> {
  // Additional query methods can be added here if needed
}
