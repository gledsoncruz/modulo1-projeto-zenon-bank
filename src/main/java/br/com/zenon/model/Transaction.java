package br.com.zenon.model;

import java.math.BigDecimal;
import java.util.Objects;

public record Transaction(
        int step,
        TransactionTypeEnum type,
        BigDecimal amount, // BigDecimal foi escolhido para se trabalhar com precisão de pontos flutuantes
        TransactionCustomer origin,
        TransactionCustomer recipient,
        boolean isFraud,
        boolean isFlaggedFraud) {

    public Transaction {
        Objects.requireNonNull(type, "type is null");
        Objects.requireNonNull(amount, "amount is null");
        Objects.requireNonNull(origin, "origin is null");
        Objects.requireNonNull(recipient, "recipient is null");

        if (step <= 0) {
            throw new IllegalArgumentException(
                    "step should be positive: " + step);
        }

        if (amount.signum() < 0) {
            throw new IllegalArgumentException(
                    "amount should be positive: " + amount);
        }
    }
}
