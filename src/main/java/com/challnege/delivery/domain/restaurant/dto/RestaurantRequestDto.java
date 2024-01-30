package com.challnege.delivery.domain.restaurant.dto;

import com.challnege.delivery.domain.member.entity.Member;
import com.challnege.delivery.global.audit.Category;
import lombok.*;

import java.io.Serializable;

@Getter
@RequiredArgsConstructor
@Builder
public class RestaurantRequestDto implements Serializable { //valid 추가

    private final String restaurantName;
    private final String address;
    private final Category category;
    private final String resNumber;





}
