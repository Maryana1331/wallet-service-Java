package com.example.wallet.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.lang.NonNull;
import javax.validation.constraints.Positive;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class TransferRequest {

    private UUID fromWalletId;

    private UUID toWalletId;

    @NonNull
    @Positive(message = "Сумма должна быть неотрицательной")
    private Long amount;

}
