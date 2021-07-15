package com.luv2code.springboot.thymeleafdemo.service.serviceinterface;
import java.util.List;

import com.luv2code.springboot.thymeleafdemo.entity.Employee;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.luv2code.springboot.thymeleafdemo.entity.Project;
@Service
public interface ProjectService {

	public List<Project> findAll();
	public Project findById(int theId);
	public void save(Project theProject);
	Page<Project> findPaginated(int pageNo, int pageSize);
	
}
	
