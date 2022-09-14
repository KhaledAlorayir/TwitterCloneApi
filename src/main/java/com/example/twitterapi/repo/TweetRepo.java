package com.example.twitterapi.repo;

import com.example.twitterapi.entity.AppUser;
import com.example.twitterapi.entity.Tweet;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TweetRepo extends JpaRepository<Tweet,Long> {
    Long countByOwner(AppUser user);
    Slice<Tweet> findByOwnerOrderByCreatedAtDesc(AppUser user, Pageable pageable);
}
