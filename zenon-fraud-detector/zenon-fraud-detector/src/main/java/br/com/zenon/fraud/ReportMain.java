package br.com.zenon.fraud;

public class ReportMain {

    static void main() {
        var transaction = new TransactionReport();
        long startTime = System.nanoTime();
        TransactionReport.Statistics statistics = transaction.generateReport("data/PS_20174392719_1491204439457_log.csv");
        long endTime = System.nanoTime();
        IO.println("Total de linhas: " + statistics.totalTransactions());
        IO.println("Total de fraudes: " + statistics.totalFrauds());
        IO.println("Valor total transacionado: " + statistics.totalAmount());
        IO.println("Tempo para gerar relatório: " + (endTime - startTime) / 1_000_000_000.0 + " s");
    }
}
