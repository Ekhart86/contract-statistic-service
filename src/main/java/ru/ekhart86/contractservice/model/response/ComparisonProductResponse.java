package ru.ekhart86.contractservice.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ComparisonProductResponse {
    private String productCode;
    private String productName;
    private String fromPeriod;
    private String toPeriod;
    private Integer fromPeriodQuantity;
    private Integer toPeriodQuantity;
    private Long fromPeriodTotalAmount;
    private Long toPeriodTotalAmount;
    private String resultDescription;
    private Double percentage;
    private String error;

    public ComparisonProductResponse(String error) {
        this.error = error;
    }

    public ComparisonProductResponse(String productCode,
                                     String productName,
                                     String fromPeriod,
                                     String toPeriod,
                                     int fromPeriodQuantity,
                                     int toPeriodQuantity,
                                     long fromPeriodTotalAmount,
                                     long toPeriodTotalAmount,
                                     String resultDescription,
                                     Double percentage) {
        this.productCode = productCode;
        this.productName = productName;
        this.fromPeriod = fromPeriod;
        this.toPeriod = toPeriod;
        this.fromPeriodQuantity = fromPeriodQuantity;
        this.toPeriodQuantity = toPeriodQuantity;
        this.fromPeriodTotalAmount = fromPeriodTotalAmount;
        this.toPeriodTotalAmount = toPeriodTotalAmount;
        this.resultDescription = resultDescription;
        this.percentage = percentage;
    }
}
