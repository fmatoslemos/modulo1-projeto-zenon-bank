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
                String[] lineSplited = line.split(",");
                addToResultList(result, lineSplited);
                count++;
            }
            return result;
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    private void addToResultList(List<Transaction> result, String[] splittedLine) {
        result.add(new Transaction(
                Integer.parseInt(splittedLine[0]),
                TransactionType.valueOf(splittedLine[1]),
                new BigDecimal(splittedLine[2]),
                new TransactionCustomer(splittedLine[3], new BigDecimal(splittedLine[4]), new BigDecimal(splittedLine[5])),
                new TransactionCustomer(splittedLine[6], new BigDecimal(splittedLine[7]), new BigDecimal(splittedLine[8])),
                Integer.parseInt(splittedLine[9]) == 0 ? Boolean.FALSE : Boolean.TRUE,
                Integer.parseInt(splittedLine[10]) == 0 ? Boolean.FALSE : Boolean.TRUE));
    }
}
