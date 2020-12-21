package com.ledger.app.models;

public class TransactionStatus {
    public final boolean success;
    public final String message;
    public TransactionStatus(boolean success, String message){
        this.success = success;
        this.message = message;
    }
}
