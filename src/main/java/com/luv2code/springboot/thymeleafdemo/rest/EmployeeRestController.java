package com.luv2code.springboot.thymeleafdemo.rest;

import com.luv2code.springboot.thymeleafdemo.dto.Employeedto;
import com.luv2code.springboot.thymeleafdemo.entity.Employee;
import com.luv2code.springboot.thymeleafdemo.entity.Project;
import com.luv2code.springboot.thymeleafdemo.service.serviceinterface.EmployeeService;
import com.luv2code.springboot.thymeleafdemo.service.serviceinterface.ProjectService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class EmployeeRestController {

    private EmployeeService employeeService;
    private ProjectService projectservice;
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    public EmployeeRestController(EmployeeService theEmployeeService,ProjectService theProjectService) {

        employeeService = theEmployeeService;
        projectservice=theProjectService;
    }
    @GetMapping("/employees")
    public List<Employeedto> listEmployees() {

        // get employees from db
        return  employeeService.findAll().stream().map(post -> modelMapper.map(post,Employeedto.class))
                .collect(Collectors.toList());





    }


    @GetMapping("/employees/{theId}")
    public Employee showFormForUpdate(@PathVariable int theId) {

        // get the employee from the service
        Employee theEmployee = employeeService.findById(theId);

        // set employee as a model attribute to pre-populate the form


        // send over to our form
        return theEmployee;
    }


    @RequestMapping(value="/employees/{theid}",method= RequestMethod.POST)
    public String saveEmployee(@PathVariable int theId) {
        Employee employee= employeeService.findById(theId);







            // save the employee
            employeeService.save(employee);


            // use a redirect to prevent duplicate submissions
            return "hello";

    }


    @PutMapping("/employees")
    public Employeedto update(@RequestBody Employeedto theEmployeedto) {



        Employee theemployee=modelMapper.map(theEmployeedto,Employee.class);

        employeeService.save(theemployee);

            return theEmployeedto;


    }

    @GetMapping("/employees/delete/{theId}")
    public String deletee(@PathVariable int theId) {

        // get the employee from the service
        employeeService.deleteById(theId);

        // set employee as a model attribute to pre-populate the form


        // send over to our form
        return "deleted employee with id "+theId;
    }

    @GetMapping("/projects")
    public List<Project> listProjects() {

        // get employees from db
        List<Project> theProjects = projectservice.findAll();

        // add to the spring model


        return theProjects;
    }
    @GetMapping("/projects/{theId}")
    public Project getProject(@PathVariable int theId) {

        // get the employee from the service
        Project theProject = projectservice.findById(theId);

        // set employee as a model attribute to pre-populate the form


        // send over to our form
        return theProject;
    }







}
