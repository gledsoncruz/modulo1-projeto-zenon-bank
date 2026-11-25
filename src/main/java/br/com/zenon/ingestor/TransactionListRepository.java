package br.com.zenon.ingestor;

import br.com.zenon.model.Transaction;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class TransactionListRepository implements TransactionRepository {

    private final List<Transaction> transactions;

    public TransactionListRepository(List<Transaction> transactions) {
        Objects.requireNonNull(transactions);
        this.transactions = transactions;
    }

    @Override
    public Optional<Transaction> findByOriginName(String name) {
        return transactions
                .stream()
                .filter((t) -> t.origin().name().equalsIgnoreCase(name))
                .findFirst();
    }

}
