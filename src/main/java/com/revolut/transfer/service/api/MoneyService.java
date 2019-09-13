package com.revolut.transfer.service.api;

import com.revolut.transfer.service.ErrorCode;

public interface MoneyService {

    void transfer(String fromAccount, String toAccount, String amount) throws MoneyServiceException;

    class MoneyServiceException extends Exception {
        public MoneyServiceException(ErrorCode errorCode) {
            super(errorCode.getCode());
        }
    }
}
