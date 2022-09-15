package com.example.resumeapiv1.dto;

import lombok.Data;

@Data
public class PersonDto {

    private String name;

    private int age;

    private int phoneNumber;

    private String email;

    public PersonDto(String name, int age, int phoneNumber, String email) {
        this.name = name;
        this.age = age;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }
}
