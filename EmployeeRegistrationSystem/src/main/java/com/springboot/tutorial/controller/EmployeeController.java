package com.springboot.tutorial.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import javax.validation.Valid;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import com.springboot.tutorial.entity.Employee;
import com.springboot.tutorial.exceptions.EmployeeNotFoundException;
import com.springboot.tutorial.repository.EmployeeRepository;
import com.springboot.tutorial.service.EmployeeService;

@Controller
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private EmployeeRepository employeeRepository;

    // Display the home page with a list of employees
    @RequestMapping("/")
    public String viewHomePage(Model model, @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortOrder,
            @RequestParam(required = false) String search) {
        // Create pageable object for pagination and sorting
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(sortOrder), sortBy));

        Page<Employee> employeePage;
        // Check if search keyword is provided
        if (search != null && !search.isEmpty()) {
            // If search keyword is provided, find employees by name containing the search keyword
            employeePage = employeeRepository.findByNameContainingIgnoreCase(search, pageable);
        } else {
            // If no search keyword is provided, retrieve all employees
            employeePage = employeeRepository.findAll(pageable);
        }
        
        // Add employee page to the model if not null
        if (employeePage != null) {
            model.addAttribute("page", employeePage);
        }
        
        // Add employee page content to the model for rendering in the view
        model.addAttribute("listAll", employeePage.getContent());
        
//        return "index.htm"; // Return the index.htm template
        return "index"; // returns index.html
    }

    // Display the page for adding a new employee
    @RequestMapping("/new_employee")
    public String showNewEmployeePage(Model model) {
        // Create a new Employee object
        Employee employee = new Employee();
        
        // Add the employee object to the model for rendering in the view
        model.addAttribute("employee", employee);
        
        return "new_employee"; // Return the new_employee template
    }

    // Save a new employee or update an existing one
//    @RequestMapping(value = "/save", method = RequestMethod.POST)
//    public String saveEmployee(@ModelAttribute("employee") Employee employee, Model model,
//            BindingResult result) {
//    	
//        // Save the employee using the service
//    	employeeService.save(employee); 
//        
//        // Redirect to the home page after saving
//        return "redirect:/"; 
//    }
    
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") long id, Model model) {
        Employee employee = employeeService.findById(id);
        model.addAttribute("employee", employee);
        return "new_employee"; // Assuming you have a Thymeleaf template for editing employee details
    }

    @PostMapping("/edit/{id}")
    public String updateEmployee(@PathVariable("id") long id, @Valid Employee employee, BindingResult result, Model model) {
        if (result.hasErrors()) {
            employee.setId(id); // Set the ID for the employee object
            return "new_employee"; // Return to the edit form with validation errors
        }
        
        // Set the ID for the employee object before saving it
        employee.setId(id);
        employeeService.save(employee);
        return "redirect:/"; // Redirect to the home page after successful update
    }

    // Endpoint to handle the form submission for creating or editing an employee
    @PostMapping("/save")
    public String processForm(@ModelAttribute Employee employee, BindingResult result) {
        if (result.hasErrors()) {
            // Handle validation errors
            return "new_employee"; // Return to the form with validation errors
        }
        
        // Check if the employee ID is not null to determine if it's an update or create operation
        if (employee.getId() != 0) {
            // Update the existing employee
            Employee existingEmployee = employeeService.findById(employee.getId());
            if (existingEmployee != null) {
                existingEmployee.setName(employee.getName());
                existingEmployee.setAge(employee.getAge());
                existingEmployee.setSalary(employee.getSalary());
                existingEmployee.setDepartment(employee.getDepartment());
                existingEmployee.setGender(employee.getGender());
                existingEmployee.setJoiningDate(employee.getJoiningDate());
                existingEmployee.setRetiringDate(employee.getRetiringDate());
                existingEmployee.setStatus(employee.getStatus());
                employeeService.save(existingEmployee);
            } else {
                // Handle the case where the employee with the given ID is not found
                // You can throw an exception, return an error page, or handle it based on your application's logic
                throw new EmployeeNotFoundException();
            }
        } else {
            // Create a new employee
            employeeService.save(employee);
        }
        
        return "redirect:/"; // Redirect to the homepage or any other appropriate page
    }

    
    @GetMapping("/confirmDelete")
    public String showDeleteConfirmationPage(@RequestParam("id") long employeeId, Model model) {
        model.addAttribute("employeeId", employeeId);
        return "delete_confirmation";
    }

    @GetMapping("/delete/{id}")
    public String deleteEmployee(@PathVariable("id") long id) {
        employeeService.deleteEmployeeById(id);
        return "redirect:/"; // Redirect to the home page after successful deletion
    }

    // Delete an employee by ID
//    @DeleteMapping(value = "/employee/{id}")
//    public void deleteEmployee(@PathVariable("id") int id) {
//        // Delete the employee by ID using the service
//    	employeeService.deleteEmployeeById(id); 
//    }
}
