package com.n26.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class MoneyFormatter {

    public static String formatMoney(Double amount) {

        if (null == amount
                || amount.equals(Double.NaN)
                || amount.equals(Double.NEGATIVE_INFINITY)
                || amount.equals(Double.POSITIVE_INFINITY)) {
            return "0.00";
        }

        return new BigDecimal(amount)
                .setScale(2, RoundingMode.HALF_UP)
                .toString();
    }

    public static String formatMoney(BigDecimal amount) {
        if (null == amount) {
            return "0.00";
        }
        return amount
                .setScale(2, RoundingMode.HALF_UP)
                .toString();
    }
}
