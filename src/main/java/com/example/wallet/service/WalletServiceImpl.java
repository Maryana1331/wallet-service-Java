package com.example.wallet.service;

import com.example.wallet.constants.ErrorCodes;
import com.example.wallet.dto.request.TransferRequest;
import com.example.wallet.dto.request.WalletOperationRequest;
import com.example.wallet.dto.response.CustomSuccessResponse;
import com.example.wallet.dto.response.WalletOperationResponse;
import com.example.wallet.entity.Wallet;
import com.example.wallet.exception.WalletTransferException;
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
                .orElseThrow(() -> new WalletTransferException(ErrorCodes.WALLET_NOT_FOUND.getDescription()));

        switch (walletOperationRequest.getOperationType().toUpperCase()) {
            case "DEPOSIT":
                wallet.setBalance(wallet.getBalance() + walletOperationRequest.getAmount());
                break;
            case "WITHDRAW":
                if (wallet.getBalance() < walletOperationRequest.getAmount()) {
                    throw new WalletTransferException(ErrorCodes.INSUFFICIENT_FUNDS.getDescription());
                }
                wallet.setBalance(wallet.getBalance() - walletOperationRequest.getAmount());
                break;
            default:
                throw new WalletTransferException(ErrorCodes.INVALID_OPERATION_TYPE.getDescription());
        }

        walletRepository.save(wallet);
        return new WalletOperationResponse(wallet.getId(), wallet.getBalance());
    }

    @Override
    @Transactional
    public CustomSuccessResponse<String> transfer(TransferRequest transferRequest) {
        if (transferRequest.getAmount() <= 0) {
            throw new WalletTransferException(ErrorCodes.AMOUNT_MUST_BE_GREATER_THAN_ZERO.getDescription());
        }

        Wallet fromWallet = walletRepository.findById(transferRequest.getFromWalletId())
                .orElseThrow(() -> new WalletTransferException(ErrorCodes.SENDER_WALLET_NOT_FOUND.getDescription()));
        Wallet toWallet = walletRepository.findById(transferRequest.getToWalletId())
                .orElseThrow(() -> new WalletTransferException(ErrorCodes.RECEIVER_WALLET_NOT_FOUND.getDescription()));

        if (fromWallet.getBalance() < transferRequest.getAmount()) {
            throw new WalletTransferException(ErrorCodes.INSUFFICIENT_BALANCE.getDescription());
        }

        fromWallet.setBalance(fromWallet.getBalance() - transferRequest.getAmount());
        toWallet.setBalance(toWallet.getBalance() + transferRequest.getAmount());

        walletRepository.save(fromWallet);
        walletRepository.save(toWallet);

        return new CustomSuccessResponse<>("Transfer successful");
    }

    @Override
    public WalletOperationResponse createWallet(WalletOperationRequest walletOperationRequest) {
        if (walletOperationRequest.getAmount() < 0) {
            throw new IllegalArgumentException("Сумма должна быть неотрицательной");
            }
        Wallet wallet = new Wallet();
        wallet.setId(UUID.randomUUID());
        wallet.setBalance(walletOperationRequest.getAmount());
        walletRepository.save(wallet);

        return new WalletOperationResponse(wallet.getId(), wallet.getBalance());
    }

    @Override
    public WalletOperationResponse updateWallet(WalletOperationRequest walletOperationRequest) {
        Wallet wallet = walletRepository.findById(walletOperationRequest.getWalletId())
                .orElseThrow(() -> new WalletTransferException(ErrorCodes.WALLET_NOT_FOUND.getDescription()));

        wallet.setBalance(walletOperationRequest.getAmount());

        walletRepository.save(wallet);

        return new WalletOperationResponse(wallet.getId(), wallet.getBalance());
    }

    @Override
    public void deleteWallet(UUID walletId) {
        if (!walletRepository.existsById(walletId)) {
            throw new WalletTransferException(ErrorCodes.WALLET_NOT_FOUND.getDescription());
        }
        walletRepository.deleteById(walletId);
    }

    @Override
    public WalletOperationResponse getWallet(UUID walletId) {
        Wallet wallet = walletRepository.findById(walletId)
                .orElseThrow(() -> new WalletTransferException(ErrorCodes.WALLET_NOT_FOUND.getDescription()));

        return new WalletOperationResponse(wallet.getId(), wallet.getBalance());
    }

    public long getBalance(UUID walletId) {
        return walletRepository.findById(walletId)
                .orElseThrow(() -> new WalletTransferException(ErrorCodes.WALLET_NOT_FOUND.getDescription()))
                .getBalance();
    }
}