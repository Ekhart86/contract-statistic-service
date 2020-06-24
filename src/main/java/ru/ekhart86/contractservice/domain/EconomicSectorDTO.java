package ru.ekhart86.contractservice.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class EconomicSectorDTO {
    private String code;
    private String description;
}
