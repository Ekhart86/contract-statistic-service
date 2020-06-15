package ru.ekhart86.contractservice.fasades;

import ru.ekhart86.contractservice.enums.ErrorMessage;
import ru.ekhart86.contractservice.model.response.ComparisonProductResponse;

public interface CompareFacade {
    ComparisonProductResponse compareByProduct(String productCode, String fromPeriod, String toPeriod);
    boolean checkProductCode(String productCode);
    ErrorMessage checkDate(String fromDate, String toDate);
}
