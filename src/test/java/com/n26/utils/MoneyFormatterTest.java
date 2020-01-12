package com.n26.utils;

import org.junit.Test;

import static org.junit.Assert.*;
import static com.n26.utils.MoneyFormatter.formatMoney;
public class MoneyFormatterTest {

    @Test
    public void formatMoneyTest() {
        assertEquals(formatMoney(10.345), "10.35");
        assertEquals(formatMoney(10.344), "10.34");
        assertEquals(formatMoney(10.8), "10.80");
        assertEquals(formatMoney(0.1), "0.10");
    }
    @Test
    public void formatMoneyValidationsTest() {
        assertEquals(formatMoney(Double.NaN), "0.00");
        assertEquals(formatMoney(Double.NEGATIVE_INFINITY), "0.00");
        assertEquals(formatMoney(Double.POSITIVE_INFINITY), "0.00");
    }
}