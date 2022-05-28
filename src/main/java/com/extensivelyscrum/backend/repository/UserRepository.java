package com.extensivelyscrum.backend.repository;

import com.extensivelyscrum.backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);

    void deleteByEmail(String email);
}
