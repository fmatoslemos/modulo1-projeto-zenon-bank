package br.com.zenon.fraud;

import java.util.List;
import java.util.Optional;


public class Main {
    void main() {
        TransactionIngestor transactionIngestor = new TransactionIngestor();
        List<Transaction> linesNewMode =
                transactionIngestor.getLinesNewMode("data/PS_20174392719_1491204439457_log.csv");
        linesNewMode.stream().limit(10).forEach(IO::println);
        IO.println(linesNewMode.size());
        IO.println("----------------------------------------------------------------------------------------------------");
        List<Transaction> linesNewModeBadData =
                transactionIngestor.getLinesNewMode("data/paysim_with_bad_data.csv");
        IO.println(linesNewModeBadData.size());
        linesNewModeBadData.forEach( IO::println);
    }
}
