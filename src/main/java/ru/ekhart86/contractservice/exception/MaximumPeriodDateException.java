package ru.ekhart86.contractservice.exception;

public class MaximumPeriodDateException extends RuntimeException {
    public MaximumPeriodDateException() {
        super("The maximum period is one month");
    }
}
