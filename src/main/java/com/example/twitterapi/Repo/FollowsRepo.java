package com.example.twitterapi.Repo;

import com.example.twitterapi.entity.AppUser;
import com.example.twitterapi.entity.Follows;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FollowsRepo extends JpaRepository<Follows,Long> {
}
