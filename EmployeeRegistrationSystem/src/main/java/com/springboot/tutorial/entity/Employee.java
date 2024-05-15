package com.springboot.tutorial.entity;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Entity
@Table(name = "employee")
@Data
public class Employee {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private long id;

	@Column(name = "name")
	private String name;

	@Column(name = "age")
	private int age;

	@Column(name = "gender")
	private String gender;

	@Column(name = "Salary")
	private double salary;

	@Column(name = "joiningDate")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate joiningDate;

	@Column(name = "retiringDate")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate retiringDate;

	@Column(name = "department")
	private String department;

	@Column(name = "Status")
	private String status;   
}
