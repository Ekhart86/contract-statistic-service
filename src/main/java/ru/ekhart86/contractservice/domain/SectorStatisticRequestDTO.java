package ru.ekhart86.contractservice.domain;

import lombok.Data;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.sql.Date;

@Data
@Validated
public class SectorStatisticRequestDTO {
    @NotNull(message = "startDate is required")
    private Date startDate;
    @NotNull(message = "endDate is required")
    private Date endDate;
    @NotBlank(message = "economicCode is required")
    private String economicCode;
    @NotBlank(message = "currencyCode is required")
    private String currencyCode;
}
