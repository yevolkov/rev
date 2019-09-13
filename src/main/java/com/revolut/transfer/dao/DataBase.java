package com.revolut.transfer.dao;

import com.google.inject.Singleton;
import com.revolut.transfer.model.Account;
import com.revolut.transfer.model.Amount;

import java.math.BigDecimal;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Singleton
public class DataBase {

    private final Map<Long, Account> data;

    public DataBase() {
        data = new ConcurrentHashMap<>();
        data.put(1L,new Account(1L,new Amount(BigDecimal.valueOf(15489.01))));
        data.put(2L,new Account(2L,new Amount(BigDecimal.valueOf(-1234986.00))));
        data.put(3L,new Account(3L,new Amount(BigDecimal.valueOf(1565494.63))));
        data.put(4L,new Account(4L,new Amount(BigDecimal.valueOf(0))));
    }

    Account getAccount(Long number) {
        return data.get(number);
    }

    @Override
    public synchronized String toString() {
        StringBuilder stringBuilder = new StringBuilder();

        for (Object account : data.values()) {
            stringBuilder.append(account);
            stringBuilder.append(System.getProperty("line.separator"));
        }

        return stringBuilder.toString();
    }

}
