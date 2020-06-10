package ru.ekhart86.contractservice.model.gov;

import lombok.Data;

@Data
public class Products {

    private String ordinalNumber;

    private String quantity;

    private OKEI OKEI;

    private String name;

    private OKPD2 OKPD2;
}
