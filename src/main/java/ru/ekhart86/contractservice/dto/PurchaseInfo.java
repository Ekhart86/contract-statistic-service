package ru.ekhart86.contractservice.dto;

import lombok.Data;

@Data
public class PurchaseInfo {

    private String purchaseMethodCode;

    private String purchaseNoticeNumber;

    private String name;

    private String purchaseCodeName;
}
