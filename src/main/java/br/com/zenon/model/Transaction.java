package br.com.zenon.model;

import java.math.BigDecimal;

public record Transaction(
        int step,
        TransactionTypeEnum type,
        BigDecimal amount, // BigDecimal foi escolhido para se trabalhar com precisão de pontos flutuantes
        TransactionCustomer origin,
        TransactionCustomer recipient,
        boolean isFraud,
        boolean isFlaggedFraud) {
}
