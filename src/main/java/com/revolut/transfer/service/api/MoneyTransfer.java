package com.revolut.transfer.service.api;

import com.revolut.transfer.model.Account;
import com.revolut.transfer.model.Amount;

public interface MoneyTransfer {

    void transfer(final Account from, final Account to, final Amount amount) throws InsufficientFundsException;

    class InsufficientFundsException extends Exception {
        public InsufficientFundsException(String message) {
            super(message);
        }
    }
}
