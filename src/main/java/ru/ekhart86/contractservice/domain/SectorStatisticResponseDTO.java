package ru.ekhart86.contractservice.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Date;

@AllArgsConstructor
@Data
public class SectorStatisticResponseDTO {
    private String economicCode;
    private String economicSectorDescription;
    private Date startDate;
    private Date endDate;
    private Integer quantityContracts;
    private Long totalAmount;
    private String currencyCode;
}
