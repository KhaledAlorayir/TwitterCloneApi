package com.example.twitterapi.repo;

import com.example.twitterapi.entity.AppUser;
import com.example.twitterapi.entity.Like;
import com.example.twitterapi.entity.Tweet;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikeRepo extends JpaRepository<Like,Long> {
    Long countByUser(AppUser user);
    Long countByTweet(Tweet tweet);

    Optional<Like> findByUserAndTweet(AppUser user, Tweet tweet);
    Slice<Like> findByTweetOrderByCreatedAtDesc(Tweet tweet, Pageable pageable);
    Slice<Like> findByUserOrderByCreatedAtDesc(AppUser user, Pageable pageable);

}
