package com.luv2code.springboot.thymeleafdemo.service.serviceimpl;

import java.util.List;
import java.util.Optional;

import com.luv2code.springboot.thymeleafdemo.entity.Employee;
import com.luv2code.springboot.thymeleafdemo.service.serviceinterface.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.luv2code.springboot.thymeleafdemo.dao.ProjectRepository;
import com.luv2code.springboot.thymeleafdemo.entity.Project;
@Service
public class ProjectServiceImpl  implements ProjectService {
	
	
	private ProjectRepository projectRepository;
	
	@Autowired
	public ProjectServiceImpl(ProjectRepository theprojectRepository) {
		projectRepository = theprojectRepository;
	}
	
	@Override
	public List<Project> findAll() {

		return projectRepository.findAll();
	}
	public Project findById(int theId) {
		Optional<Project> result = projectRepository.findById(theId);


		Project project=null;

		if (result.isPresent()) {
			project=result.get();
		}
		else {
			// we didn't find the employee
			throw new RuntimeException("Did not find employee id - " + theId);
		}

		return project;
	}
	@Override
	public void save(Project theProject) {
		projectRepository.save(theProject);
	}
	@Override
	public Page<Project> findPaginated(int pageNo, int pageSize) {
		Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
		return this.projectRepository.findAll(pageable);
	}

}
