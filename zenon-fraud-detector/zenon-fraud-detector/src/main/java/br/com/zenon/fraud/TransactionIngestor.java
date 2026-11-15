package br.com.zenon.fraud;

import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class TransactionIngestor {

    public List<Transaction> getLinesNewMode(String fileName) {
        try {
            Path path = Path.of(fileName);
            List<String> lines = Files.readAllLines(path);
            return lines.stream().skip(1).limit(1000).map(this::createTransaction).toList();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    private Transaction createTransaction(String line) {
        String[] chunks = line.split(",");
        return new Transaction(
                Integer.parseInt(chunks[0]),
                TransactionType.valueOf(chunks[1]),
                new BigDecimal(chunks[2]),
                new TransactionCustomer(chunks[3], new BigDecimal(chunks[4]), new BigDecimal(chunks[5])),
                new TransactionCustomer(chunks[6], new BigDecimal(chunks[7]), new BigDecimal(chunks[8])),
                chunks[9].equals("1"),
                chunks[10].equals("1"));
    }
}
