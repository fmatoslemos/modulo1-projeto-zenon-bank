package br.com.zenon.fraud;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FraudAnalyser {

    private final List<Transaction> transactions;

    public FraudAnalyser(List<Transaction> transactions) {
        Objects.requireNonNull(transactions);
        this.transactions = transactions;
    }

    public long countFrauds() {
        return getFraudsStream().count();
    }

    public List<Transaction> findHighestFrauds(int limit) {
        return getFraudsStream().
                sorted(Comparator.comparing(Transaction::amount).reversed()).
                limit(limit).
                toList();
    }

    public List<String> findTopSuspiciousClients(int limit) {
        List<Transaction> topFraudTransactions = this.findHighestFrauds(limit);
        return topFraudTransactions.isEmpty() ? Collections.emptyList() : topFraudTransactions.stream().map(f-> f.origin().name()).distinct().toList();
    }

    public BigDecimal calculateTotalFraudsLosses() {
        return getFraudsStream().map(Transaction::amount).reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public Map<TransactionType, Long > countTotalFraudsByTransactionType() {
        return getFraudsStream().collect(Collectors.groupingBy(Transaction::type, Collectors.counting()));
    }

    private Stream<Transaction> getFraudsStream() {
        return transactions.stream().filter(Transaction::isFraud);
    }
}
