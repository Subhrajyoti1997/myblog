package com.blog.myblog5.repository;

import com.blog.myblog5.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByEmail(String email);
    Optional <User> findByUsername(String username);
    Optional <User> findByUsernameOrEmail(String username,String email);
    Boolean existsByEmail(String email);
    Boolean existsByUsername(String username);
}
