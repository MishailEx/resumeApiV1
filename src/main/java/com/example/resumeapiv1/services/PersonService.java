package com.example.resumeapiv1.services;

import com.example.resumeapiv1.models.Person;
import com.example.resumeapiv1.repositories.PersonRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
public class PersonService {

    private PersonRepository personRepository;
    private PasswordEncoder passwordEncoder;

    public PersonService(PersonRepository personRepository, PasswordEncoder passwordEncoder) {
        this.personRepository = personRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public Person save(Person person) {
        Optional<Person> personRsl = personRepository.findByName(person.getName());
        if (personRsl.isPresent()) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT, "a person with that name already exists");
        }
        person.setPassword(passwordEncoder.encode(person.getPassword()));
        return personRepository.save(person);
    }

    @Transactional
    public void deleteById(int id) {
        personRepository.deleteById(id);
    }

    @Transactional
    public Person update(int id, Person person) {
        Person rsl = personRepository.findById(id).orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "a person not found"));
        rsl.setName(person.getName());
        rsl.setPassword(person.getPassword());
        rsl.setAge(person.getAge());
        rsl.setPhoneNumber(person.getPhoneNumber());
        return personRepository.save(rsl);
    }

    public Person findById(int id) {
        return personRepository.findById(id).orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "a person not found"));
    }

    public Optional<Person> findByUsername(String username) {
        return personRepository.findByName(username);
    }
}
