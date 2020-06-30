package ru.ekhart86.contractservice.service.contract;

import org.springframework.stereotype.Service;
import ru.ekhart86.contractservice.domain.ContractDTO;
import ru.ekhart86.contractservice.repository.ContractRepository;

import java.sql.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ContractServiceImpl implements ContractService {

    private final ContractRepository contractRepository;

    public ContractServiceImpl(ContractRepository contractRepository) {
        this.contractRepository = contractRepository;
    }

    @Override
    public List<ContractDTO> findBySignDateAndProductCode(Date startDate, Date endDate, String productCode, String currencyCode) {
        return contractRepository.findBySignDateAndProductCode(startDate, endDate, productCode, currencyCode).stream().map(contract ->
                new ContractDTO(
                        contract.getProductCode(),
                        contract.getRegionCode(),
                        contract.getSignDate(),
                        contract.getAmount(),
                        contract.getCurrencyCode()))
                .collect(Collectors.toList());
    }
    @Override
    public List<ContractDTO> findBySignDateAndProductCode(Date startDate, Date endDate, List<String> productCode, String currencyCode) {
        return contractRepository.findBySignDateAndListOfProductCode(startDate, endDate, productCode, currencyCode).stream().map(contract ->
                new ContractDTO(
                        contract.getProductCode(),
                        contract.getRegionCode(),
                        contract.getSignDate(),
                        contract.getAmount(),
                        contract.getCurrencyCode()))
                .collect(Collectors.toList());
    }

    @Override
    public boolean dateExist(Date date) {
        return contractRepository.findBySignDate(date).size() > 0;
    }

    @Override
    public boolean currencyExist(String currency) {
        return contractRepository.findByCurrencyCode(currency).size() > 0;
    }

}
