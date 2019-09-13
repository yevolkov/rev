package com.revolut.transfer.dao;

import com.revolut.transfer.model.Account;

public interface AccountDao {

    Account getAccountByNumber(Long accountNumber) throws AccountNotFoundException;

    class AccountNotFoundException extends Exception {
        public AccountNotFoundException(String message) {
            super(message);
        }
    }
}
