package com.example.gateway.rest;

import com.example.domain.entity.Tweet;
import com.example.domain.service.TweetService;
import com.example.gateway.rest.datacontract.PaginationDataContract;
import com.example.gateway.rest.datacontract.ResponseDataContract;
import com.example.gateway.rest.datacontract.TweetDataContract;
import com.example.gateway.rest.datacontract.TweetDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;


@RestController
@RequestMapping("/tweet")
public class TweetController {

    @Autowired
    private TweetService tweetService;

    @Autowired
    private ModelMapper mapper;

    @GetMapping
    public ResponseEntity<TweetDataContract> listTweetsByUser(@RequestParam(value = "user_id") String userId, Pageable pageable) {
        var pageTweets = tweetService.listTweetsByUser(userId, pageable);
        var tweets = pageTweets.getContent().stream()
                .map(t -> mapper.map(t, TweetDto.class)).toList();

        TweetDataContract tweetDataContract = TweetDataContract.builder()
                .data(tweets)
                .pagination(PaginationDataContract.builder()
                        .size(pageTweets.getSize())
                        .number(pageTweets.getNumber())
                        .totalPages(pageTweets.getTotalPages())
                        .totalElements(pageTweets.getTotalElements())
                        .build()).build();

        return ResponseEntity.ok(tweetDataContract);
    }


    @PostMapping
    public ResponseEntity<ResponseDataContract> createTweet(@RequestBody TweetDto tweetDto) {
        Tweet tweet = tweetService.createTweet(tweetDto);
        return ResponseEntity.ok(ResponseDataContract.builder()
                .data(mapper.map(tweet, TweetDto.class))
                .build());
    }

    @DeleteMapping("/{tweet_id}")
    public ResponseEntity deleteTweet(@PathVariable(value = "tweet_id") UUID tweeId) {
        tweetService.deleteTweet(tweeId);
        return ResponseEntity.noContent().build();
    }
}
