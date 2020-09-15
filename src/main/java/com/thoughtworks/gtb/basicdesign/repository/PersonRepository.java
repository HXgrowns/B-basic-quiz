package com.thoughtworks.gtb.basicdesign.repository;

import com.thoughtworks.gtb.basicdesign.domain.Person;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
@Data
@AllArgsConstructor
public class PersonRepository {
    private List<Person> personList = new ArrayList<>();

    public PersonRepository() {
        this.personList.add(Person.builder()
                .id(1)
                .name("KAMIL")
                .age(24)
                .avatar("https://inews.gtimg.com/newsapp_match/0/3581582328/0")
                .description("Lorem ipsum dolor sit amet, consectetur adipisicing elit. Repellendus, non, dolorem, cumque distinctio magni quam expedita velit laborum sunt amet facere tempora ut fuga aliquam ad asperiores voluptatem dolorum! Quasi.")
                .build());
    }
}
