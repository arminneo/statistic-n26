package com.n26.stores;

import com.n26.io.Transaction;
import org.junit.Test;

import com.n26.Application;

import org.junit.Before;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Random;
import java.util.stream.IntStream;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class TransactionStoreTest {
    public static final int BOUND = 50;

    @Autowired
    private TransactionStore store;

    @Before
    public void setUp() {
        store.clear();
    }

    private Random random = new Random();

    @Test
    public void addTest() {
        int countTransactions = random.nextInt(BOUND);

        for (int i = 0; i < countTransactions; i++) {
            store.add(new Transaction(BigDecimal.valueOf(i), Instant.now().plusMillis(500)));
        }

        assertEquals(countTransactions, store.getValidTransactionList().size());
    }

    @Test
    public void clearTest() {
        addTest();

        store.clear();

        assertEquals(0, store.getList().size());
    }

    @Test
    public void deleteOldTransactionsTest() {
        int countOldTransactions = random.nextInt(BOUND);
        int countTransactions = random.nextInt(BOUND);

        for (int i = 0; i < countTransactions; i++) {
            store.add(new Transaction(BigDecimal.valueOf(i), Instant.now().plusMillis(500)));
        }

        for (int i = 0; i < countTransactions; i++) {
            store.add(new Transaction(BigDecimal.valueOf(i), Instant.now().minusMillis(61_111)));
        }

        store.deleteOldTransactions();

        assertEquals(countTransactions, store.getList().size());
    }

    @Test
    public void getValidTransactionListTest() {

        int countOldTransactions = random.nextInt(BOUND);
        int countTransactions = random.nextInt(BOUND);

        for (int i = 0; i < countTransactions; i++) {
            store.add(new Transaction(BigDecimal.valueOf(i), Instant.now().plusMillis(5000)));
        }

        for (int i = 0; i < countTransactions; i++) {
            store.add(new Transaction(BigDecimal.valueOf(i), Instant.now().minusMillis(61_111)));
        }

        assertEquals(countTransactions, store.getValidTransactionList().size());
    }

}