package com.netflix.demo.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.netflix.demo.entity.User;

public interface UserRepository extends JpaRepository<User,Long>{

     Optional<User> findByEmail(String email);
     boolean existsByEmail (String email);
}
