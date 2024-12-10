package com.example.wallet.controllers;

import com.example.wallet.dto.request.TransferRequest;
import com.example.wallet.dto.request.WalletOperationRequest;
import com.example.wallet.dto.response.CustomSuccessResponse;
import com.example.wallet.dto.response.WalletOperationResponse;
import com.example.wallet.service.WalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Positive;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/wallet")
public class WalletController {

    private final WalletService walletService;

    @PostMapping
    public ResponseEntity<WalletOperationResponse> createWallet(@RequestBody @Positive WalletOperationRequest walletOperationRequest) {
        WalletOperationResponse response = walletService.createWallet(walletOperationRequest);
        return ResponseEntity.ok(new CustomSuccessResponse<>(response).getData());
    }

    @PutMapping
    public ResponseEntity<WalletOperationResponse> updateWallet(@RequestBody WalletOperationRequest walletOperationRequest) {
        WalletOperationResponse response = walletService.updateWallet(walletOperationRequest);
        return ResponseEntity.ok(new CustomSuccessResponse<>(response).getData());
    }

    @DeleteMapping("/{walletId}")
    public ResponseEntity<Void> deleteWallet(@PathVariable UUID walletId) {
        walletService.deleteWallet(walletId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{walletId}")
    public ResponseEntity<WalletOperationResponse> getWallet(@PathVariable UUID walletId) {
        WalletOperationResponse response = walletService.getWallet(walletId);
        return ResponseEntity.ok(new CustomSuccessResponse<>(response).getData());
    }

    @PostMapping("/operation")
    public ResponseEntity<WalletOperationResponse> performOperation(@RequestBody WalletOperationRequest walletOperationRequest) {
        WalletOperationResponse response = walletService.performOperation(walletOperationRequest);
        return ResponseEntity.ok(new CustomSuccessResponse<>(response).getData());
    }

    @PostMapping("/transfer")
    public ResponseEntity<CustomSuccessResponse<String>> transfer(@RequestBody TransferRequest transferRequest) {
        CustomSuccessResponse<String> response = walletService.transfer(transferRequest);
        return ResponseEntity.ok(new CustomSuccessResponse<>(response).getData());
    }
}
