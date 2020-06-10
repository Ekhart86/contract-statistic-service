package ru.ekhart86.contractservice.services;

import ru.ekhart86.contractservice.dto.ContractResponse;

public interface ContractService {
    ContractResponse findContractsByProduct(String productCode, String date);
}
