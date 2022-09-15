package com.example.resumeapiv1.security;

import com.example.resumeapiv1.models.Person;
import com.example.resumeapiv1.services.PersonService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PersonDetailsServiceImpl implements UserDetailsService {

    private PersonService personService;

    public PersonDetailsServiceImpl(PersonService personService) {
        this.personService = personService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Person> person = personService.findByUsername(username);
        if (person.isEmpty()) {
            throw new UsernameNotFoundException("user not found");
        }
        return new PersonDetail(person.get());
    }
}
