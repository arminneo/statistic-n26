package com.n26.validations;

import com.n26.io.Transaction;

import java.time.Duration;
import java.time.Instant;
import java.util.function.Function;

public class Validators {
    public static final long EXPIRES_AFTER_MS = 60_000L;

    public static Function<Transaction, Boolean> isTransactionInFuture = transaction ->
            getDiffFromNow(transaction.getTimestamp()) < 0;

    public static Function<Transaction, Boolean> isTransactionOld = transaction ->
            getDiffFromNow(transaction.getTimestamp()) > EXPIRES_AFTER_MS;

    public static boolean isTransactionValid(Transaction transaction) {
        if (transaction.getTimestamp() == null) return false;
        if (transaction.getAmount() == null) return false;
        return !isTransactionInFuture.apply(transaction);
    }

    private static long getDiffFromNow(Instant from) {
        return Duration.between(from, Instant.now()).toMillis();
    }
}
