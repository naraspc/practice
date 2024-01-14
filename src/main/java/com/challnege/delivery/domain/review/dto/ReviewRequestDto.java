package com.challnege.delivery.domain.review.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ReviewRequestDto {
    private Long memberID;
    private String nickname;
    private String content;
    private int rating;
}
