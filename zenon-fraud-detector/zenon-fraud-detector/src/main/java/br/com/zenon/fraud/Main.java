package br.com.zenon.fraud;

import java.util.List;
import java.util.Optional;


public class Main {
    void main() {
        TransactionIngestor transactionIngestor = new TransactionIngestor();
        List<Optional<Transaction>> linesNewMode =
                transactionIngestor.getLinesNewMode("data/paysim_with_bad_data.csv");
        linesNewMode.stream().filter(Optional::isPresent).limit(10).forEach( f-> IO.println(f.get()));
    }
}
