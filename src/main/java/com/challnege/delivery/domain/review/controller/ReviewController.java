package com.challnege.delivery.domain.review.controller;

import com.challnege.delivery.domain.review.dto.ReviewRequestDto;
import com.challnege.delivery.domain.review.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping("/api/restaurants/{restaurantId}/reviews")
    public void postReview(@PathVariable Long restaurantId,
                           @RequestBody ReviewRequestDto requestDto){
        reviewService.postReview(restaurantId,requestDto);
    }

    @DeleteMapping("restauransts/{restaurantsId}/reviews/{reviewId}")
    public void deleteReview(@PathVariable Long restaurantsId,
                             @PathVariable Long reviewId){
        reviewService.deleteReview(restaurantsId,reviewId);
    }
}
