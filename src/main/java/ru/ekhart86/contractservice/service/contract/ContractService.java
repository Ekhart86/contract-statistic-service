package ru.ekhart86.contractservice.service.contract;

import ru.ekhart86.contractservice.domain.ContractDTO;

import java.sql.Date;
import java.util.List;

public interface ContractService {
    List<ContractDTO> findBySignDateAndProductCode(Date startDate, Date endDate, String productCode, String currencyCode);
    boolean dateExist(Date date);
}
