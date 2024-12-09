package com.example.wallet.dto.request;

import lombok.Getter;
import lombok.Setter;
import java.util.UUID;

@Getter
@Setter
public class WalletOperationRequest {

    private String operationType;
    private UUID walletId;
    private long amount;
}
