package ru.ekhart86.contractservice.fasades;

import org.springframework.stereotype.Service;
import ru.ekhart86.contractservice.dao.ProductDao;
import ru.ekhart86.contractservice.enums.ErrorMessage;
import ru.ekhart86.contractservice.model.gov.Contract;
import ru.ekhart86.contractservice.model.gov.MainResponse;
import ru.ekhart86.contractservice.model.response.ComparisonResponse;
import ru.ekhart86.contractservice.services.ContractService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

@Service
public class CompareFacadeImpl implements CompareFacade {

    private ContractService contractService;
    private ProductDao productDao;

    public CompareFacadeImpl(ContractService contractService, ProductDao productDao) {
        this.contractService = contractService;
        this.productDao = productDao;
    }

    @Override
    public boolean checkProductCode(String productCode) {
        System.out.println("Продукт в базе данных = " + productDao.getProductByPartCode(productCode).size());
        return productDao.getProductByPartCode(productCode).size() > 0;
    }

    @Override
    public ComparisonResponse compareByProduct(String productCode, String fromPeriod, String toPeriod) {
        MainResponse fromDateResponse = contractService.findContractsByOkpd(productCode, fromPeriod);
        MainResponse toDateResponse = contractService.findContractsByOkpd(productCode, toPeriod);
        if (fromDateResponse != null && toDateResponse != null) {
            int fromDateNumberOfContracts = fromDateResponse.getContracts().getData().size();
            int toDateNumberOfContracts = toDateResponse.getContracts().getData().size();
            long fromDateAmountOfContracts = getAmount(fromDateResponse);
            long toDateAmountOfContracts = getAmount(toDateResponse);
            var textResult = compareAmount(fromDateAmountOfContracts, toDateAmountOfContracts);
            return new ComparisonResponse(fromPeriod,
                    toPeriod, fromDateNumberOfContracts, toDateNumberOfContracts,
                    fromDateAmountOfContracts, toDateAmountOfContracts, textResult);
        } else {
            return null;
        }
    }

    private long getAmount(MainResponse response) {
        double amount = 0.0;
        for (Contract contract : response.getContracts().getData()) {
            try {
                double temp = Double.parseDouble(contract.getPrice());
                amount += temp;
            } catch (Exception e) {
                System.out.println("Стоимость указанно некорректно, пропускаем это значение...");
            }
        }
        return (int) amount;
    }

    private String compareAmount(long fromDateAmount, long toDateAmount) {
        System.out.println("Сумма раньше : " + fromDateAmount);
        System.out.println("Сумма сейчас : " + toDateAmount);
        var builder = new StringBuilder();
        if (fromDateAmount < toDateAmount) {
            return builder.append("Увеличение общей суммы контрактов на ")
                    .append((toDateAmount - fromDateAmount) / (toDateAmount / 100))
                    .append("%, по сравнению с прошлым периодом").toString();
        } else if (fromDateAmount > toDateAmount) {
            return builder.append("Уменьшение общей суммы контрактов на ")
                    .append((fromDateAmount - toDateAmount) / (fromDateAmount / 100))
                    .append("%, по сравнению с прошлым периодом").toString();
        } else {
            return builder.append("Суммы контрактов за выбранные периоды одинаковы").toString();
        }
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
