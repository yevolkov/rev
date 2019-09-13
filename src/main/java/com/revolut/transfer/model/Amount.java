package com.revolut.transfer.model;

import java.math.BigDecimal;

public class Amount implements Comparable<Amount> {

    private BigDecimal amount;

    public Amount(BigDecimal amount) {
        this.amount = amount;
    }

    void add(BigDecimal augend) {
        amount = amount.add(augend);
    }

    public boolean isPositiveBalance() {
        return amount.compareTo(BigDecimal.ZERO) > 0;
    }

    void subtract(BigDecimal augend) {
        amount = amount.subtract(augend);
    }

    @Override
    public int compareTo(Amount o) {
        return amount.compareTo(o.getAmount());
    }

    public BigDecimal getAmount() {
        return new BigDecimal(amount.toString());
    }

    @Override
    public String toString() {
        return String.valueOf(amount);
    }
}
