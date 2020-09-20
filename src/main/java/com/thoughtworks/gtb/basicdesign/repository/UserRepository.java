package com.thoughtworks.gtb.basicdesign.repository;

import com.thoughtworks.gtb.basicdesign.domain.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Repository
@Data
@AllArgsConstructor
public class UserRepository {
    private List<User> personList = Collections.synchronizedList(new ArrayList<>());
    private static AtomicLong atomicLong;
    private static final Long ID_INITIAL_VALUE = 1L;
    public UserRepository() {
        this.personList.add(User.builder()
                .id(ID_INITIAL_VALUE)
                .name("KAMIL")
                .age(24L)
                .avatar("https://inews.gtimg.com/newsapp_match/0/3581582328/0")
                .description("Lorem ipsum dolor sit amet, consectetur adipisicing elit. Repellendus, non, dolorem, cumque distinctio magni quam expedita velit laborum sunt amet facere tempora ut fuga aliquam ad asperiores voluptatem dolorum! Quasi.")
                .build());
        atomicLong = new AtomicLong(ID_INITIAL_VALUE);
    }

    public User findById(Long id) {
        return personList.stream()
                .filter(person -> person.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public static AtomicLong getAtomicLong() {
        return atomicLong;
    }

    public synchronized User createUser(User user) {
        personList.add(user);
        user.setId(atomicLong.incrementAndGet());
        return user;
    }
}
