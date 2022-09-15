package com.example.resumeapiv1.utill;

import com.example.resumeapiv1.models.Person;
import com.example.resumeapiv1.repositories.PersonRepository;
import lombok.Data;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Data
@Component
public class AuthPerson {

    private static PersonRepository repository;

    public AuthPerson(PersonRepository repository) {
        AuthPerson.repository = repository;
    }

    public static Person getAuthPerson() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return repository.findByName(authentication.getName()).get();
    }
}
