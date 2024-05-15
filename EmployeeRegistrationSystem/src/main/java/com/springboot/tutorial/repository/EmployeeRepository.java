package com.springboot.tutorial.repository;

import org.springframework.data.domain.Pageable;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.springboot.tutorial.entity.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

	void deleteById(int id);
	
	List<Employee> findAllByOrderByNameAsc();

	List<Employee> findByNameContainingIgnoreCase(String keyword);

    Page<Employee> findByNameContainingIgnoreCase(String name, Pageable pageable);

}
