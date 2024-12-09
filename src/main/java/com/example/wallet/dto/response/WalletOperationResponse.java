package com.example.wallet.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.UUID;

@Data
@AllArgsConstructor
public class WalletOperationResponse {
        private UUID walletId;
        private long balance;
}
