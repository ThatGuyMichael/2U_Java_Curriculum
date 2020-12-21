package com.ledger.app.models;

import java.math.BigDecimal;

public class Transaction {
    public final Long id;
    public final String sender;
    public final String recipient;
    public final BigDecimal transactionValue;

    public Transaction(Long id, String sender, String recipient, BigDecimal transactionValue){
        this.id = id;
        this.sender = sender;
        this.recipient = recipient;
        this.transactionValue = transactionValue;
    }
}
