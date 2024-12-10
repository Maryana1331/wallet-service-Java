package com.example.wallet.constants;

public enum ErrorCodes {
    WALLET_NOT_FOUND("Кошелек не найден"),
    INSUFFICIENT_FUNDS("Недостаточно средств"),
    INVALID_OPERATION_TYPE("Неправильный тип операции"),
    AMOUNT_MUST_BE_GREATER_THAN_ZERO("Сумма должна быть больше нуля"),
    SENDER_WALLET_NOT_FOUND("Кошелек отправителя не найден"),
    RECEIVER_WALLET_NOT_FOUND("Кошелек получателя не найден"),
    INSUFFICIENT_BALANCE("Недостаточный баланс"),
    UNKNOWN_ERROR("Неизвестная ошибка"),
    JSON_NOT_VALID("Невалидный json");

    private final String description;

    ErrorCodes(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
