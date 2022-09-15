package com.example.resumeapiv1.controllers;

import com.example.resumeapiv1.models.Person;
import com.example.resumeapiv1.models.Vacancy;
import com.example.resumeapiv1.services.PersonService;
import com.example.resumeapiv1.services.VacancyService;
import com.example.resumeapiv1.utill.AuthPerson;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/vacancy")
public class VacancyController {

    private VacancyService vacancyService;

    public VacancyController(VacancyService vacancyService) {
        this.vacancyService = vacancyService;
    }

    @GetMapping
    public ResponseEntity<List<Vacancy>> findAll() {
        return new ResponseEntity<>(
                vacancyService.findAll(),
                HttpStatus.OK
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<Vacancy> findById(@PathVariable int id) {
        return new ResponseEntity<>(
                vacancyService.findById(id),
                HttpStatus.OK
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<Vacancy> update(@PathVariable int id) {
        Person person = AuthPerson.getAuthPerson();
        return new ResponseEntity<>(
                vacancyService.updateVacancy(id, person),
                HttpStatus.ACCEPTED
        );
    }


}
