package ru.ekhart86.contractservice.model.response;

import lombok.Data;

@Data
public class ComparisonResponse {

    private String fromPeriod;
    private String toPeriod;
    private int fromDateQuantity;
    private int toDateQuantity;
    private long fromDateTotalAmount;
    private long toDateTotalAmount;
    private String result;

    public ComparisonResponse(String fromPeriod, String toPeriod,
                              int fromDateQuantity,
                              int toDateQuantity,
                              long fromDateTotalAmount,
                              long toDateTotalAmount,
                              String result) {
        this.fromDateQuantity = fromDateQuantity;
        this.toDateQuantity = toDateQuantity;
        this.fromDateTotalAmount = fromDateTotalAmount;
        this.toDateTotalAmount = toDateTotalAmount;
        this.result = result;
        this.fromPeriod = fromPeriod;
        this.toPeriod = toPeriod;
    }
}
