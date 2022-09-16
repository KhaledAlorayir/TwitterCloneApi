package com.example.twitterapi.repo;

import com.example.twitterapi.entity.AppUser;
import com.example.twitterapi.entity.Tweet;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TweetRepo extends JpaRepository<Tweet,Long> {
    Long countByOwner(AppUser user);
    Slice<Tweet> findByOwnerOrderByCreatedAtDesc(AppUser user, Pageable pageable);
    @Query("SELECT t FROM Tweet t JOIN Follows f ON t.owner = f.followed WHERE f.follower = ?1 ORDER BY t.createdAt DESC")
    Slice<Tweet> getTweetsByYourFollowing(AppUser user,Pageable pageable);
}
