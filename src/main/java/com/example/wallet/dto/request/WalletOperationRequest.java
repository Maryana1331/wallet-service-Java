package com.example.wallet.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import java.util.UUID;
import javax.validation.constraints.Positive;

@Getter
@Setter
@AllArgsConstructor
public class WalletOperationRequest {

    private String operationType;

    private UUID walletId;

    @NonNull
    @Positive(message = "Сумма должна быть неотрицательной")
    private Long amount;
}
