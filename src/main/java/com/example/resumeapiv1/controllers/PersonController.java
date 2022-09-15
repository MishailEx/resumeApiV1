package com.example.resumeapiv1.controllers;

import com.example.resumeapiv1.dto.PersonDto;
import com.example.resumeapiv1.jwt.JWTConfig;
import com.example.resumeapiv1.models.Person;
import com.example.resumeapiv1.services.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequestMapping("/person")
public class PersonController {

    private final PersonService personService;
    private final JWTConfig jwtConfig;

    @Autowired
    public PersonController(PersonService personService, JWTConfig jwtConfig) {
        this.personService = personService;
        this.jwtConfig = jwtConfig;
    }

    @GetMapping("/{id}")
    public ResponseEntity<PersonDto> findById(@PathVariable int id) {
        return new ResponseEntity<>(
                convertToDto(this.personService.findById(id)),
                HttpStatus.OK
        );
    }

    @PostMapping
    public ResponseEntity create(@RequestBody @Valid Person person, HttpServletResponse res) {
        Person save = this.personService.save(person);
        jwtConfig.createToken(save.getName(), res);
        return new ResponseEntity<>(
                convertToDto(save),
                HttpStatus.CREATED
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<PersonDto> update(@PathVariable int id, @RequestBody @Valid Person person,
                                            BindingResult bindingResult) {
        return new ResponseEntity<>(
                convertToDto(this.personService.update(id, person)),
                HttpStatus.ACCEPTED
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        this.personService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    private PersonDto convertToDto(Person person) {
        return new PersonDto(person.getName(), person.getAge(), person.getPhoneNumber(), person.getEmail());
    }
}
