package com.example.twitterapi.Repo;

import com.example.twitterapi.entity.AppUser;
import com.example.twitterapi.entity.Like;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeRepo extends JpaRepository<Like,Long> {
}
