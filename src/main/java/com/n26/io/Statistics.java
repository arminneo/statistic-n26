package com.n26.io;

import java.math.BigDecimal;
import java.util.DoubleSummaryStatistics;

import static com.n26.utils.MoneyFormatter.formatMoney;

public class Statistics {

    private String sum;
    private String avg;
    private String min;
    private String max;
    private long count;

    public Statistics() {
    }

    public Statistics(String sum, String avg, String max, String min, long count) {
        this.sum = sum;
        this.avg = avg;
        this.max = max;
        this.min = min;
        this.count = count;
    }

    public String getSum() {
        return sum;
    }

    public void setSum(String sum) {
        this.sum = sum;
    }

    public String getAvg() {
        return avg;
    }

    public void setAvg(String avg) {
        this.avg = avg;
    }

    public String getMax() {
        return max;
    }

    public void setMax(String max) {
        this.max = max;
    }

    public String getMin() {
        return min;
    }

    public void setMin(String min) {
        this.min = min;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public static Statistics of(BigDecimal sum, BigDecimal avg, BigDecimal max, BigDecimal min, long count) {
        return new Statistics(formatMoney(sum), formatMoney(avg), formatMoney(max), formatMoney(min), count);
    }

    public static Statistics of(DoubleSummaryStatistics statistics) {
        String sum = formatMoney(statistics.getSum());
        String avg = formatMoney(statistics.getAverage());
        String max = formatMoney(statistics.getMax());
        String min = formatMoney(statistics.getMin());

        return new Statistics(sum, avg, max, min, statistics.getCount());
    }
}
