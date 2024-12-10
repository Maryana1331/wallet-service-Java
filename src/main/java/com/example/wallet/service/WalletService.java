package com.example.wallet.service;

import com.example.wallet.dto.request.TransferRequest;
import com.example.wallet.dto.request.WalletOperationRequest;
import com.example.wallet.dto.response.CustomSuccessResponse;
import com.example.wallet.dto.response.WalletOperationResponse;
import java.util.UUID;

public interface WalletService {

    WalletOperationResponse createWallet(WalletOperationRequest walletOperationRequest);

    WalletOperationResponse updateWallet(WalletOperationRequest walletOperationRequest);

    void deleteWallet(UUID walletId);

    WalletOperationResponse getWallet(UUID walletId);

    WalletOperationResponse performOperation(WalletOperationRequest walletOperationRequest);

    CustomSuccessResponse<String> transfer(TransferRequest transferRequest);
}
