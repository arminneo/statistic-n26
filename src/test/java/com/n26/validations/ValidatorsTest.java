package com.n26.validations;

import com.n26.io.Transaction;
import org.junit.Test;
import org.junit.Before;

import java.math.BigDecimal;
import java.time.Instant;

import static org.junit.Assert.*;

public class ValidatorsTest {

    private Transaction txn;

    @Before
    public void setUp() {
        txn = new Transaction(BigDecimal.valueOf(3445.4784), Instant.now());
    }

    @Test
    public void testIsFutureTransaction() {
        txn.setTimestamp(Instant.now().plusMillis(50L));
        assertTrue(Validators.isTransactionInFuture.apply(txn));
    }

    @Test
    public void testIsVeryOldTransaction() {
        assertFalse(Validators.isTransactionOld.apply(txn));

        txn.setTimestamp(Instant.now().minusMillis(60020L));
        assertTrue(Validators.isTransactionOld.apply(txn));

        txn.setTimestamp(Instant.now().plusMillis(60000L));
        assertFalse(Validators.isTransactionOld.apply(txn));
    }

    @Test
    public void isTransactionValid() {
        assertTrue(Validators.isTransactionValid(txn));
        assertFalse(Validators.isTransactionValid(new Transaction(null, Instant.now())));
        assertFalse(Validators.isTransactionValid(new Transaction(BigDecimal.ONE, null)));
        assertFalse(Validators.isTransactionValid(new Transaction(BigDecimal.ONE, Instant.now().plusMillis(60060L))));
    }
}