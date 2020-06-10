package ru.ekhart86.contractservice.services;

import ru.ekhart86.contractservice.model.gov.MainResponse;

public interface ContractService {
    MainResponse findContractsByOkpd(String okpd, String date);
}
