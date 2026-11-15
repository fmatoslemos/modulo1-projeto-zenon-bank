package br.com.zenon.fraud;

import java.util.List;


public class Main {
    void main(String[] args) throws Exception {
        TransactionIngestor transactionIngestor = new
                TransactionIngestor("/home/felipe/proj/modulo1-projeto-zenon-bank/zenon-fraud-detector/zenon-fraud-detector/data/PS_20174392719_1491204439457_log.csv");

        List<Transaction> lines = transactionIngestor.getLines();
        for (int i = 0; i < 10; i++) {
            IO.println(lines.get(i));
        }
    }
}
