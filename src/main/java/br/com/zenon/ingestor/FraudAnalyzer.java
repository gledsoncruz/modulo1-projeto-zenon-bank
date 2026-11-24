package br.com.zenon.ingestor;

import br.com.zenon.model.Transaction;
import br.com.zenon.model.TransactionTypeEnum;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FraudAnalyzer {

    private final List<Transaction>  transactions;

    public FraudAnalyzer(List<Transaction> transactions) {
        Objects.requireNonNull(transactions, "transactions is null");
        this.transactions = transactions;
    }

    public long getCountFrauds() {
        return fraudListStream().count();
    }

    public List<BigDecimal> findHighestValueFrauds(int limit) {
        return highestValueFraudStream()
                .map(Transaction::amount)
                .limit(limit)
                .toList();
    }

    public List<String> findTopSuspiciousCustomer(int limit) {
        return highestValueFraudStream()
                .map((t -> t.origin().name()))
                .distinct()
                .limit(limit)
                .toList();
    }

    public BigDecimal totalAmountFrauds() {
        return fraudListStream()
                .map(Transaction::amount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public Map<TransactionTypeEnum, Long> countFraudByType() {
        return fraudListStream()
                .collect(Collectors.groupingBy(Transaction::type, Collectors.counting()));
    }

    private Stream<Transaction> fraudListStream() {
        return transactions
                .stream()
                .filter(Transaction::isFraud);
    }

    private Stream<Transaction> highestValueFraudStream() {
        return fraudListStream()
                .sorted(Comparator.comparing(Transaction::amount).reversed());
    }


}
