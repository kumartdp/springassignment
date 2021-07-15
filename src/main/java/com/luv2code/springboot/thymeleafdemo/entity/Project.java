package com.luv2code.springboot.thymeleafdemo.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="project")
public class Project {
	
	
	@Id
	@GeneratedValue
	private int projectid;
	@Column(name="projectname")
	private String projectname;
	@Column(name="projectdetails")
	private String projectdetails;
	@OneToMany(cascade={CascadeType.MERGE,CascadeType.DETACH,CascadeType.PERSIST,CascadeType.REFRESH},mappedBy = "project",fetch = FetchType.LAZY)
	@Getter(AccessLevel.PROTECTED)
	@Setter(AccessLevel.PROTECTED)
	private List <Employee> employees;
	private String updatedBy;
	private LocalDate updatedAt;



	@JsonManagedReference
	public List<Employee> getEmployees() {
		return employees;
	}
	public void setEmployees(List<Employee> employees) {
		this.employees = employees;
	}

	public void add(Employee e)
	{
		if(employees==null)
		{
			employees=new ArrayList<>();
		}
		employees.add(e);
		e.setProject(this);
	}
	
	
	
	

}
