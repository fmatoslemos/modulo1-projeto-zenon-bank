package br.com.zenon.fraud;

import java.math.BigDecimal;
import java.util.List;


public class Main {
    static void main() throws Exception{
        TransactionIngestor transactionIngestor = new
                TransactionIngestor("/home/felipe/proj/modulo1-projeto-zenon-bank/zenon-fraud-detector/zenon-fraud-detector/data/PS_20174392719_1491204439457_log.csv");

        List<Transaction> lines = transactionIngestor.getLines();
        for (int i = 0; i < 10; i++) {
            System.out.println(lines.get(i));
        }
    }
}
