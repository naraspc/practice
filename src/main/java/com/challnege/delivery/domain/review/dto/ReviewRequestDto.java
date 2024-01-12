package com.challnege.delivery.domain.review.dto;

import com.challnege.delivery.domain.member.entity.Member;
import lombok.Getter;

@Getter
public class ReviewRequestDto {
    private Long customerId;
    private String nickname;
    private String content;
    private Member member;
    private int rating;
}
