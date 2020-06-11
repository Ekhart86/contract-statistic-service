package ru.ekhart86.contractservice.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ComparisonResponse {
    private String productName;
    private String fromPeriod;
    private String toPeriod;
    private int fromDateQuantity;
    private int toDateQuantity;
    private long fromDateTotalAmount;
    private long toDateTotalAmount;
    private String resultDescription;
    private Double percentage;
}
