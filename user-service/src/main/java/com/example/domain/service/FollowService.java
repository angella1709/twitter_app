package com.example.domain.service;

import com.example.domain.entity.Relationship;

import java.util.List;
import java.util.UUID;

public interface FollowService {
    Relationship follow(Relationship relationship);

    List<Relationship> getFollowing(UUID followerId);

    List<Relationship> getFollowers(UUID followedId);

    void unfollow(UUID followedId, UUID followerId);
}
