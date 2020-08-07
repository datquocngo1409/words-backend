package com.example.demo.repository;

import com.example.demo.model.CustomUser;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomUserRepository extends CrudRepository<CustomUser, Long> {
    CustomUser findByUsername(String username);
}
