package ru.ekhart86.contractservice.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Date;

@AllArgsConstructor
@Data
public class CompareSectorResponseDTO {
    private String economicSectorDescription;
    private String economicCode;
    private Date startFromPeriod;
    private Date endFromPeriod;
    private Date startToPeriod;
    private Date endToPeriod;
    private String currencyCode;
    private Integer fromPeriodQuantityContracts;
    private Integer toPeriodQuantityContracts;
    private Long fromAmount;
    private Long toAmount;
    private Double contractPercentage;
    private Double amountPercentage;
}
