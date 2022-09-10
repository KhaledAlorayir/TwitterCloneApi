package com.example.twitterapi.Repo;

import com.example.twitterapi.entity.AppUser;
import com.example.twitterapi.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepo extends JpaRepository<Role,Long> {
}
