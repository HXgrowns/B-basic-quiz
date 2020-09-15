package com.thoughtworks.gtb.basicdesign.repository;

import com.thoughtworks.gtb.basicdesign.domain.Education;
import com.thoughtworks.gtb.basicdesign.domain.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Repository
@Data
@AllArgsConstructor
public class UserRepository {
    private List<User> personList = Collections.synchronizedList(new ArrayList<>());

    public UserRepository() {
        this.personList.add(User.builder()
                .id(1)
                .name("KAMIL")
                .age(24)
                .avatar("https://inews.gtimg.com/newsapp_match/0/3581582328/0")
                .description("Lorem ipsum dolor sit amet, consectetur adipisicing elit. Repellendus, non, dolorem, cumque distinctio magni quam expedita velit laborum sunt amet facere tempora ut fuga aliquam ad asperiores voluptatem dolorum! Quasi.")
                .build());
    }

    public User findById(int id) {
        return personList.stream()
                .filter(person -> person.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public synchronized User createUser(User user) {
        personList.add(user);
        user.setId(personList.size());
        return user;
    }
}
