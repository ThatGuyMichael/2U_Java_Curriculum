package com.ledger.app.models;

import java.math.BigDecimal;

public class TransactionUpdatedValue {
    public BigDecimal transactionValue;

    public TransactionUpdatedValue(){ }
    public TransactionUpdatedValue(BigDecimal transactionValue){
        this.transactionValue = transactionValue;
    }
}
