
package com.luv2code.springboot.thymeleafdemo.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import com.luv2code.springboot.thymeleafdemo.entity.Employee;
import com.luv2code.springboot.thymeleafdemo.entity.Project;
import com.luv2code.springboot.thymeleafdemo.service.serviceinterface.EmployeeService;
import com.luv2code.springboot.thymeleafdemo.service.serviceinterface.ProjectService;

import javax.validation.Valid;

@Controller
@RequestMapping("/employees")
public class EmployeeController {

	private EmployeeService employeeService;
	private ProjectService projectservice;
	
	@Autowired
	public EmployeeController(EmployeeService theEmployeeService,ProjectService theProjectService) {
		
		employeeService = theEmployeeService;
		projectservice=theProjectService;
	}
	
	// add mapping for "/list"

	@GetMapping("/list/{pageNo}")
	public String listEmployees(@PathVariable(value = "pageNo") int pageNo, Model theModel) {
		
		// get employees from db
		int pageSize = 5;

		Page<Employee> page = employeeService.findPaginated(pageNo, pageSize);

		List<Employee> listEmployees = page.getContent();
		List<Employee>  employees = employeeService.findAll();
		theModel.addAttribute("currentPage", pageNo);
		List<Employee> employees1= employees.subList(5*(pageNo-1),Math.min((5*(pageNo-1)+5),employees.size()));
		theModel.addAttribute("employees", employees1);
		int count=employees.size();
		theModel.addAttribute("totalPages", ((count)/5)+1);
		theModel.addAttribute("totalItems", count);
		return "/employees/list-employees";
	}
	
	@GetMapping("/showFormForAdd")
	public String showFormForAdd(Model theModel) {
		
		// create model attribute to bind form data
		Employee theEmployee = new Employee();


		
		theModel.addAttribute("employee", theEmployee);
		
		return "/employees/employee-form";
	}

	@GetMapping("/showFormForUpdate")
	public String showFormForUpdate(@RequestParam("employeeId") int theId,
									Model theModel) {
		
		// get the employee from the service
		Employee theEmployee = employeeService.findById(theId);
		
		// set employee as a model attribute to pre-populate the form
		theModel.addAttribute("employee", theEmployee);
		
		// send over to our form
		return "/employees/employee-form-update";
	}
	
	
	@PostMapping("/save")
	public String saveEmployee(@Valid  @ModelAttribute("employee") Employee theEmployee, Errors errors,Model themodel) {
		if (null != errors && errors.getErrorCount() > 0) {
			return "/employees/employee-form";
		}
		else {
			List<Employee> theEmployees = employeeService.findAll();
			for(Employee e:theEmployees)
			{
				if(e.getEmail().equals(theEmployee.getEmail()))

				{
					boolean existing_user=true;
					themodel.addAttribute("existing_user",existing_user);

					return "/employees/employee-form";

				}
			}


			// save the employee
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			String currentPrincipalName = authentication.getName();
			theEmployee.setCreatedBy(currentPrincipalName);
			theEmployee.setCreatedAt(LocalDate.now());



			employeeService.save(theEmployee);

			// use a redirect to prevent duplicate submissions
			return "redirect:/employees/list/1";
		}
	}
	@PostMapping("/saveafterupdate")
	public String saveEmployeeAfterupdate(@Valid  @ModelAttribute("employee") Employee theEmployee, Errors errors) {
		if (null != errors && errors.getErrorCount() > 0) {
			return "/employees/employee-form";
		}
		else {
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			String currentPrincipalName = authentication.getName();
			theEmployee.setUpdatedBy(currentPrincipalName);
			theEmployee.setUpdatedAt(LocalDate.now());




			// save the employee
			employeeService.save(theEmployee);

			// use a redirect to prevent duplicate submissions
			return "redirect:/employees/list/1";
		}
	}



	@GetMapping("/delete")
	public String delete(@RequestParam("employeeId") int theId) {
		
		// delete the employee
		employeeService.deleteById(theId);
		
		// redirect to /employees/list
		return "redirect:/employees/list/1";
		
	}
	
	@GetMapping("/search")
	public String delete(@RequestParam("employeeName") String theName,
						 Model theModel) {
		
		// delete the employee
		List<Employee> theEmployees = employeeService.searchBy(theName);
		
		// add to the spring model
		theModel.addAttribute("employees", theEmployees);
		
		// send to /employees/list
		return "/employees/list-employees";
		
	}
	@GetMapping("/viewprojects/{pageNo}")
	public String assignproject(@PathVariable(value = "pageNo") int pageNo,Model theModel) {

		int pageSize = 5;

		Page<Project> page = projectservice.findPaginated(pageNo, pageSize);

		List<Project> listprojects = page.getContent();
		List<Project>  projects = projectservice.findAll();
		theModel.addAttribute("currentPage", pageNo);
		List<Project> projects1= projects.subList(5*(pageNo-1),Math.min((5*(pageNo-1)+5),projects.size()));
		theModel.addAttribute("projects", projects1);
		int count=projects.size();
		theModel.addAttribute("totalPages", ((count)/5)+1);
		theModel.addAttribute("totalItems", count);
		return "/employees/list-projects";
		
	}
	@GetMapping("/addemployee")
	public String addEmployeeList(@RequestParam("projectid") int theprojectid,
									Model theModel) {
		Project p=projectservice.findById(theprojectid);





		theModel.addAttribute("currentprojectid",theprojectid);
		List<Employee> tempemployees=new ArrayList<Employee>();
		List<Employee> theEmployees = employeeService.findAll();
		for(Employee emp:theEmployees)
		{
			if(emp.getProject()==null ||(emp.getProject()!=null  && (!emp.getProject().getProjectname().equals(p.getProjectname()))))
			{
				tempemployees.add(emp);

			}

		}
		theModel.addAttribute("employeesforproject",tempemployees);


		return "/employees/employeesforproject";
	}
	@GetMapping("/addemployeetoproject")
	public String addEmployeeToProject(@RequestParam("employeeId") int theemloyeeid,@RequestParam("projectid") int theprojectid,Model theModel)
	{





		Employee theEmployee = employeeService.findById(theemloyeeid);
		Project p=projectservice.findById(theprojectid);
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String currentPrincipalName = authentication.getName();
		p.setUpdatedBy(currentPrincipalName);
		p.setUpdatedAt(LocalDate.now());
		projectservice.save(p);
		theEmployee.setProject(p);
		employeeService.save(theEmployee);
		return "redirect:/employees/viewprojects/1";


	}
	@GetMapping("/viewemployeesofproject")
	public String viewEmployeesOfProject(@RequestParam("projectid") int theprojectid,Model theModel)
	{





		Project p=projectservice.findById(theprojectid);
		List<Employee>  emps=p.getEmployees();
		theModel.addAttribute("projectemployees",emps);

		return "employees/viewprojectemployees.html";


	}
}


















