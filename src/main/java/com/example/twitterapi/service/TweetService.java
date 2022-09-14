package com.example.twitterapi.service;

import com.example.twitterapi.dto.*;
import com.example.twitterapi.entity.AppUser;
import com.example.twitterapi.entity.Tweet;
import com.example.twitterapi.exception.DoesntExistsException;
import com.example.twitterapi.exception.InvalidPageException;
import com.example.twitterapi.exception.unAuthorizedException;
import com.example.twitterapi.repo.LikeRepo;
import com.example.twitterapi.repo.ResRepo;
import com.example.twitterapi.repo.TweetRepo;
import com.example.twitterapi.repo.UserRepo;
import com.example.twitterapi.shared.Helper;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TweetService {

    private final UserRepo userRepo;
    private final TweetRepo tweetRepo;
    private final LikeRepo likeRepo;
    private final ResRepo resRepo;
    private final int PAGE_LIMIT = 10;


    public TweetDTO Tweet(SendTweet sendTweet) {
        AppUser user = userRepo.findById(Helper.getAuth()).get();
        Tweet tweet = new Tweet(sendTweet.getContent(),user);
        tweetRepo.save(tweet);
        return new TweetDTO(tweet.getId(),tweet.getContent(),new UserListDTO(user.getId(),user.getUsername(),user.getImg_url()),tweet.getCreatedAt(),(long) 0,(long) 0);
    }

    public Message deleteTweet(long tweetId){
        Tweet tweet = tweetRepo.findById(tweetId).orElseThrow(() -> new DoesntExistsException());

        if(tweet.getOwner().getId() != Helper.getAuth()){
            throw new unAuthorizedException();
        }
        tweetRepo.deleteById(tweetId);
        return new Message("tweet has been deleted");
    }

    public Pagination2<TweetDTO> getMyTweets(int page_number) {
        page_number -= 1;

        AppUser user = userRepo.findById(Helper.getAuth()).get();

        if(page_number < 0 ){
            throw new InvalidPageException();
        }
        Pageable page = PageRequest.of(page_number,PAGE_LIMIT);
        Slice<Tweet> results = tweetRepo.findByOwnerOrderByCreatedAtDesc(user,page);
        List<TweetDTO> tweets = results.stream().map(tweet -> getTweetDTO(tweet,user)).collect(Collectors.toList());

        return new Pagination2<>(tweets,results.hasNext(),results.hasPrevious(),page_number+1);
    }

    public TweetDTO getTweetById(long id){
        Tweet tweet = tweetRepo.findById(id).orElseThrow(() -> new DoesntExistsException());
        return getTweetDTO(tweet,tweet.getOwner());
    }

    public TweetDTO getTweetDTO(Tweet tweet, AppUser owner){
        return new TweetDTO(tweet.getId(),tweet.getContent(),new UserListDTO(owner.getId(),owner.getUsername(),owner.getImg_url()),tweet.getCreatedAt(),likeRepo.countByTweet(tweet),resRepo.countByOriginal(tweet));
    }

    //todo 1 update all routes to new pagination system
    //todo 2 followers/following desc

}
