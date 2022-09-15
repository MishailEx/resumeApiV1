package com.example.resumeapiv1.services;

import com.example.resumeapiv1.models.Person;
import com.example.resumeapiv1.models.Vacancy;
import com.example.resumeapiv1.repositories.VacancyRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class VacancyService {

    private VacancyRepository vacancyRepository;


    public VacancyService(VacancyRepository vacancyRepository) {
        this.vacancyRepository = vacancyRepository;
    }

    public List<Vacancy> findAll() {
        return StreamSupport.stream(vacancyRepository.findAll().spliterator(), false)
                .collect(Collectors.toList()) ;
    }

    public Vacancy findById(int id) {
        return vacancyRepository.findById(id).get();
    }

    @Transactional
    public Vacancy updateVacancy(int id, Person person) {
        Vacancy vacancy = findById(id);
        List<Person> responded = vacancy.getResponded();
        if (responded == null) {
            vacancy.setResponded(new ArrayList<>());
        }
        if (!responded.contains(person)) {
            vacancy.getResponded().add(person);
        }
        return vacancy;
    }
}
