package com.n26.services;

import com.n26.io.Statistics;
import com.n26.io.Transaction;
import org.springframework.stereotype.Service;
import com.n26.stores.TransactionStore;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.List;

/**
 * Assumption:
 *   + To keep it simple I just used a normal, every time statistics processing.
 *   + Another approach O(1):
 *     Using MOVING WINDOW algorithm
 *     Have a sorted list of transactions by time
 *     On add -> add this transaction to statistics
 *     On timeout -> remove timed-out transaction in statistics.
 *     But this approach need at least 2 day to implement successfully. Which I didn't had.
 */

@Service
public class StatisticsService {

    final TransactionStore store;

    public StatisticsService(TransactionStore transactionStore) {
        this.store = transactionStore;
    }

    public Statistics getStatistics() {
        return processStatistics();
    }


    private Statistics processStatistics() {
        List<Transaction> transactions = store.getValidTransactionList();
        BigDecimal sum = BigDecimal.ZERO;
        BigDecimal max = null, min = null, avg;
        for (Transaction txn : transactions) {
            BigDecimal amount = txn.getAmount();
            if (null == amount) continue;
            if (null == max) {
                max = amount;
                min = amount;
            }
            max = amount.max(max);
            min = amount.min(min);
            sum = amount.add(sum);
        }

        if (null == max || transactions.size() < 1) {
            min = max = avg = BigDecimal.ZERO;
        } else {
            avg = sum.divide(BigDecimal.valueOf(transactions.size()), MathContext.DECIMAL128);
        }
        return Statistics.of(sum, avg, max, min, transactions.size());
    }

}
