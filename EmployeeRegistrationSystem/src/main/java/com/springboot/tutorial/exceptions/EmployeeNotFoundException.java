package com.springboot.tutorial.exceptions;

public class EmployeeNotFoundException extends RuntimeException {

    public EmployeeNotFoundException() {
        super("Employee not found!");
    }
    
    @Override
    public String toString() {
        return "EmployeeNotFoundException: " + getMessage();
    }
}
