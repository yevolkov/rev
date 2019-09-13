package com.revolut.transfer.service;

import com.revolut.transfer.model.Account;
import com.revolut.transfer.model.Amount;
import com.revolut.transfer.service.api.MoneyTransfer;

import java.util.Objects;

public class MoneyTransferImpl implements MoneyTransfer {

    private static final Object collisionLock = new Object();

    @Override
    public void transfer(final Account from, final Account to, final Amount amount) throws InsufficientFundsException {

        Objects.requireNonNull(from, "fromAccount couldn't be null!");
        Objects.requireNonNull(to, "toAccount couldn't be null!");
        Objects.requireNonNull(amount, "amount couldn't be null!");

        class Helper {
            private void transfer() throws InsufficientFundsException {
                if (from.getAmount().compareTo(amount) < 0 ||
                        !from.getAmount().isPositiveBalance()) {
                    throw new InsufficientFundsException("Not enough money");
                } else {
                    from.credit(amount);
                    to.debit(amount);
                }
            }
        }

        int fromHash = Objects.hashCode(from);
        int toHash = Objects.hashCode(to);

        if (fromHash < toHash) {
            synchronized (from) {
                synchronized (to) {
                    new Helper().transfer();
                }
            }
        } else if (fromHash > toHash) {
            synchronized (to) {
                synchronized (from) {
                    new Helper().transfer();
                }
            }
        } else {
            synchronized (collisionLock) {
                synchronized (to) {
                    synchronized (from) {
                        new Helper().transfer();
                    }
                }
            }
        }
    }
}
