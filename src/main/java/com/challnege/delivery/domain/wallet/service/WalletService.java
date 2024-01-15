package com.challnege.delivery.domain.wallet.service;

import com.challnege.delivery.domain.wallet.entity.Wallet;
import com.challnege.delivery.domain.wallet.repository.WalletRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class WalletService {
    private final WalletRepository walletRepository;
    public Wallet createWallet() {
        Wallet wallet = Wallet.builder()
                .point(1000000L)
                .spending(0L)
                .build();
        walletRepository.save(wallet);
        return wallet;
    }

}
