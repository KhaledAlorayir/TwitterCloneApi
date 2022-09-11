package com.example.twitterapi.service;

import com.example.twitterapi.dto.Message;
import com.example.twitterapi.dto.Pagination;
import com.example.twitterapi.dto.UserListDTO;
import com.example.twitterapi.entity.AppUser;
import com.example.twitterapi.entity.Follows;
import com.example.twitterapi.exception.DoesntExistsException;
import com.example.twitterapi.exception.FollowSelfException;
import com.example.twitterapi.exception.InvalidPageException;
import com.example.twitterapi.repo.FollowsRepo;
import com.example.twitterapi.repo.UserRepo;
import com.example.twitterapi.shared.Helper;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FollowService {
    private final FollowsRepo followsRepo;
    private final UserRepo userRepo;

    private ModelMapper mapper = new ModelMapper();

    private final int PAGE_LIMIT = 10;

    public Message follow(long uid) {
        AppUser to = userRepo.findById(uid).orElseThrow(() -> new DoesntExistsException());
        AppUser from = userRepo.getReferenceById(Helper.getAuth());

        if(uid == Helper.getAuth()){
            throw new FollowSelfException();
        }

        Optional<Follows> Following = followsRepo.findByFollowerAndFollowed(from,to);

        if(Following.isPresent()){
                followsRepo.deleteById(Following.get().getId());
                return new Message("unfollowed");
        }

        Follows follow = new Follows();
        follow.setFollower(from);
        follow.setFollowed(to);
        followsRepo.save(follow);
        return new Message("followed");
    }

    public Pagination<UserListDTO> getMyFollowers(int page_number) {
        page_number -= 1;
        AppUser u = userRepo.getReferenceById(Helper.getAuth());

        long result_count = followsRepo.countByFollowed(u);
        int page_count = (int)Math.ceil((double) result_count/PAGE_LIMIT);

        if(page_number < 0 || (page_number >= page_count && page_count != 0)){
            throw new InvalidPageException();
        }

        Pageable page = PageRequest.of(page_number,PAGE_LIMIT);
        List<Follows> result = followsRepo.findByFollowed(u,page);
        List<UserListDTO> followers = result.stream().map(follows -> mapper.map(follows.getFollower(),UserListDTO.class)).collect(Collectors.toList());

        return new Pagination<>(followers,page_number+1,page_count);
    }

    public Pagination<UserListDTO> getMyFollowing(int page_number){
        page_number -= 1;
        AppUser u = userRepo.getReferenceById(Helper.getAuth());
        long result_count = followsRepo.countByFollower(u);
        int page_count = (int)Math.ceil((double) result_count/PAGE_LIMIT);

        if(page_number < 0 || (page_number >= page_count && page_count != 0)){
            throw new InvalidPageException();
        }

        Pageable page = PageRequest.of(page_number,PAGE_LIMIT);
        List<Follows> result = followsRepo.findByFollower(u,page);
        List<UserListDTO> following = result.stream().map(follows -> mapper.map(follows.getFollowed(),UserListDTO.class)).collect(Collectors.toList());
        return new Pagination<>(following,page_number+1,page_count);

    }

    //TODO secConfig for the next 2 routes


}
