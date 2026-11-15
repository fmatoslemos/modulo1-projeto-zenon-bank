package br.com.zenon.fraud;

import java.util.List;


public class Main {
    void main() {
        TransactionIngestor transactionIngestor = new TransactionIngestor();
        long inicio;
        long fim;

        inicio = System.currentTimeMillis();
        List<Transaction> linesNewMode =
                transactionIngestor.getLinesNewMode("data/PS_20174392719_1491204439457_log.csv");
        fim = System.currentTimeMillis();
        IO.println("Tempo em ms novo modo " + (fim - inicio));
        linesNewMode.stream().limit(10).forEach(IO::println);

        inicio = System.currentTimeMillis();
        List<Transaction> linesOldMode =
                transactionIngestor.getLinesOldMode("data/PS_20174392719_1491204439457_log.csv");
        fim = System.currentTimeMillis();
        IO.println("Tempo em ms velho modo " + (fim - inicio));
        linesOldMode.stream().limit(10).forEach(IO::println);
    }
}
