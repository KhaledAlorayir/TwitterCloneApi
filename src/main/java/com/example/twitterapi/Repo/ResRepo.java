package com.example.twitterapi.Repo;

import com.example.twitterapi.entity.AppUser;
import com.example.twitterapi.entity.Response;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResRepo extends JpaRepository<Response,Long> {
}
