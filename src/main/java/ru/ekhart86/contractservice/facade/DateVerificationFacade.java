package ru.ekhart86.contractservice.facade;

import org.springframework.stereotype.Service;
import ru.ekhart86.contractservice.exception.DateNotExistException;
import ru.ekhart86.contractservice.exception.DateOrderException;
import ru.ekhart86.contractservice.util.DateValidator;

import java.sql.Date;
import java.util.Arrays;

@Service
public class DateVerificationFacade {

    private final DateValidator dateValidator;

    public DateVerificationFacade(DateValidator dateValidator) {
        this.dateValidator = dateValidator;
    }

    public void checkDateInDataBase(Date... dates) {
        Arrays.asList(dates).forEach(date -> {
            if (!dateValidator.checkDateInDataBase(date)) throw new DateNotExistException(date.toString());
        });
    }

    public void startOlderThanEnd(Date startDate, Date endDate) {
        if (!dateValidator.startOlderThanEnd(startDate, endDate)) {
            throw new DateOrderException("startDate = " + startDate + " and endDate = " + endDate);
        }
    }
}
