package com.example.resumeapiv1.repositories;

import com.example.resumeapiv1.models.Vacancy;
import org.springframework.data.repository.CrudRepository;

public interface VacancyRepository extends CrudRepository<Vacancy, Integer> {
}
