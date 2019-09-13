package com.revolut.transfer.service;

import com.revolut.transfer.model.Account;
import com.revolut.transfer.model.Amount;
import com.revolut.transfer.service.api.MoneyTransfer;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;

public class MoneyTransferImplTest {

    private MoneyTransfer moneyTransfer = new MoneyTransferImpl();

    private Account fromAccount;
    private Account toAccount;
    private Amount amount;
    private Account fromAccountWithNotEnoughMoney;


    @Before
    public void init() {
        fromAccount = new Account(1L, new Amount(BigDecimal.valueOf(500L)));
        toAccount = new Account(2L, new Amount(BigDecimal.valueOf(-10L)));
        amount = new Amount(BigDecimal.valueOf(50L));
        fromAccountWithNotEnoughMoney = new Account(1L, new Amount(BigDecimal.valueOf(10L)));
    }

    @Test
    public void shouldTransferMoneyWhenEnoughMoney() throws MoneyTransfer.InsufficientFundsException {
        BigDecimal fromAmountBeforeTransaction = fromAccount.getAmount().getAmount();
        BigDecimal toAmountBeforeTransaction = toAccount.getAmount().getAmount();
        moneyTransfer.transfer(fromAccount, toAccount, amount);
        BigDecimal fromAmountAfterTransaction = fromAccount.getAmount().getAmount();
        BigDecimal toAmountAfterTransaction = toAccount.getAmount().getAmount();

        assertEquals(fromAmountBeforeTransaction.subtract(amount.getAmount()), fromAmountAfterTransaction);
        assertEquals(toAmountBeforeTransaction.add(amount.getAmount()), toAmountAfterTransaction);
    }

    @Test(expected = MoneyTransfer.InsufficientFundsException.class)
    public void shouldThrowExceptionWhenNotEnoughMoney() throws MoneyTransfer.InsufficientFundsException {
        moneyTransfer.transfer(fromAccountWithNotEnoughMoney, toAccount, amount);
    }

}
