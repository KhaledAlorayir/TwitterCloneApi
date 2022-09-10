package com.example.twitterapi.repo;

import com.example.twitterapi.entity.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepo extends JpaRepository<AppUser,Long> {
    Optional<AppUser> findByEmail(String email);
    Optional<AppUser> findByUsername(String username);

}