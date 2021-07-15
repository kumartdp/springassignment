

package com.luv2code.springboot.thymeleafdemo.dao;

import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.luv2code.springboot.thymeleafdemo.entity.Project;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Integer> {

	// that's it ... no need to write any code LOL!
	
	// add a method to sort by last name


}