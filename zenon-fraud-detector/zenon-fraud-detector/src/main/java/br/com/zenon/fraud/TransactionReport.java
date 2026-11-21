package br.com.zenon.fraud;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

public class TransactionReport {

    private record ReportTransaction(BigDecimal amount, boolean isFraud) {
    }

    public record Statistics(long totalTransactions, long totalFrauds, BigDecimal totalAmount) {

        private static Statistics ZERO = new Statistics(0L, 0L, BigDecimal.ZERO);

        private Statistics addReportTransaction(ReportTransaction reportTransaction) {
            return new Statistics(1 + this.totalTransactions,
                    (reportTransaction.isFraud ? 1 : 0) + this.totalFrauds,
                    reportTransaction.amount.add(this.totalAmount));
        }

        private Statistics add(Statistics statistics) {
            return new Statistics(this.totalTransactions + statistics.totalTransactions,
                    this.totalFrauds + statistics.totalFrauds, this.totalAmount.add(statistics.totalAmount));
        }
    }

    public Statistics generateReport(String fileName) {
        Objects.requireNonNull(fileName);
        try (Stream<String> stream = Files.lines(Path.of(fileName))) {
            return stream.
                    skip(1).
                    map(this::parseReportTransaction).
                    reduce(Statistics.ZERO, Statistics::addReportTransaction, Statistics::add);
        } catch (IOException ex) {
            throw new RuntimeException("Erro ao ler o arquivo " + fileName, ex);
        }

    }

    private ReportTransaction parseReportTransaction(String line) {
        String[] chunks = line.split(",");
        BigDecimal amount = new BigDecimal(Optional.ofNullable(chunks[2]).orElseThrow(() -> new IllegalArgumentException("amount should not be null")));
        boolean isFraud = chunks[9].equals("1");
        return new ReportTransaction(amount, isFraud);
    }
}
