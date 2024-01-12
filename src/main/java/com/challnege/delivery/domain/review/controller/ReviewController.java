package com.challnege.delivery.domain.review.controller;

import com.challnege.delivery.domain.review.dto.ReviewRequestDto;
import com.challnege.delivery.domain.review.dto.ReviewResponseDto;
import com.challnege.delivery.domain.review.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping("/api/restaurants/{restaurantId}/reviews")
    public ResponseEntity<ReviewResponseDto> postReview(@PathVariable Long restaurantId,
                                                        @RequestBody ReviewRequestDto requestDto){
        return reviewService.postReview(restaurantId,requestDto);
    }

    @DeleteMapping("restauransts/{restaurantId}/reviews/{reviewId}")
    public ResponseEntity<ReviewResponseDto> deleteReview(@PathVariable Long restaurantId,
                             @PathVariable Long reviewId){
        return reviewService.deleteReview(restaurantId,reviewId);
    }
}
