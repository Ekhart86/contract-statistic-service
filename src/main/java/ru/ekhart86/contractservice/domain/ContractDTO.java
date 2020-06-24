package ru.ekhart86.contractservice.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Date;

@AllArgsConstructor
@Data
public class ContractDTO {
    private String productCode;
    private String regionCode;
    private Date signDate;
    private Long amount;
    private String currencyCode;
}
