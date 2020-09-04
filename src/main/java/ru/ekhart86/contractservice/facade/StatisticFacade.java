package ru.ekhart86.contractservice.facade;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ekhart86.contractservice.domain.*;
import ru.ekhart86.contractservice.exception.*;
import ru.ekhart86.contractservice.service.contract.ContractServiceImpl;
import ru.ekhart86.contractservice.service.product.ProductServiceImpl;
import ru.ekhart86.contractservice.service.sector.SectorServiceImpl;
import ru.ekhart86.contractservice.util.DateValidator;


import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
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
    public SectorStatisticResponseDTO getStatisticByEconomicCode(Date startDate,
                                                                 Date endDate,
                                                                 String economicCode,
                                                                 String currencyCode) {
        List<String> listProductCode = getListProductByCode(economicCode);
        List<ContractDTO> listContractDTO = getListContractByDateAndProductCode(listProductCode, startDate, endDate, currencyCode);
        int quantityContracts = listContractDTO.size();
        long totalAmount = listContractDTO.stream().mapToLong(ContractDTO::getAmount).sum();
        String economicSectorDescription = economicSectorService.getEconomicSectorDescription(economicCode);
        return new SectorStatisticResponseDTO(economicCode,
                economicSectorDescription,
                startDate,
                endDate,
                quantityContracts,
                totalAmount,
                currencyCode);
    }


    public CompareSectorResponseDTO compareEconomicSector(Date startFromPeriod,
                                                          Date endFromPeriod,
                                                          Date startToPeriod,
                                                          Date endToPeriod,
                                                          String economicCode,
                                                          String currencyCode) {
        List<String> listProduct = getListProductByCode(economicCode);
        List<ContractDTO> listFromPeriodContracts = getListContractByDateAndProductCode(listProduct, startFromPeriod, endFromPeriod, currencyCode);
        List<ContractDTO> listToPeriodContracts = getListContractByDateAndProductCode(listProduct, startToPeriod, endToPeriod, currencyCode);
        int quantityFromContracts = listFromPeriodContracts.size();
        int quantityToContracts = listToPeriodContracts.size();
        long totalFromAmount = listFromPeriodContracts.stream().mapToLong(ContractDTO::getAmount).sum();
        long totalToAmount = listToPeriodContracts.stream().mapToLong(ContractDTO::getAmount).sum();
        String economicSectorDescription = economicSectorService.getEconomicSectorDescription(economicCode);
        Double contractPercentage = getComparePercentage(quantityFromContracts, quantityToContracts);
        Double amountPercentage = getComparePercentage(totalFromAmount, totalToAmount);
        return new CompareSectorResponseDTO(
                economicSectorDescription,
                economicCode,
                startFromPeriod,
                endFromPeriod,
                startToPeriod,
                endToPeriod,
                currencyCode,
                quantityFromContracts,
                quantityToContracts,
                totalFromAmount,
                totalToAmount,
                contractPercentage,
                amountPercentage);
    }

    public List<EconomicSectorDTO> getAllEconomicSectors() {
        return economicSectorService.getEconomicSectorList();
    }

    private List<String> getListProductByCode(String economicCode) {
        return productService.findProductsByEconomicCode(economicCode)
                .stream()
                .map(ProductDTO::getCode)
                .collect(Collectors.toList());
    }

    private List<ContractDTO> getListContractByDateAndProductCode(List<String> productCodeList, Date startPeriod, Date endPeriod, String currencyCode) {
        return contractService.findBySignDateAndProductCode(startPeriod, endPeriod, productCodeList, currencyCode);
    }

    private Double getComparePercentage(long fromDateAmount, long toDateAmount) {
        if (fromDateAmount == toDateAmount) return 0.0;
        if (fromDateAmount == 0.0) return 100.0;
        if (toDateAmount == 0.0) return -100.0;
        double result = fromDateAmount < toDateAmount ?
                ((double) toDateAmount - fromDateAmount) / ((double) toDateAmount / 100)
                : -((double) fromDateAmount - toDateAmount) / ((double) fromDateAmount / 100);
        BigDecimal bd = new BigDecimal(Double.toString(result));
        return bd.setScale(1, RoundingMode.HALF_UP).doubleValue();
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

    public void startIsBeforeEnd(Date startDate, Date endDate) {
        if (!dateValidator.startOlderThanEnd(startDate, endDate)) {
            logger.info("Period start date must be earlier than period end date : startDate = " + startDate + " and endDate = " + endDate);
            throw new DateOrderException("startDate = " + startDate + " and endDate = " + endDate);
        }
    }

    public void periodIsValid(Date startDate, Date endDate) {
        if (startDate.toLocalDate().plusMonths(1).isBefore(endDate.toLocalDate())) {
            throw new MaximumPeriodDateException();
        }
    }

    public void checkCurrencyCode(String currencyCode) {
        if (!contractService.currencyExist(currencyCode)) {
            logger.info("currencyCode " + currencyCode + " don't exist in database");
            throw new CurrencyNotExistException(currencyCode);
        }
    }
}
