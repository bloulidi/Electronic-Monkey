package com.stackroute.userservice.repository;

import com.stackroute.userservice.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {

    Optional<User> findByEmail(String email);
    List<User> findByName(String name);
    boolean existsByEmail(String email);
    List<User> findByAdmin(boolean admin);
}