package com.revolut.transfer.service;

public enum ErrorCode {
    NOT_ENOUGH_MONEY("MONEY_100"),
    ACCOUNT_NOT_FOUND("MONEY_101"),
    UNKNOWN_ERROR("MONEY_102"),
    NEGATIVE_NUMBER("MONEY_103");


    private final String code;

    ErrorCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
