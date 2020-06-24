package ru.ekhart86.contractservice.facade;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.ekhart86.contractservice.domain.ContractDTO;
import ru.ekhart86.contractservice.domain.ProductDTO;
import ru.ekhart86.contractservice.domain.SectorStatisticResponseDTO;
import ru.ekhart86.contractservice.exception.CurrencyNotExistException;
import ru.ekhart86.contractservice.exception.DateNotExistException;
import ru.ekhart86.contractservice.exception.DateOrderException;
import ru.ekhart86.contractservice.exception.IllegalEconomicSectorException;
import ru.ekhart86.contractservice.service.contract.ContractServiceImpl;
import ru.ekhart86.contractservice.service.product.ProductServiceImpl;
import ru.ekhart86.contractservice.service.sector.SectorServiceImpl;
import ru.ekhart86.contractservice.util.DateValidator;

import java.sql.Date;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StatisticFacade {

    private final Logger logger = LoggerFactory.getLogger(StatisticFacade.class);
    private final ProductServiceImpl productService;
    private final ContractServiceImpl contractService;
    private final SectorServiceImpl economicSectorService;
    private final DateValidator dateValidator;

    public StatisticFacade(ProductServiceImpl productService,
                           ContractServiceImpl contractService,
                           SectorServiceImpl economicSectorService,
                           DateValidator dateValidator) {
        this.productService = productService;
        this.contractService = contractService;
        this.economicSectorService = economicSectorService;
        this.dateValidator = dateValidator;
    }

    /**
     * The method returns a statistics report about number of contracts concluded,
     * their amount, for the specified period in the specified economic code and currency
     */
    public SectorStatisticResponseDTO getStatisticByEconomicCode(Date startDate, Date endDate, String economicCode, String currencyCode) {
        var listProductCode = productService.findProductsByEconomicCode(economicCode)
                .stream()
                .map(ProductDTO::getCode)
                .collect(Collectors.toList());
        List<ContractDTO> listContractDTO = listProductCode
                .stream()
                .map(productCode -> contractService.findBySignDateAndProductCode(startDate, endDate, productCode, currencyCode))
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
        var quantityContracts = listContractDTO.size();
        var totalAmount = listContractDTO.stream().mapToLong(ContractDTO::getAmount).sum();
        var economicSectorDescription = economicSectorService.getEconomicSectorDescription(economicCode);
        return new SectorStatisticResponseDTO(economicCode, economicSectorDescription, startDate, endDate, quantityContracts, totalAmount, currencyCode);
    }

    public void checkEconomicSector(String sectorCode) {
        if (!economicSectorService.economicSectorExist(sectorCode.toUpperCase())) {
            logger.info("sectorCode " + sectorCode + " don't exist");
            throw new IllegalEconomicSectorException(sectorCode);
        }
    }

    public void checkDateInDataBase(List<Date> dates) {
        dates.forEach(date -> {
            if (!dateValidator.checkDateInDataBase(date)) {
                logger.info("date " + date + " don't exist in database");
                throw new DateNotExistException(date.toString());
            }
        });
    }

    public void startOlderThanEnd(Date startDate, Date endDate) {
        if (!dateValidator.startOlderThanEnd(startDate, endDate)) {
            logger.info("Period start date must be earlier than period end date : startDate = " + startDate + " and endDate = " + endDate);
            throw new DateOrderException("startDate = " + startDate + " and endDate = " + endDate);
        }
    }

    public void checkCurrencyCode(String currencyCode) {
        if (!contractService.currencyExist(currencyCode)) {
            logger.info("currencyCode " + currencyCode + " don't exist in database");
            throw new CurrencyNotExistException(currencyCode);
        }
    }
}
