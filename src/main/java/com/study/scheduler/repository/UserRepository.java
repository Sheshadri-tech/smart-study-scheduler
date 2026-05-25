package com.study.scheduler.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.study.scheduler.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail(String email);
}