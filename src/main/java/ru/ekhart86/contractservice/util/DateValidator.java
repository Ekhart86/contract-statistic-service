package ru.ekhart86.contractservice.util;

import org.springframework.stereotype.Service;
import ru.ekhart86.contractservice.service.contract.ContractServiceImpl;

import java.sql.Date;

@Service
public class DateValidator {

    private final ContractServiceImpl contractService;

    public DateValidator(ContractServiceImpl contractService) {
        this.contractService = contractService;

    }

    public boolean checkDateInDataBase(Date date) {
        return contractService.dateExist(date);
    }

    public boolean startOlderThanEnd(Date startDate, Date endDate) {
        return startDate.before(endDate);
    }

}
