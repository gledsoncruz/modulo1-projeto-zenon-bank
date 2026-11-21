package br.com.zenon.model;

import java.math.BigDecimal;

public record TransactionCustomer(
        String name,
        BigDecimal oldBalance,
        BigDecimal newBalance
) {

    public TransactionCustomer {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("name should not be empty");
        }

        if (oldBalance.signum() < 0) {
            throw new IllegalArgumentException(
                    "oldBalance should be positive: " + oldBalance);
        }

        if (newBalance.signum() < 0) {
            throw new IllegalArgumentException(
                    "newBalance should be positive: " + newBalance);
        }
    }
}
