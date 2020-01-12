package com.n26.stores;

import com.n26.io.Transaction;
import com.n26.validations.Validators;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Repository
public class TransactionStore {

    private static final Logger log = LoggerFactory.getLogger(TransactionStore.class);

    private List<Transaction> storage = Collections.synchronizedList(new ArrayList<>());

    public List<Transaction> getList() {
        return storage;
    }

    public void add(Transaction transaction) {
        storage.add(transaction);
    }

    public void addAll(Collection<Transaction> transactions) {
        storage.addAll(transactions);
    }

    public void clear() {
        storage.clear();
    }

    public List<Transaction> getValidTransactionList() {
        deleteOldTransactions();
        return storage;
    }

    @Scheduled(fixedRateString = "1000")
    public synchronized void deleteOldTransactions() {
        int prevSize = storage.size();
        storage.removeIf(txn -> Validators.isTransactionOld.apply(txn));
        log.info("Deleted old transactions: {}", prevSize - storage.size());
    }
}
