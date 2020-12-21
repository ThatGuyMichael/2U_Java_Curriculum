package com.ledger.app.models;

import java.math.BigDecimal;

public class TransactionToAdd {
    public final String sender;
    public final String recipient;
    public final BigDecimal transactionValue;
    TransactionToAdd(String sender, String recipient, BigDecimal transactionValue){
        this.sender = sender;
        this.recipient = recipient;
        this.transactionValue = transactionValue;
    }
}
