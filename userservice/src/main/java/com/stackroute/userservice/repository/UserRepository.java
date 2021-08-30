package com.stackroute.userservice.repository;

import com.stackroute.userservice.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    User findByEmail(String email);
    List<User> findByName(String name);
    List<User> findByAdmin(boolean admin);
    boolean existsByEmail(String email);
    User findByEmailAndPassword(String email, String password);
}