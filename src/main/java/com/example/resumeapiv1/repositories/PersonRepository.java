package com.example.resumeapiv1.repositories;

import com.example.resumeapiv1.models.Person;
import com.example.resumeapiv1.models.Resume;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface PersonRepository extends CrudRepository<Person, Integer> {

    Optional<Person> findByName(String name);

    Optional<Person> findByPhoneNumber(int number);
}
