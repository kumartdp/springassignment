package com.luv2code.springboot.thymeleafdemo.service.serviceinterface;

import java.util.List;

import org.springframework.stereotype.Service;

import com.luv2code.springboot.thymeleafdemo.entity.Employee;
import org.springframework.data.domain.Page;
@Service
public interface EmployeeService {

	public List<Employee> findAll();
	
	public Employee findById(int theId);
	
	public void save(Employee theEmployee);
	
	public void deleteById(int theId);

	public List<Employee> searchBy(String theName);
	Page<Employee> findPaginated(int pageNo, int pageSize);
	
}
