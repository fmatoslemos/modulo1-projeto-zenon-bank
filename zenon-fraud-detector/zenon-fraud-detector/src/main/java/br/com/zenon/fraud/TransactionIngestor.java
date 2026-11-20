package br.com.zenon.fraud;

import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

public class TransactionIngestor {

    public List<Transaction> getLinesNewMode(String fileName, int numberOfLines) {
        try {
            Path path = Path.of(fileName);
            List<String> lines = Files.readAllLines(path);
            return lines.stream().skip(1).limit(numberOfLines).map(this::createTransaction).filter(Optional::isPresent).map(Optional::get).toList();
        } catch (Exception ex) {
            throw new RuntimeException("Erro ao ler o arquivo " + fileName, ex);
        }
    }

    private Optional<Transaction> createTransaction(String line) {
        try {
            String[] chunks = line.split(",");
            int step = Integer.parseInt(Optional.ofNullable(chunks[0]).orElseThrow(() -> new IllegalArgumentException("step should not be null")));
            if (step < 1) {
                throw new IllegalArgumentException("step should be positive: " + step);
            }

            TransactionType type = TransactionType.valueOf(Optional.ofNullable(chunks[1]).orElseThrow(() -> new IllegalArgumentException("type should not be null")));
            BigDecimal amount = new BigDecimal(Optional.ofNullable(chunks[2]).orElseThrow(() -> new IllegalArgumentException("amount should not be null")));
            if (amount.compareTo(BigDecimal.ZERO) <= 0) {
                throw new IllegalArgumentException("amount should be positive: " + amount);
            }

            TransactionCustomer origin = getOrigin(chunks);
            TransactionCustomer recipient = getRecipient(chunks);

            boolean isFraud = chunks[9].equals("1");
            boolean isMarkedAsFraud = chunks[10].equals("1");
            Transaction transaction = new Transaction(
                    step,
                    type,
                    amount,
                    origin,
                    recipient,
                    isFraud,
                    isMarkedAsFraud);
            return Optional.of(transaction);
        } catch (NumberFormatException ex) {
            System.err.println("Erro: " + line + " | " + ex);
            return Optional.empty();
        } catch (RuntimeException ex) {
            //Logar
            System.err.println("Erro: " + line + " | " + ex);
            return Optional.empty();
        }
    }

    private static TransactionCustomer getRecipient(String[] chunks) {
        String nameRecipient = Optional.ofNullable(chunks[6]).orElseThrow(() -> new IllegalArgumentException("name should not be null"));
        if (nameRecipient.isEmpty()) {
            throw new IllegalArgumentException("name should not be empty");
        }
        BigDecimal oldBalanceRecipient = new BigDecimal(Optional.ofNullable((chunks[7])).orElseThrow(() -> new IllegalArgumentException("oldBalance should not be null")));
        if (oldBalanceRecipient.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("oldBalance should be positive: " + oldBalanceRecipient);
        }
        BigDecimal newBalanceRecipient = new BigDecimal(Optional.ofNullable(chunks[8]).orElseThrow(() -> new IllegalArgumentException("newBalance should not be null")));
        if (newBalanceRecipient.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("newBalance should be positive: " + newBalanceRecipient);
        }
        return new TransactionCustomer(nameRecipient, oldBalanceRecipient, newBalanceRecipient);
    }

    private static TransactionCustomer getOrigin(String[] chunks) {
        String nameOrigin = Optional.ofNullable(chunks[3]).orElseThrow(() -> new IllegalArgumentException("name should not be null"));
        if (nameOrigin.isEmpty()) {
            throw new IllegalArgumentException("name should not be empty");
        }
        BigDecimal oldBalanceOrigin = new BigDecimal(Optional.ofNullable(chunks[4]).orElseThrow(() -> new IllegalArgumentException("oldBalance should not be null")));
        if (oldBalanceOrigin.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("oldBalance should be positive: " + oldBalanceOrigin);
        }
        BigDecimal newBalanceOrigin = new BigDecimal(Optional.ofNullable(chunks[5]).orElseThrow(() -> new IllegalArgumentException("newBalance should not be null")));
        if (newBalanceOrigin.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("newBalance should be positive: " + newBalanceOrigin);
        }
        return new TransactionCustomer(nameOrigin, oldBalanceOrigin, newBalanceOrigin);
    }
}
