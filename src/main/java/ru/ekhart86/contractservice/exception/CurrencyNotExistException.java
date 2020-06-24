package ru.ekhart86.contractservice.exception;

public class CurrencyNotExistException extends RuntimeException {
    public CurrencyNotExistException(String exception) {
        super("Invalid currency type specified : " + exception);
    }
}
