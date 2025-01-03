package com.example.domain.service;

import com.example.domain.entity.Tweet;
import com.example.exception.TweetNotFound;
import com.example.gateway.TweetRepository;
import com.example.gateway.rest.datacontract.TweetDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class TweetServiceImpl implements TweetService {

    @Autowired
    private TweetRepository tweetRepository;

    @Override
    public Page<Tweet> listTweetsByUser(String userId, Pageable pageable) {
        Page<Tweet> tweets = tweetRepository.findByUserId(userId, pageable);
        if (tweets.isEmpty()) {
            throw new TweetNotFound("Tweets not found - user " + userId);
        }
        return tweets;
    }

    @Override
    public Tweet getTweet(UUID tweetId) {
        return tweetRepository.findById(tweetId)
                .orElseThrow(() -> new TweetNotFound("Tweets not found - tweetId " + tweetId));
    }

    @Override
    public Tweet createTweet(TweetDto tweetDto) {
        Tweet tweet = Tweet.builder()
                .id(UUID.randomUUID())
                .content(tweetDto.getContent())
                .userId(tweetDto.getUserId())
                .createdAt(LocalDateTime.now())
                .build();
        return tweetRepository.save(tweet);
    }

    @Override
    public void deleteTweet(UUID tweetId) {
        var tweet = tweetRepository.findById(tweetId)
                .orElseThrow(() -> new TweetNotFound("Tweets not found - tweetId " + tweetId));
        tweetRepository.delete(tweet);
    }


}
