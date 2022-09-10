package com.example.twitterapi.repo;

import com.example.twitterapi.entity.AppUser;
import com.example.twitterapi.entity.Follows;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FollowsRepo extends JpaRepository<Follows,Long> {
    Long countByFollower(AppUser user);
    Long countByFollowed(AppUser user);
}
