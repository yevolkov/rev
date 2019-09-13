package com.revolut.transfer.dao;

import com.google.inject.Inject;
import com.revolut.transfer.model.Account;

public class AccountDaoImpl implements AccountDao {

    private final DataBase dataBase;

    @Inject
    public AccountDaoImpl(DataBase dataBase) {
        this.dataBase = dataBase;
    }

    @Override
    public Account getAccountByNumber(Long accountNumber) throws AccountNotFoundException {
        Account account = dataBase.getAccount(accountNumber);
        if (account == null) {
            throw new AccountNotFoundException(accountNumber + " account number");
        }
        return account;
    }
}
