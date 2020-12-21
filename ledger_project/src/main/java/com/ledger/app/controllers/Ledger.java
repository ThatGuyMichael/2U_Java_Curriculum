package com.ledger.app.controllers;

import com.ledger.app.models.*;
import com.ledger.app.repositorys.TransactionRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
public class Ledger {

    private TransactionRepository repository;

    Ledger(TransactionRepository repository){
        this.repository = repository;
    }

    @GetMapping("/transaction")
    List<Transaction> findAll(){
        return repository.findAll();
    }

    @GetMapping("/transaction/sum")
    TransactionSum getSumOfAll(){
        BigDecimal sum = repository.findAll()
            .stream()
            .map(transaction -> transaction.transactionValue) // Get all the values for transactionValue
            .reduce(BigDecimal.ZERO, BigDecimal::add);// Sums all the transactionValue(s), default zero if list is empty
        return new TransactionSum(sum);
    }

    @GetMapping("/transaction/{id}")
    Transaction findOne(@PathVariable Long id){
        Transaction result = repository.findOne(id);
        if(result != null){
            return result;
        } else {
            throw new TransactionNotFoundException(id);
        }
    }

    @PostMapping("/transaction")
    TransactionStatus add(@RequestBody TransactionToAdd newTransaction){
        int result = repository.add(newTransaction);
        return  new TransactionStatus(result > 0, null);
    }

    @PutMapping("/transaction/{id}")
    TransactionStatus updateValue(@PathVariable Long id, @RequestBody TransactionUpdatedValue updatedValued){
        int result = repository.updateValue(id, updatedValued.transactionValue);
        if(result > 0) {
            return new TransactionStatus(true, null);
        } else {
            throw new TransactionNotFoundException(id);
        }
    }

    @DeleteMapping("/transaction/{id}")
    TransactionStatus softDelete(@PathVariable Long id){
        int result = repository.softDelete(id);
        if(result > 0) {
            return new TransactionStatus(true, null);
        } else {
            throw new TransactionNotFoundException(id);
        }
    }

    @ResponseBody
    @ExceptionHandler(TransactionNotFoundException.class) // Calls the below method whenever this exception is raised in this controller
    @ResponseStatus(HttpStatus.NOT_FOUND) // Set the HTTP status code to 404 for thr response
    TransactionStatus transactionNotFoundHandler(TransactionNotFoundException ex) {
        return new TransactionStatus(false, ex.getMessage());
    }
}
