package ru.ekhart86.contractservice.exception;

public class DateOrderException extends RuntimeException {
    public DateOrderException(String exception) {
        super("Period start date must be earlier than period end date : " + exception);
    }
}
