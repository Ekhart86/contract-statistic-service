package ru.ekhart86.contractservice.domain;

import lombok.Data;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.sql.Date;

@Data
@Validated
public class CompareAllSectorRequestDTO {
    @NotNull(message = "startFromPeriod is required")
    private Date startFromPeriod;
    @NotNull(message = "endFromPeriod is required")
    private Date endFromPeriod;
    @NotNull(message = "startToPeriod is required")
    private Date startToPeriod;
    @NotNull(message = "endToPeriod is required")
    private Date endToPeriod;
    @NotBlank(message = "currencyCode is required")
    private String currencyCode;
}
