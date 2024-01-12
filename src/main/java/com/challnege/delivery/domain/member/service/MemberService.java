package com.challnege.delivery.domain.member.service;

import com.challnege.delivery.domain.member.dto.MemberRequestDto;
import com.challnege.delivery.domain.member.dto.MemberResponseDto;
import com.challnege.delivery.domain.member.entity.Member;
import com.challnege.delivery.domain.member.repository.MemberRepository;
import com.challnege.delivery.domain.wallet.entity.Wallet;
import com.challnege.delivery.domain.wallet.repository.WalletRepository;
import com.challnege.delivery.domain.wallet.service.WalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final WalletService walletService;
    public MemberResponseDto createMember(MemberRequestDto requestDto) {
        Wallet wallet = walletService.createWallet();
        Member member = requestDto.toEntity(wallet);
        memberRepository.save(member);
        return MemberResponseDto.fromEntity(member);
    }

//    public MemberResponseDto readMember(long memberId) {
//        Member member = findMemberById(memberId);
//    }

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
