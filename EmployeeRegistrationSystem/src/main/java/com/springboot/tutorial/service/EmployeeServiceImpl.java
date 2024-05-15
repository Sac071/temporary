package com.springboot.tutorial.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.springboot.tutorial.entity.Employee;
import com.springboot.tutorial.repository.EmployeeRepository;

@Service
public class EmployeeServiceImpl implements EmployeeService {
	@Autowired
	private EmployeeRepository eRepo;

	@Override
	public List<Employee> listAll() {
		return eRepo.findAll();
	}

	@Override
	public void save(Employee emp) {
		eRepo.save(emp);
	}

	@Override
	public void deleteEmployeeById(long id) {
		eRepo.deleteById(id);
	}

	@Override
    public Page<Employee> findAll(Pageable pageable) {
        return eRepo.findAll(pageable);
    }

	@Override
	public Employee findById(long id) {
	    Optional<Employee> optionalEmployee = eRepo.findById(id);
	    return optionalEmployee.orElse(null); // Return null if optionalEmployee is empty
	}


}
