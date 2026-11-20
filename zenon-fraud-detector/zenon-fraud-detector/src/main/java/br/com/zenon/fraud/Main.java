package br.com.zenon.fraud;

import java.util.List;


public class Main {
    void main() {
        TransactionIngestor transactionIngestor = new TransactionIngestor();
//        List<Transaction> linesNewMode =
//                transactionIngestor.getLinesNewMode("data/PS_20174392719_1491204439457_log.csv");
//        linesNewMode.stream().limit(10).forEach(IO::println);
//        IO.println(linesNewMode.size());
//        IO.println("----------------------------------------------------------------------------------------------------");
//        List<Transaction> linesNewModeBadData =
//                transactionIngestor.getLinesNewMode("data/paysim_with_bad_data.csv");
//        IO.println(linesNewModeBadData.size());
//        linesNewModeBadData.forEach(IO::println);
        IO.println("----------------------------------------------------------------------------------------------------");
        IO.println("----------------------------------------------------------------------------------------------------");
        IO.println("------------------------------USING FRAUD ANALYZER--------------------------------------------------");
        FraudAnalyser fraudAnalyser = new FraudAnalyser(transactionIngestor.getLinesNewMode("data/PS_20174392719_1491204439457_log.csv"));
        IO.println("1. Total de Fraudes: " + fraudAnalyser.countFrauds());

        IO.println("2. Top 3 Fraudes de Maior Valor:");
        fraudAnalyser.findHighestFrauds(3).forEach(transaction -> IO.println("%.2f".formatted(transaction.amount())));

        IO.println("3. Clientes Suspeitos:");
        List<String> topSuspiciousClients = fraudAnalyser.findTopSuspiciousClients(5);
        topSuspiciousClients.forEach(IO::println);

        IO.println("4. Prejuízo Total: " + fraudAnalyser.calculateTotalFraudsLosses());

        IO.println("5. Fraudes por Tipo: ");
        IO.println(" - CASH_OUT: " + fraudAnalyser.countTotalFraudsByTransactionType().getOrDefault(TransactionType.CASH_OUT, 0L));
        IO.println(" - TRANSFER: " + fraudAnalyser.countTotalFraudsByTransactionType().getOrDefault(TransactionType.TRANSFER, 0L));

        IO.println("----------------------------------------------------------------------------------------------------");
        IO.println("----------------------------------------------------------------------------------------------------");
        IO.println("------------------------------BENCHMARKS------------------------------------------------------------");
        TransactionRepository transactionListRepository = new TransactionListRepository(transactionIngestor.getLinesNewMode("data/PS_20174392719_1491204439457_log.csv"));
//        transactionListRepository.findByOriginName("C12345").ifPresentOrElse(IO::println, () -> IO.println("Transação não encontrada para o cliente C12345"));
//        transactionListRepository.findByOriginName("C1231006815").ifPresentOrElse(IO::println, () -> IO.println("Transação não encontrada para o cliente C1231006815"));

        long startTimeList = System.nanoTime();
        transactionListRepository.findByOriginName("C1868032458").ifPresentOrElse(IO::println, () -> IO.println("Transação não encontrada para o cliente C1868032458"));
        long endTimeList = System.nanoTime();
        IO.println("Tempo usando Lista " + (endTimeList - startTimeList) / 1_000_000.0 + " ms");

        TransactionRepository transactionMapRepository = new TransactionMapRepository(transactionIngestor.getLinesNewMode("data/PS_20174392719_1491204439457_log.csv"));
        long startTimeMap = System.nanoTime();
        transactionMapRepository.findByOriginName("C1868032458").ifPresentOrElse(IO::println, () -> IO.println("Transação não encontrada para o cliente C1868032458"));
        long endTimeMap = System.nanoTime();
        IO.println("Tempo usando Map " + (endTimeMap - startTimeMap) / 1_000_000.0 +  "ms");
    }
}
