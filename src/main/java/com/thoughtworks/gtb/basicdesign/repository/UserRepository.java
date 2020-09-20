package com.thoughtworks.gtb.basicdesign.repository;

import com.thoughtworks.gtb.basicdesign.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

}
