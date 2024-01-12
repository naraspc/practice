package com.challnege.delivery.domain.review.service;

import com.challnege.delivery.domain.member.entity.Member;
import com.challnege.delivery.domain.member.repository.MemberRepository;
import com.challnege.delivery.domain.restaurant.entity.Restaurant;
import com.challnege.delivery.domain.restaurant.repository.RestaurantRepository;
import com.challnege.delivery.domain.review.dto.ReviewRequestDto;
import com.challnege.delivery.domain.review.entity.Review;
import com.challnege.delivery.domain.review.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final RestaurantRepository restaurantRepository;
    private final MemberRepository memberRepository;
    private final ReviewRepository reviewRepository;

    @Transactional
    public void postReview(Long restaurantId, ReviewRequestDto requestDto) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId).orElseThrow(
                () -> new IllegalArgumentException("해당 업장은 존재하지 않습니다.")
        );
        Member member = memberRepository.findById(requestDto.getMember().getMemberId()).orElseThrow(
                () -> new IllegalArgumentException()
        );
        reviewRepository.save(new Review(restaurant,member,requestDto));
    }

    @Transactional
    public void deleteReview(Long restaurantsId, Long reviewId) {
        Restaurant restaurant = restaurantRepository.findById(restaurantsId).orElseThrow(
                () -> new IllegalArgumentException("해당 업장은 존재하지 않습니다.")
        );
        Review review = reviewRepository.findByRestaurantAndReviewId(restaurant,reviewId);
        reviewRepository.delete(review);
    }
}
