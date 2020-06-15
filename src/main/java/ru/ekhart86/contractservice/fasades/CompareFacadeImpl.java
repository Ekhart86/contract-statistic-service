package ru.ekhart86.contractservice.fasades;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.ekhart86.contractservice.dao.ProductDao;
import ru.ekhart86.contractservice.dto.ContractResponse;
import ru.ekhart86.contractservice.enums.ErrorMessage;
import ru.ekhart86.contractservice.model.response.ComparisonProductResponse;
import ru.ekhart86.contractservice.services.ContractService;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

@Service
public class CompareFacadeImpl implements CompareFacade {

    private final Logger logger = LoggerFactory.getLogger(CompareFacadeImpl.class);
    private final ContractService contractService;
    private final ProductDao productDao;

    public CompareFacadeImpl(ContractService contractService, ProductDao productDao) {
        this.contractService = contractService;
        this.productDao = productDao;
    }

    @Override
    public boolean checkProductCode(String productCode) {
        return productDao.getProductByPartCode(productCode).size() > 0;
    }

    @Override
    public ComparisonProductResponse compareByProduct(String productCode, String fromPeriod, String toPeriod) {
        ContractResponse fromDateResponse = contractService.findContractsByProduct(productCode, fromPeriod);
        ContractResponse toDateResponse = contractService.findContractsByProduct(productCode, toPeriod);
        String productName = productDao.getProductByPartCode(productCode).get(0).getDescription();
        if (fromDateResponse != null && toDateResponse != null) {
            int fromPeriodQuantityContracts = fromDateResponse.getContracts().getData().size();
            int toPeriodQuantityContracts = toDateResponse.getContracts().getData().size();
            long fromDateAmountOfContracts = getAmount(fromDateResponse);
            long toDateAmountOfContracts = getAmount(toDateResponse);
            var resultPercentage = compareAmount(fromDateAmountOfContracts, toDateAmountOfContracts);
            var resultDescription = "Сумма стоимости контрактов %s на %s%%";
            if (resultPercentage > 0) {
                resultDescription = String.format(resultDescription, "увеличилась", resultPercentage);
            } else if (resultPercentage < 0) {
                resultDescription = String.format(resultDescription, "уменьшилась", resultPercentage);
            } else {
                resultDescription = "Сумма стоимости контрактов не изменилась";
            }
            return new ComparisonProductResponse(productCode, productName, fromPeriod, toPeriod, fromPeriodQuantityContracts,
                    toPeriodQuantityContracts, fromDateAmountOfContracts, toDateAmountOfContracts, resultDescription, resultPercentage);
        } else {
            return new ComparisonProductResponse("Нет данных о контрактах для продукта '" + productName + " - " + productCode + "' в этом периоде!");
        }
    }

    private long getAmount(ContractResponse response) {
        var amount = new ArrayList<Double>();
        response.getContracts().getData().forEach(contract -> {
            try {
                amount.add(Double.parseDouble(contract.getPrice()));
            } catch (Exception e) {
                logger.info("Стоимость указанна некорректно, пропускаем это значение...");
            }
        });
        return (int) amount.stream().mapToDouble(Double::doubleValue).sum();
    }

    private Double compareAmount(long fromDateAmount, long toDateAmount) {
        if (fromDateAmount == toDateAmount) return 0.0;
        var result = fromDateAmount < toDateAmount ?
                ((double) toDateAmount - fromDateAmount) / (toDateAmount / 100)
                : -((double) fromDateAmount - toDateAmount) / (fromDateAmount / 100);
        BigDecimal bd = new BigDecimal(Double.toString(result));
        return bd.setScale(2, RoundingMode.HALF_UP).doubleValue();
    }

    @Override
    public ErrorMessage checkDate(String fromDate, String toDate) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy", Locale.ENGLISH);
        String[] fromDateArray = fromDate.split("-", 2);
        String[] toDateArray = toDate.split("-", 2);
        try {
            if (fromDateArray.length != 2 || toDateArray.length != 2) {
                return ErrorMessage.INCORRECT_DATE;
            }
            if (fromDate.chars().filter(ch -> ch == '-').count() > 1 || toDate.chars().filter(ch -> ch == '-').count() > 1) {
                return ErrorMessage.INCORRECT_DATE;
            }
            Date fromDateStart = formatter.parse(fromDateArray[0]);
            Date fromDateFinish = formatter.parse(fromDateArray[1]);
            Date toDateStart = formatter.parse(toDateArray[0]);
            Date toDateFinish = formatter.parse(toDateArray[1]);
            if (toDateFinish.before(toDateStart) || toDateStart.before(fromDateFinish) || fromDateFinish.before(fromDateStart)) {
                return ErrorMessage.INCORRECT_DATE;
            }
        } catch (ParseException e) {
            return ErrorMessage.INCORRECT_DATE;
        }
        return ErrorMessage.CORRECT_DATE;
    }
}
