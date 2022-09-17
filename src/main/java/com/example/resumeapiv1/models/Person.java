package com.example.resumeapiv1.models;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(name = "person")
@Data
public class Person {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    @Size(min = 3, max = 20, message = "number of characters from 3 to 20")
    private String name;

    @Column(name = "password")
    @Size(min = 5, message = "number of characters from 5")
    private String password;

    @Column(name = "age")
    @Min(value = 2, message = "must not be empty and be at least 2 digits")
    private int age;

    @Column(name = "phone_number")
    private int phoneNumber;

    @Column(name = "email")
    @NotBlank(message = "email must be not empty")
    private String email;

    public Person() {
    }
}