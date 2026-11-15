package br.com.zenon.fraud;

import java.io.FileInputStream;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TransactionIngestor {

    public List<Transaction> getLinesNewMode(String fileName) {
        try {
            List<Transaction> result = new ArrayList<>();
            Path path = Path.of(fileName);
            List<String> lines = Files.readAllLines(path);
            for (int i = 1; i <= 1000; i++) {
                String[] lineSplited = lines.get(i).split(",");
                addToResultList(result, lineSplited);
            }
            return result;
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public List<Transaction> getLinesOldMode(String fileName) {
        List<Transaction> result = new ArrayList<>();
        try (FileInputStream fis = new FileInputStream(fileName);
             Scanner scanner = new Scanner(fis)) {
            boolean isFirstLine = true;
            int count = 0;
            while (scanner.hasNext() && count < 1000) {
                String line = scanner.nextLine();
                if (isFirstLine) {
                    isFirstLine = false;
                    continue;
                }
                String[] chunks = line.split(",");
                addToResultList(result, chunks);
                count++;
            }
            return result;
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    private void addToResultList(List<Transaction> result, String[] chunks) {
        result.add(new Transaction(
                Integer.parseInt(chunks[0]),
                TransactionType.valueOf(chunks[1]),
                new BigDecimal(chunks[2]),
                new TransactionCustomer(chunks[3], new BigDecimal(chunks[4]), new BigDecimal(chunks[5])),
                new TransactionCustomer(chunks[6], new BigDecimal(chunks[7]), new BigDecimal(chunks[8])),
                Integer.parseInt(chunks[9]) == 0 ? Boolean.FALSE : Boolean.TRUE,
                Integer.parseInt(chunks[10]) == 0 ? Boolean.FALSE : Boolean.TRUE));
    }
}
