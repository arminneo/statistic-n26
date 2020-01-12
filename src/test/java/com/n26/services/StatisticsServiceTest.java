package com.n26.services;

import com.n26.io.Statistics;
import com.n26.io.Transaction;
import com.n26.stores.TransactionStore;
import com.n26.Application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import org.junit.Test;
import org.junit.Before;
import org.junit.runner.RunWith;

import java.util.Arrays;
import java.time.Instant;
import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class StatisticsServiceTest {

    @Autowired
    private TransactionStore store;

    @Autowired
    private StatisticsService service;

    @Before
    public void init() {
        store.clear();
    }

    @Test
    public void testGetStatisticsWithRounding() {
        buildDataSet();

        Statistics stats = service.getStatistics();

        assertEquals("51915.50", stats.getSum());
        assertEquals("50000.00", stats.getMax());
        assertEquals("8.86", stats.getMin());
        assertEquals("8652.58", stats.getAvg());
        assertEquals(6, stats.getCount());
    }

    private void buildDataSet() {
        store.addAll(Arrays.asList(
                transactionOf(54),
                transactionOf(8.86),
                transactionOf(654),
                transactionOf(543.75),
                transactionOf(654.886),
                transactionOf(50000)
        ));
    }

    private Transaction transactionOf(double value) {
        return new Transaction(new BigDecimal(value), Instant.now());
    }
}