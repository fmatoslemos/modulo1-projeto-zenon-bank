package br.com.zenon.fraud;

import java.text.NumberFormat;
import java.util.Currency;
import java.util.Locale;
import java.util.ResourceBundle;

public class ReportMain {

    static void main(String[] args) {
        Locale locale;
        if (args.length == 2) {
            locale = Locale.of(args[0], args[1]);
        } else if(args.length == 0) {
            locale = Locale.of("pt", "BR");
        } else {
            IO.println("Uso: java ReportMain pt BR");
            return;
        }

        ResourceBundle mensagens = ResourceBundle.getBundle("report", locale);
        NumberFormat formatadorMoeda = NumberFormat.getCurrencyInstance(locale);
        formatadorMoeda.setCurrency(Currency.getInstance(Locale.of("en", "US")));
        NumberFormat formatadorNumero = NumberFormat.getNumberInstance(locale);

        var transaction = new TransactionReport();
        TransactionReport.Statistics statistics = transaction.generateReport("data/PS_20174392719_1491204439457_log.csv");
        IO.println("%s %s".formatted(mensagens.getString("br.com.zenon.fraud.total_transacoes"), formatadorNumero.format(statistics.totalTransactions())));
        IO.println("%s %s ".formatted(mensagens.getString("br.com.zenon.fraud.total_fraudes"), formatadorNumero.format(statistics.totalFrauds())));
        IO.println("%s %s".formatted(mensagens.getString("br.com.zenon.fraud.valor.total"), formatadorMoeda.format(statistics.totalAmount())));
    }
}
