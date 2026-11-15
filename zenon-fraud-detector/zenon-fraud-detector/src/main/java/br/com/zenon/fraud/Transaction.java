package br.com.zenon.fraud;

import java.math.BigDecimal;

public record Transaction(Integer step,
                          TransactionType type,
                          BigDecimal amount,
                          String nameOrig,
                          BigDecimal oldbalanceOrg,
                          BigDecimal newbalanceOrig,
                          String nameDest,
                          BigDecimal oldbalanceDest,
                          BigDecimal newbalanceDest,
                          int isFraud,
                          int isFlaggedFraud) {

    public Transaction(Integer step, TransactionType type, BigDecimal amount, String nameOrig, BigDecimal oldbalanceOrg, BigDecimal newbalanceOrig, String nameDest, BigDecimal oldbalanceDest, BigDecimal newbalanceDest, int isFraud, int isFlaggedFraud) {
        this.step = step;
        this.type = type;
        this.amount = amount;
        this.nameOrig = nameOrig;
        this.oldbalanceOrg = oldbalanceOrg;
        this.newbalanceOrig = newbalanceOrig;
        this.nameDest = nameDest;
        this.oldbalanceDest = oldbalanceDest;
        this.newbalanceDest = newbalanceDest;
        this.isFraud = isFraud;
        this.isFlaggedFraud = isFlaggedFraud;
    }
}
