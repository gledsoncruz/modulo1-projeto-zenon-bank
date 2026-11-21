package br.com.zenon.model;

import java.util.Arrays;

public enum TransactionTypeEnum {

    CASH_IN,
    CASH_OUT,
    DEBIT,
    PAYMENT,
    TRANSFER;

    public static boolean isValid(String value) {
        if (value == null) {
            return false;
        }

        return Arrays.stream(values())
                .anyMatch(type -> type.name().equals(value));
    }
}
