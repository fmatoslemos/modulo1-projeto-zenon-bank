package br.com.zenon.fraud;

import java.util.List;


public class Main {
    void main() {
        TransactionIngestor transactionIngestor = new TransactionIngestor();
        long inicio = 0;
        long fim = 0;

//        inicio = System.currentTimeMillis();
//        List<Transaction> linesNewMode =
//                transactionIngestor.getLinesNewMode("data/PS_20174392719_1491204439457_log.csv");
//        fim = System.currentTimeMillis();
//        IO.println("Tempo em ms novo modo " + (fim - inicio));
//        for (int i = 0; i < 10; i++) {
//            IO.println(linesNewMode.get(i));
//        }

        inicio = System.currentTimeMillis();
        List<Transaction> linesOldMode =
                transactionIngestor.getLinesOldMode("data/PS_20174392719_1491204439457_log.csv");
        fim = System.currentTimeMillis();
        IO.println("Tempo em ms velho modo " + (fim - inicio));
        for (int i = 0; i < 10; i++) {
            IO.println(linesOldMode.get(i));
        }
    }
}
