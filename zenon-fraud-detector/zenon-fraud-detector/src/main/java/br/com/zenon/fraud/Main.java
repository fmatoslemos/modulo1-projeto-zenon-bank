package br.com.zenon.fraud;

import java.util.List;


public class Main {
    void main() {
        TransactionIngestor transactionIngestor = new TransactionIngestor();
        List<Transaction> linesNewMode =
                transactionIngestor.getLinesNewMode("data/PS_20174392719_1491204439457_log.csv");
        linesNewMode.stream().limit(10).forEach(IO::println);
    }
}
