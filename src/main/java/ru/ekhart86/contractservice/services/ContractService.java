package ru.ekhart86.contractservice.services;

import ru.ekhart86.contractservice.dto.MainResponse;

public interface ContractService {
    MainResponse findContractsByProduct(String productCode, String date);
}
