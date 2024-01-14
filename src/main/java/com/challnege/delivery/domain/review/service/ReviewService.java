package com.challnege.delivery.domain.review.service;

import com.challnege.delivery.domain.member.entity.Member;
import com.challnege.delivery.domain.member.repository.MemberRepository;
import com.challnege.delivery.domain.restaurant.entity.Restaurant;
import com.challnege.delivery.domain.restaurant.repository.RestaurantRepository;
import com.challnege.delivery.domain.review.dto.ReviewRequestDto;
import com.challnege.delivery.domain.review.dto.ReviewResponseDto;
import com.challnege.delivery.domain.review.entity.Review;
import com.challnege.delivery.domain.review.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final RestaurantRepository restaurantRepository;
    private final MemberRepository memberRepository;
    private final ReviewRepository reviewRepository;

    @Transactional
    public ReviewResponseDto postReview(Long restaurantId, ReviewRequestDto requestDto) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId).orElseThrow(
                () -> new IllegalArgumentException("해당 업장은 존재하지 않습니다.")
        );
        Member member = memberRepository.findById(requestDto.getMemberID()).orElseThrow(
                () -> new IllegalArgumentException("해당 회원은 존재하지 않습니다.")
        );

        Review review = new Review(restaurant, member, requestDto);
        reviewRepository.save(review);

        return new ReviewResponseDto(review);
    }

    @Transactional
    public ReviewResponseDto deleteReview(Long restaurantId, Long reviewId) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId).orElseThrow(
                () -> new IllegalArgumentException("해당 업장은 존재하지 않습니다.")
        );

        Review review = reviewRepository.findByRestaurantAndReviewId(restaurant, reviewId);
        if (review == null) {
            throw new ResponseStatusException(org.springframework.http.HttpStatus.NOT_FOUND, "해당 리뷰는 존재하지 않습니다.");
        }

        // 로그인한 사용자가 리뷰를 삭제할 권한이 있는지 확인하는 로직을 구현
        reviewRepository.delete(review);

        return new ReviewResponseDto(review);
    }
}
