package com.thoughtworks.gtb.basicdesign.controller;

import com.thoughtworks.gtb.basicdesign.domain.Person;
import com.thoughtworks.gtb.basicdesign.service.PersonService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/persons")
public class PersonController {
    private PersonService personService;

    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Person> findById(@PathVariable int id) {
        return ResponseEntity.ok().body(personService.findById(id));
    }
}
