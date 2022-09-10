package com.example.twitterapi.repo;

import com.example.twitterapi.entity.Response;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResRepo extends JpaRepository<Response,Long> {
}
