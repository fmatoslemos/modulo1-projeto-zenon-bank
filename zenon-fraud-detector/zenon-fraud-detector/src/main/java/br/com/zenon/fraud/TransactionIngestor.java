package br.com.zenon.fraud;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
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
                result.add(new Transaction(
                        Integer.parseInt(lineSplited[0]),
                        TransactionType.valueOf(lineSplited[1]),
                        new BigDecimal(lineSplited[2]),
                        new TransactionCustomer(lineSplited[3], new BigDecimal(lineSplited[4]), new BigDecimal(lineSplited[5])),
                        new TransactionCustomer(lineSplited[6], new BigDecimal(lineSplited[7]), new BigDecimal(lineSplited[8])),
                        Integer.parseInt(lineSplited[9]) == 0 ? Boolean.FALSE : Boolean.TRUE,
                        Integer.parseInt(lineSplited[10]) == 0 ? Boolean.FALSE : Boolean.TRUE));
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
            for (int i = 1; i <= 1000 && scanner.hasNext(); i++) {
                String line = scanner.nextLine();
                if (isFirstLine) {
                    isFirstLine = false;
                    continue;
                }
                String[] lineSplited = line.split(",");
                result.add(new Transaction(
                        Integer.parseInt(lineSplited[0]),
                        TransactionType.valueOf(lineSplited[1]),
                        new BigDecimal(lineSplited[2]),
                        new TransactionCustomer(lineSplited[3], new BigDecimal(lineSplited[4]), new BigDecimal(lineSplited[5])),
                        new TransactionCustomer(lineSplited[6], new BigDecimal(lineSplited[7]), new BigDecimal(lineSplited[8])),
                        Integer.parseInt(lineSplited[9]) == 0 ? Boolean.FALSE : Boolean.TRUE,
                        Integer.parseInt(lineSplited[10]) == 0 ? Boolean.FALSE : Boolean.TRUE));
            }
            return result;
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}
