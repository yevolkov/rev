package com.revolut.transfer.service;

import com.google.inject.Inject;
import com.revolut.transfer.dao.AccountDao;
import com.revolut.transfer.model.Account;
import com.revolut.transfer.model.Amount;
import com.revolut.transfer.service.api.MoneyService;
import com.revolut.transfer.service.api.MoneyTransfer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.Objects;

import static com.revolut.transfer.service.ErrorCode.*;

public class MoneyServiceImpl implements MoneyService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MoneyServiceImpl.class);

    private final MoneyTransfer moneyTransfer;
    private final AccountDao accountDao;

    @Inject
    public MoneyServiceImpl(MoneyTransfer moneyTransfer, AccountDao accountDao) {
        this.moneyTransfer = moneyTransfer;
        this.accountDao = accountDao;
    }

    @Override
    public void transfer(String fromAccount, String toAccount, String amount) throws MoneyServiceException {
        if (Objects.equals(fromAccount, toAccount)) {
            return;
        }

        try {
            BigDecimal bigDecimal = new BigDecimal(amount);
            if (bigDecimal.compareTo(BigDecimal.ZERO) <= 0) {
                throw new MoneyServiceException(NEGATIVE_NUMBER);
            }
            Account from = accountDao.getAccountByNumber(Long.valueOf(fromAccount));
            Account to = accountDao.getAccountByNumber(Long.valueOf(toAccount));
            moneyTransfer.transfer(from, to, new Amount(bigDecimal));
        } catch (AccountDao.AccountNotFoundException | MoneyTransfer.InsufficientFundsException e) {
            LOGGER.error(e.getMessage(), e);
            throw new MoneyServiceException(
                    e instanceof AccountDao.AccountNotFoundException ?
                            ACCOUNT_NOT_FOUND : NOT_ENOUGH_MONEY);
        } catch (Exception ignored) {
            LOGGER.error(ignored.getMessage(), ignored);
            throw new MoneyServiceException(UNKNOWN_ERROR);
        }
    }

}
