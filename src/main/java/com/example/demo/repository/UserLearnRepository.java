package com.example.demo.repository;

import com.example.demo.model.User;
import com.example.demo.model.UserLearn;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserLearnRepository extends JpaRepository<UserLearn, Long> {
    UserLearn findByUser(User user);
}
