package com.mlcdev.employeeapi.repository;


import com.mlcdev.employeeapi.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
}
