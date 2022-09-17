package com.example.resumeapiv1.repositories;

import com.example.resumeapiv1.models.Person;
import com.example.resumeapiv1.models.Resume;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ResumeRepository extends CrudRepository<Resume, Integer> {
    List<Resume> findByPerson(Person person);
}
