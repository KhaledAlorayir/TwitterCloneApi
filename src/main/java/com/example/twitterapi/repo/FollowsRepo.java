package com.example.twitterapi.repo;

import com.example.twitterapi.entity.AppUser;
import com.example.twitterapi.entity.Follows;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FollowsRepo extends JpaRepository<Follows,Long> {
    Long countByFollower(AppUser user);
    Long countByFollowed(AppUser user);
    Optional<Follows> findByFollowerAndFollowed(AppUser from, AppUser to);
    Slice<Follows> findByFollowedOrderByCreatedAtDesc(AppUser user, Pageable pageable);
    Slice<Follows> findByFollowerOrderByCreatedAtDesc(AppUser user, Pageable pageable);
}
