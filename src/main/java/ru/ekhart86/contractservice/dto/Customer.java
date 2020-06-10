package ru.ekhart86.contractservice.dto;

import lombok.Data;

@Data
public class Customer {

    private String OGRN;

    private String postalAddress;

    private String regNum;

    private String inn;

    private String fullName;

    private String kpp;

    private String shortName;

    private String OKATO;

    private String iko;

    private String legalAddress;
}
