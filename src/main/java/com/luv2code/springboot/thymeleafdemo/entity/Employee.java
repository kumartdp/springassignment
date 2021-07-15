package com.luv2code.springboot.thymeleafdemo.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDate;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
@Entity
@Table(name="employee")
public class Employee {

	// define fields
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	@Size(min=3,message = "insufficient size")

	@Column(name="first_name")
	@NotNull(message="cannotbenull")
	@NotBlank(message="blanksnotaccepted")

	private String firstName;
	private String createdBy;
	private LocalDate createdAt;
	private LocalDate updatedAt;
	private String updatedBy;

	
	@Column(name="last_name")
	@Size(min=3,message = "insufficient size")
	@NotNull(message="cannotbenull")
	@NotBlank(message="blanksnotaccepted")
	private String lastName;
	
	@Column(name="email")
	@Pattern(regexp = "^[a-zA-Z]*[0-9]*@.*(.)com",message = "invalid email format")
	@NotNull(message="cannotbenull")
	@NotBlank(message="blanksnotaccepted")
	private String email;
	@ManyToOne(cascade={CascadeType.MERGE,CascadeType.DETACH,CascadeType.PERSIST,CascadeType.REFRESH},fetch = FetchType.LAZY)
	@JoinColumn(name="project_id")
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	@Getter(AccessLevel.PROTECTED)
	@Setter(AccessLevel.PROTECTED)
	private Project project;



    @JsonBackReference
	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}



	// define tostring
	public Employee(Integer id,String firstName, String LastName, String email)
	{
		this.firstName=firstName;
		this.lastName=LastName;
		this.email=email;
		this.id=id;

	}
	public Employee()
	{





	}


	@Override
	public String toString() {
		return "Employee [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", email=" + email + "]";
	}
		
}











