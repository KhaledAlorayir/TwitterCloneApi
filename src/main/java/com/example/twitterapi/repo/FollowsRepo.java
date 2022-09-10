package com.example.twitterapi.repo;

import com.example.twitterapi.entity.Follows;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FollowsRepo extends JpaRepository<Follows,Long> {
}
