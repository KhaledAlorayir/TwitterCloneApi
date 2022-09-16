package com.example.twitterapi.service;

import com.example.twitterapi.dto.*;
import com.example.twitterapi.entity.AppUser;
import com.example.twitterapi.exception.DoesntExistsException;
import com.example.twitterapi.exception.ExistsException;
import com.example.twitterapi.exception.InvalidPageException;
import com.example.twitterapi.repo.*;
import com.example.twitterapi.shared.Helper;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepo userRepo;
    private final LikeRepo likeRepo;
    private final FollowsRepo followsRepo;
    private final TweetRepo tweetRepo;
    private ModelMapper mapper = new ModelMapper();
    private final int PAGE_LIMIT = 10;

    public UserDTO getUserDTO(AppUser user){
        Long followers_count = followsRepo.countByFollowed(user);
        Long following_count = followsRepo.countByFollower(user);
        Long tweets_count = tweetRepo.countByOwner(user);
        Long likes_count = likeRepo.countByUser(user);
        return new UserDTO(user.getId(),user.getUsername(),user.getEmail(),user.getCreated_at(),user.getBio(),user.getImg_url(),user.getRole().getId(), followers_count,following_count,tweets_count,likes_count);
    }
    public UserDTO changeUsername(UsernameDTO usernameDTO){
        userRepo.findByUsername(usernameDTO.getUsername()).ifPresent(user -> {
            throw new ExistsException("username");
        });

        AppUser user = userRepo.findById(Helper.getAuth()).get();

        user.setUsername(usernameDTO.getUsername());
        userRepo.save(user);
        return getUserDTO(user);
    }

    public Pagination<UserListDTO> searchUsers(String usernameQuery,int page_number) {
        page_number -= 1;

        if(page_number < 0){
            throw new InvalidPageException();
        }

        Pageable page = PageRequest.of(page_number,PAGE_LIMIT);
        Slice<AppUser> results = userRepo.findByUsernameContaining(usernameQuery,page);


        List<UserListDTO> data = Arrays.asList(mapper.map(results.getContent(),UserListDTO[].class));
        return new Pagination<>(data,results.hasNext(),results.hasPrevious(),page_number+1);
    }

    public UserDTO getUserbyID(long uid) {
        AppUser user = userRepo.findById(uid).orElseThrow(() -> new DoesntExistsException());
        return getUserDTO(user);
    }

    public UserDTO changBio(BioDTO bioDTO){
        AppUser user = userRepo.findById(Helper.getAuth()).get();
        user.setBio(bioDTO.getBio());
        userRepo.save(user);
        return getUserDTO(user);
    }

}
