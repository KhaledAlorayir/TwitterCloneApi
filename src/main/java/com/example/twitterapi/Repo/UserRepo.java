package com.example.twitterapi.Repo;

import com.example.twitterapi.entity.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<AppUser,Long> {
}
