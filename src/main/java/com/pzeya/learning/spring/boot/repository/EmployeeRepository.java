package com.pzeya.learning.spring.boot.repository;

import com.pzeya.learning.spring.boot.model.Employee;
import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface EmployeeRepository
    extends ReactiveCrudRepository<Employee, String>, ReactiveQueryByExampleExecutor<Employee> {}
