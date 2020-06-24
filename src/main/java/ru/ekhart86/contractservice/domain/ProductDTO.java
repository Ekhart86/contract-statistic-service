package ru.ekhart86.contractservice.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ProductDTO {
    private String code;
    private String description;
    private String economicCode;
}
