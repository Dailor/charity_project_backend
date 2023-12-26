package com.charity.charity_project_backend.auth.repositories;

import com.charity.charity_project_backend.auth.models.Role;
import com.charity.charity_project_backend.auth.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByEmail(String email);

    Integer countUserByRole(Role role);
}
