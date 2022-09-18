package com.example.twitterapi.service;

import com.example.twitterapi.dto.Message;
import com.example.twitterapi.dto.Pagination;
import com.example.twitterapi.dto.TweetDTO;
import com.example.twitterapi.dto.UserListDTO;
import com.example.twitterapi.entity.AppUser;
import com.example.twitterapi.entity.Like;
import com.example.twitterapi.entity.Tweet;
import com.example.twitterapi.exception.DoesntExistsException;
import com.example.twitterapi.exception.InvalidPageException;
import com.example.twitterapi.repo.LikeRepo;
import com.example.twitterapi.repo.TweetRepo;
import com.example.twitterapi.repo.UserRepo;
import com.example.twitterapi.shared.Helper;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LikeService {
    private final LikeRepo likeRepo;
    private final TweetRepo tweetRepo;
    private final UserRepo userRepo;
    private final ModelMapper mapper = new ModelMapper();
    private final int PAGE_LIMIT = 10;

    private final TweetService tweetService;


    public Message LikeTweet(long tweetId){
        Tweet tweet = tweetRepo.findById(tweetId).orElseThrow(() -> new DoesntExistsException());
        AppUser user = userRepo.getReferenceById(Helper.getAuth());
        Optional<Like> exists = likeRepo.findByUserAndTweet(user,tweet);

        if(exists.isPresent()){
            likeRepo.deleteById(exists.get().getId());
            return new Message("tweet has been unliked!");
        }

        Like like = new Like(user,tweet);
        likeRepo.save(like);
        return new Message("tweet has been liked!");
    }

    public Pagination<UserListDTO> getTweetLikes(long tweetId, int page_number) {
        page_number -= 1;
        if(page_number < 0){
            throw new InvalidPageException();
        }

        Tweet tweet = tweetRepo.findById(tweetId).orElseThrow(() -> new DoesntExistsException());
        Pageable page = PageRequest.of(page_number,PAGE_LIMIT);
        Slice<Like> likes = likeRepo.findByTweetOrderByCreatedAtDesc(tweet,page);
        List<UserListDTO> users = likes.stream().map(like -> mapper.map(like.getUser(),UserListDTO.class)).collect(Collectors.toList());
        return new Pagination<>(users,likes.hasNext(),likes.hasPrevious(),page_number+1);
    }

    public Pagination<TweetDTO> getMyLikes(int page_number){
        page_number -= 1;
        if(page_number < 0){
            throw new InvalidPageException();
        }

        AppUser user = userRepo.findById(Helper.getAuth()).get();
        Pageable page = PageRequest.of(page_number,PAGE_LIMIT);
        Slice<Like> likes = likeRepo.findByUserOrderByCreatedAtDesc(user,page);
        List<TweetDTO> tweets = likes.stream().map(like -> tweetService.getTweetDTO(like.getTweet(),user)).collect(Collectors.toList());
        return new Pagination<>(tweets,likes.hasNext(),likes.hasPrevious(),page_number+1);
    }

    public Pagination<TweetDTO> getUserLikes(long uid,int page_number){
        page_number -= 1;
        if(page_number < 0){
            throw new InvalidPageException();
        }

        AppUser user = userRepo.findById(uid).orElseThrow(() -> new DoesntExistsException());
        Pageable page = PageRequest.of(page_number,PAGE_LIMIT);
        Slice<Like> likes = likeRepo.findByUserOrderByCreatedAtDesc(user,page);
        List<TweetDTO> tweets = likes.stream().map(like -> tweetService.getTweetDTO(like.getTweet(),user)).collect(Collectors.toList());
        return new Pagination<>(tweets,likes.hasNext(),likes.hasPrevious(),page_number+1);
    }

}
