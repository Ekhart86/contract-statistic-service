package ru.ekhart86.contractservice.exception;

public class IllegalEconomicSectorException extends RuntimeException {
    public IllegalEconomicSectorException(String exception) {
        super("Incorrect economic code : " + exception);
    }
}
