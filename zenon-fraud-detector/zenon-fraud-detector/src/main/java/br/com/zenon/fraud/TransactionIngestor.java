package br.com.zenon.fraud;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class TransactionIngestor {

    private final String fileName;

    public TransactionIngestor(String fileName) {
        this.fileName = fileName;
    }

    public List<Transaction> getLines() throws IOException {
        List<Transaction> result = new ArrayList<>();
        Path path = Path.of(fileName);
        List<String> lines = Files.readAllLines(path);
        for (int i = 1; i <= 1000; i++) {
            String[] lineSplited = lines.get(i).split(",");
            result.add(new Transaction(
                    Integer.parseInt(lineSplited[0]),
                    TransactionType.valueOf(lineSplited[1]),
                    new BigDecimal(lineSplited[2]),
                    new TransactionCustomer(lineSplited[3], new BigDecimal(lineSplited[4]), new BigDecimal(lineSplited[5])),
                    new TransactionCustomer(lineSplited[6], new BigDecimal(lineSplited[7]), new BigDecimal(lineSplited[8])),
                    Integer.parseInt(lineSplited[9])  == 0 ? Boolean.FALSE : Boolean.TRUE,
                    Integer.parseInt(lineSplited[10]) == 0 ? Boolean.FALSE : Boolean.TRUE));
        }
        return result;
    }
}
