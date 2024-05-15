package com.springboot.tutorial.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.springboot.tutorial.entity.Employee;

public interface EmployeeService {

	List<Employee> listAll();

	void save(Employee employee);

	public void deleteEmployeeById(long id);

	Employee findById(long id);

	Page<Employee> findAll(Pageable pageable);
}
