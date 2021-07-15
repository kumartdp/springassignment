package com.luv2code.springboot.thymeleafdemo.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public @Data
@AllArgsConstructor
@NoArgsConstructor
class Employeedto {
    private int id;
    private String firstName;
    private String lastName;
    private String email;


}