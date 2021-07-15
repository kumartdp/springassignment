package com.luv2code.springboot.thymeleafdemo;

import com.luv2code.springboot.thymeleafdemo.dao.EmployeeRepository;
import com.luv2code.springboot.thymeleafdemo.entity.Employee;
import com.luv2code.springboot.thymeleafdemo.service.serviceinterface.EmployeeService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
public class EmployeeServiceTest {
    @Autowired
    private EmployeeService service;

    @MockBean
    private EmployeeRepository repository;






    @Test
    public void saveUserTest() {
        Employee employee = new Employee(1,"ravi", "kishore", "ravikishore@mail.com");
        service.save(employee);
        verify(repository, times(1)).save(employee);
    }
    @Test
    public void getUserbyIdTest() {
        Integer id = 1;
        Employee emp=new Employee(1,"a", "b", "ab@mail.com");
        when(repository.findById(id)) .thenReturn(java.util.Optional.of(emp));
        assertEquals(emp, service.findById(id));
    }
    @Test
    public void getUsersTest() {




        when(repository.findAllByOrderByLastNameAsc()).thenReturn(Stream
                .of(new Employee(2,"Danile", "scs", "a@gmail.com"),new Employee(3,"abc", "dsf", "b@gmail.com")).collect(Collectors.toList()));
        assertEquals(2, service.findAll().size());
    }

    @Test
    public void deleteUserTest() {
       Integer id=2;
       when(repository.findById(id)) .thenReturn(java.util.Optional.of(new Employee(1,"ramesh", "kumar", "rameshkumar@mail.com")));
       service.deleteById(id);
       verify(repository, times(1)).deleteById(id);
    }
}
