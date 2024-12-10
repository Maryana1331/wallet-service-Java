package com.example.wallet.service;

import com.example.wallet.dto.request.TransferRequest;
import com.example.wallet.dto.request.WalletOperationRequest;
import com.example.wallet.dto.response.CustomSuccessResponse;
import com.example.wallet.dto.response.WalletOperationResponse;
import java.util.UUID;

public interface WalletService {

    WalletOperationResponse createWallet(WalletOperationRequest walletOperationRequest);

    WalletOperationResponse updateWallet(WalletOperationRequest walletOperationRequest);

    void deleteWallet(UUID walletId); // Изменен на void, так как метод не должен возвращать значение

    WalletOperationResponse getWallet(UUID walletId); // Переименован для соответствия с реализацией

    WalletOperationResponse performOperation(WalletOperationRequest walletOperationRequest);

    CustomSuccessResponse<String> transfer(TransferRequest transferRequest);
}
