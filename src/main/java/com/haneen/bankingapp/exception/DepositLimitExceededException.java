package com.haneen.bankingapp.exception;

public class DepositLimitExceededException extends RuntimeException {
    public DepositLimitExceededException(String message) {
        super(message);
    }
}
