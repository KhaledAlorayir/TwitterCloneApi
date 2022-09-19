package com.example.twitterapi.repo;

import com.example.twitterapi.entity.Response;
import com.example.twitterapi.entity.Tweet;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ResRepo extends JpaRepository<Response,Long> {
    Long countByOriginal(Tweet tweet);
    Slice<Response> findByOriginal(Tweet tweet, Pageable pageable);

    Optional<Response> findByReplay(Tweet tweet);

    void deleteByReplay(Tweet tweet);
}
