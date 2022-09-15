package com.example.resumeapiv1.services;

import com.example.resumeapiv1.exception.PersonExistsException;
import com.example.resumeapiv1.exception.PersonNotFoundException;
import com.example.resumeapiv1.models.Person;
import com.example.resumeapiv1.repositories.PersonRepository;
import com.example.resumeapiv1.utill.PersonErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ExceptionHandler;

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
            throw new PersonExistsException();
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
        Person person1 = personRepository.findById(id).get();
        person1.setName(person.getName());
        person1.setPassword(person.getPassword());
        person1.setAge(person.getAge());
        person1.setPhoneNumber(person.getPhoneNumber());
        return personRepository.save(person1);
    }

    public Person findById(int id) {
        return personRepository.findById(id).orElseThrow(PersonNotFoundException::new);
    }

    public Optional<Person> findByUsername(String username) {
        return personRepository.findByName(username);
    }

    @ExceptionHandler(PersonExistsException.class)
    private ResponseEntity<PersonErrorResponse> handleException() {
        PersonErrorResponse response = new PersonErrorResponse("user with phone number exists");
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(PersonNotFoundException.class)
    private ResponseEntity<PersonErrorResponse> personNotFound() {
        PersonErrorResponse response = new PersonErrorResponse("user not found");
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
