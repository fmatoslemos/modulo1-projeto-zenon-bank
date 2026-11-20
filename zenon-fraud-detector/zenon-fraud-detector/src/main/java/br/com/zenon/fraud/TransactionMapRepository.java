package br.com.zenon.fraud;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

public class TransactionMapRepository implements TransactionRepository{

    private Map<String, Transaction> transactionsMap;

    public TransactionMapRepository(List<Transaction> transactions) {
        Objects.requireNonNull(transactions);
        transactionsMap = transactions.stream().collect(Collectors.toMap(f -> f.origin().name(), Function.identity()));
    }

    @Override
    public Optional<Transaction> findByOriginName(String name) {
        return Optional.ofNullable(transactionsMap.get(name));
    }
}
