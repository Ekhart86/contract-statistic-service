package ru.ekhart86.contractservice.exception;

public class DateNotExistException extends RuntimeException {
    public DateNotExistException(String exception) {
        super("The specified date is not in the database : " + exception);
    }
}
