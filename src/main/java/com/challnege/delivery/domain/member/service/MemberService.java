package com.challnege.delivery.domain.member.service;

import com.challnege.delivery.domain.member.dto.MemberRequestDto;
import com.challnege.delivery.domain.member.dto.MemberResponseDto;
import com.challnege.delivery.domain.member.entity.Member;
import com.challnege.delivery.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    public MemberResponseDto createMember(MemberRequestDto requestDto) {
        Member member = requestDto.toEntity();
        memberRepository.save(member);
        return MemberResponseDto.fromEntity(member);
    }

    public void deleteMember(long memberId) {
        isMemberExist(memberId);
        memberRepository.deleteById(memberId);
    }

    public Member findMemberById(long memberId) {
        Optional<Member> optionalMember = memberRepository.findById(memberId);
        Member member = optionalMember.orElseThrow(() -> new NullPointerException("존재하지 않는 계정입니다."));
        return member;
    }

    public void isMemberExist(long memberId) {
        boolean isExist = memberRepository.existsById(memberId);
        if (!isExist) {
            throw new NullPointerException("존재하지 않는 계정입니다.");
        }
    }
}
