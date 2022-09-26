package com.example.twitterapi.repo;

import com.example.twitterapi.entity.AppUser;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;
import java.util.Optional;

public interface UserRepo extends JpaRepository<AppUser,Long> {
    Optional<AppUser> findByEmail(String email);
    Optional<AppUser> findByUsername(String username);

    Slice<AppUser> findByUsernameContainingIgnoreCase(String username, Pageable pageable);

}
