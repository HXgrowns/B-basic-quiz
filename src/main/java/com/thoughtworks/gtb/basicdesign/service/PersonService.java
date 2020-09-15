package com.thoughtworks.gtb.basicdesign.service;

import com.thoughtworks.gtb.basicdesign.domain.Person;
import com.thoughtworks.gtb.basicdesign.repository.PersonRepository;
import org.springframework.stereotype.Service;

@Service
public class PersonService {
    private PersonRepository personRepository;

    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public Person findById(int id) {
        return personRepository.getPersonList()
                .stream()
                .filter(person -> person.getId() == id)
                .findFirst()
                .orElse(null);
    }
}
