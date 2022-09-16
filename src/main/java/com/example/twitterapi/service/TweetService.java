package com.example.twitterapi.service;

import com.example.twitterapi.dto.*;
import com.example.twitterapi.entity.AppUser;
import com.example.twitterapi.entity.Response;
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
        Tweet tweet = new Tweet(sendTweet.getContent(),user,false);
        tweetRepo.save(tweet);
        return new TweetDTO(tweet.getId(),tweet.getContent(),new UserListDTO(user.getId(),user.getUsername(),user.getImg_url()),tweet.getCreatedAt(),false,(long) 0,(long) 0);
    }

    public Message deleteTweet(long tweetId){
        Tweet tweet = tweetRepo.findById(tweetId).orElseThrow(() -> new DoesntExistsException());

        if(tweet.getOwner().getId() != Helper.getAuth()){
            throw new unAuthorizedException();
        }
        tweetRepo.deleteById(tweetId);
        return new Message("tweet has been deleted");
    }

    public Pagination<TweetDTO> getMyTweets(int page_number) {
        page_number -= 1;

        if(page_number < 0 ){
            throw new InvalidPageException();
        }
        AppUser user = userRepo.findById(Helper.getAuth()).get();


        Pageable page = PageRequest.of(page_number,PAGE_LIMIT);
        Slice<Tweet> results = tweetRepo.findByOwnerOrderByCreatedAtDesc(user,page);
        List<TweetDTO> tweets = results.stream().map(tweet -> getTweetDTO(tweet,user)).collect(Collectors.toList());

        return new Pagination<>(tweets,results.hasNext(),results.hasPrevious(),page_number+1);
    }

    public TweetDTO getTweetById(long id){
        Tweet tweet = tweetRepo.findById(id).orElseThrow(() -> new DoesntExistsException());
        return getTweetDTO(tweet,tweet.getOwner());
    }

    public Pagination<TweetDTO> getUserTweets(long uid,int page_number){
        page_number -= 1;
        if(page_number < 0 ){
            throw new InvalidPageException();
        }
        AppUser user = userRepo.findById(uid).orElseThrow(() -> new DoesntExistsException());
        Pageable page = PageRequest.of(page_number,PAGE_LIMIT);
        Slice<Tweet> results = tweetRepo.findByOwnerOrderByCreatedAtDesc(user,page);
        List<TweetDTO> tweets = results.stream().map(tweet -> getTweetDTO(tweet,user)).collect(Collectors.toList());
        return new Pagination<>(tweets,results.hasNext(),results.hasPrevious(),page_number+1);
    }

    /*Note
    * for the following route we need to write a SQL query to get the tweets from people the user follows
    * we can achieve this via 2 way, nested queries or a join
    * 1- nested query:
    * SELECT * FROM tweet
      WHERE owner_id IN
     (SELECT followed_id FROM follows
     WHERE follower_id = 9)
     ORDER BY created_at DESC

     * 2- join
     * SELECT tweet.id,tweet.content,tweet.created_at,tweet.owner_id
       FROM tweet JOIN follows
       ON tweet.owner_id = follows.followed_id
       where follows.follower_id = 9
       ORDER BY tweet.created_at DESC

     * the second way will be used since joins perform better.
     * which is the following when turned into JPQL
     * SELECT t FROM Tweet t JOIN Follows f ON t.owner = f.followed WHERE f.follower = ?1 ORDER BY t.createdAt DESC
     * we can use the native SQL query, but it would require additional work for pagination
     * //
     * currently this will also return tweets that are replies, but this can be changed with another check
     *  ***AND replay = false
     * //
     */
   public Pagination<TweetDTO> getTimeline(int page_number) {
       page_number -= 1;
       if(page_number < 0 ){
           throw new InvalidPageException();
       }
       Pageable page = PageRequest.of(page_number,PAGE_LIMIT);
       AppUser user = userRepo.getReferenceById(Helper.getAuth());
       Slice<Tweet> timeline = tweetRepo.getTweetsByYourFollowing(user,page);
       List<TweetDTO> parsed = timeline.stream().map(tweet -> getTweetDTO(tweet,tweet.getOwner())).collect(Collectors.toList());

       return new Pagination<>(parsed,timeline.hasNext(),timeline.hasPrevious(),page_number+1);
    }

   public TweetDTO ReplayToTweet(SendTweet sendTweet, long tweetId) {
       Tweet tweet = tweetRepo.findById(tweetId).orElseThrow(() -> new DoesntExistsException());
       AppUser user = userRepo.findById(Helper.getAuth()).get();

       Tweet replay = new Tweet(sendTweet.getContent(),user,true);
       Response response = new Response(tweet,replay);

       tweetRepo.save(replay);
       resRepo.save(response);
       return getTweetDTO(replay,user);
   }

   public Pagination<TweetDTO> getTweetReplies(long tweetId,int page_number){
       page_number -= 1;

       if(page_number < 0 ){
           throw new InvalidPageException();
       }

       Tweet tweet = tweetRepo.findById(tweetId).orElseThrow(() -> new DoesntExistsException());

       Pageable page = PageRequest.of(page_number,PAGE_LIMIT);
       Slice<Response> responses = resRepo.findByOriginal(tweet,page);
       List<TweetDTO> parsed = responses.stream().map(response -> getTweetDTO(response.getReplay(),response.getReplay().getOwner())).collect(Collectors.toList());
       return new Pagination<>(parsed,responses.hasNext(),responses.hasPrevious(),page_number+1);
   }

    public TweetDTO getTweetDTO(Tweet tweet, AppUser owner){
        return new TweetDTO(tweet.getId(),tweet.getContent(),new UserListDTO(owner.getId(),owner.getUsername(),owner.getImg_url()),tweet.getCreatedAt(),tweet.isReplay(),likeRepo.countByTweet(tweet),resRepo.countByOriginal(tweet));
    }


}
