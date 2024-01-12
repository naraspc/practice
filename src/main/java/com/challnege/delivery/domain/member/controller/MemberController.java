package com.challnege.delivery.domain.member.controller;

import com.challnege.delivery.domain.member.dto.MemberRequestDto;
import com.challnege.delivery.domain.member.dto.MemberResponseDto;
import com.challnege.delivery.domain.member.service.MemberService;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;

    @PostMapping
    public ResponseEntity<MemberResponseDto> createMember(@RequestBody MemberRequestDto requestDto) {
        MemberResponseDto memberResponseDto = memberService.createMember(requestDto);
        return new ResponseEntity<>(memberResponseDto, HttpStatus.CREATED);
    }



    @DeleteMapping("/{memberId}")
    public ResponseEntity deleteMember(@PathVariable @Positive long memberId) {
        memberService.deleteMember(memberId);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
