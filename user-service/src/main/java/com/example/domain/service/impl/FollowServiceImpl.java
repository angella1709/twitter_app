package com.example.domain.service.impl;

import com.example.domain.entity.Relationship;
import com.example.domain.exception.RelationshipNotFound;
import com.example.domain.service.FollowService;
import com.example.gateway.repository.RelationshipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class FollowServiceImpl implements FollowService {

    @Autowired
    private RelationshipRepository relationshipRepository;

    @Override
    public Relationship follow(Relationship relationship) {
        return relationshipRepository.save(relationship);
    }

    @Override
    public List<Relationship> getFollowing(UUID userID) {
        return relationshipRepository.findByFollowerId(userID);
    }

    @Override
    public List<Relationship> getFollowers(UUID userID) {
        return relationshipRepository.findByFollowedId(userID);
    }

    @Override
    public void unfollow(UUID followedId, UUID followerId) {
        var follow = relationshipRepository.findByFollowerIdAndFollowedId(followedId, followerId)
                .orElseThrow(() -> new RelationshipNotFound("Follow not found"));
        relationshipRepository.delete(follow);
    }

}
