package com.n26.controllers;

import com.n26.io.Transaction;
import com.n26.stores.TransactionStore;
import com.n26.validations.Validators;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/transactions")
public class TransactionsController extends BaseController {

    private final TransactionStore transactionStore;

    public TransactionsController(TransactionStore transactionStore) {
        this.transactionStore = transactionStore;
    }

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody Transaction transaction) {
        if (null == transaction || !Validators.isTransactionValid(transaction)) {
            return new ResponseEntity<>(UNPROCESSABLE_ENTITY);
        }
        if (Validators.isTransactionOld.apply(transaction)) {
            return new ResponseEntity<>(NO_CONTENT);
        }
        transactionStore.add(transaction);
        return new ResponseEntity<>(CREATED);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete() {
        transactionStore.clear();
    }
}
