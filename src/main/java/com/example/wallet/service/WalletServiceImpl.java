package com.example.wallet.service;

import com.example.wallet.dto.request.WalletOperationRequest;
import com.example.wallet.dto.response.WalletOperationResponse;
import com.example.wallet.entity.Wallet;
import com.example.wallet.repository.WalletRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Getter
@Setter
@Service
@RequiredArgsConstructor
public class WalletServiceImpl implements WalletService {

    private final WalletRepository walletRepository;

    @Transactional
    @Override
    public WalletOperationResponse performOperation(WalletOperationRequest walletOperationRequest) {
        Wallet wallet = walletRepository.findById(walletOperationRequest.getWalletId())
                .orElseThrow(() -> new IllegalArgumentException("Wallet not found"));

        switch (walletOperationRequest.getOperationType().toUpperCase()) {
            case "DEPOSIT":
                wallet.setBalance(wallet.getBalance() + walletOperationRequest.getAmount());
                break;
            case "WITHDRAW":
                if (wallet.getBalance() < walletOperationRequest.getAmount()) {
                    throw new IllegalArgumentException("Insufficient funds");
                }
                wallet.setBalance(wallet.getBalance() - walletOperationRequest.getAmount());
                break;
            default:
                throw new IllegalArgumentException("Invalid operation type. Must be 'DEPOSIT' or 'WITHDRAW'.");
        }

        walletRepository.save(wallet);
        return new WalletOperationResponse(wallet.getId(), wallet.getBalance());
    }

    @Override
    public WalletOperationResponse createWallet(WalletOperationRequest walletOperationRequest) {
        Wallet wallet = new Wallet();
        wallet.setId(UUID.randomUUID());
        wallet.setBalance(walletOperationRequest.getAmount());
        walletRepository.save(wallet);

        return new WalletOperationResponse(wallet.getId(), wallet.getBalance());
    }

    @Override
    public WalletOperationResponse updateWallet(WalletOperationRequest walletOperationRequest) {
        Wallet wallet = walletRepository.findById(walletOperationRequest.getWalletId())
                .orElseThrow(() -> new IllegalArgumentException("Wallet not found"));

        wallet.setBalance(walletOperationRequest.getAmount());

        walletRepository.save(wallet);

        return new WalletOperationResponse(wallet.getId(), wallet.getBalance());
    }

    @Override
    public void deleteWallet(UUID walletId) {
        if (!walletRepository.existsById(walletId)) {
            throw new IllegalArgumentException("Wallet not found");
        }
        walletRepository.deleteById(walletId);
    }

    @Override
    public WalletOperationResponse getWallet(UUID walletId) {
        Wallet wallet = walletRepository.findById(walletId)
                .orElseThrow(() -> new IllegalArgumentException("Wallet not found"));

        return new WalletOperationResponse(wallet.getId(), wallet.getBalance());
    }

    public long getBalance(UUID walletId) {
        return walletRepository.findById(walletId)
                .orElseThrow(() -> new IllegalArgumentException("Wallet not found"))
                .getBalance();
    }
}
