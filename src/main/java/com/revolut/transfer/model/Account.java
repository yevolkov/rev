package com.revolut.transfer.model;

import java.util.Objects;

public class Account {

    private final Long accountNumber;
    private final Amount amount;

    public Account(Long accountNumber, Amount amount) {
        this.accountNumber = accountNumber;
        this.amount = amount;
    }

    public void debit(Amount augend) {
        amount.add(augend.getAmount());
    }

    public void credit(Amount augend) {
        amount.subtract(augend.getAmount());
    }

    public Amount getAmount() {
        return amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Account)) return false;
        Account account = (Account) o;
        return Objects.equals(accountNumber, account.accountNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountNumber);
    }

    @Override
    public String toString() {
        return "Account{" +
                "accountNumber=" + accountNumber +
                ", amount=" + amount +
                '}';
    }
}
