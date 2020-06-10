package ru.ekhart86.contractservice.model.gov;

import lombok.Data;

@Data
public class Contract {

    private PurchaseInfo purchaseInfo;

    private Attachments attachments;

    private Scan[] scan;

    private String publishDate;

    private String signDate;

    private String mongo_id;

    private Products[] products;

    private String number;

    private String regionCode;

    private String searchRank;

    private String fz;

    private String regNum;

    private String price;

    private Currency currency;

    private String id;

    private String[] misuses;

    private String fileVersion;

    private Execution execution;

    private String schemaVersion;

    private String versionNumber;

    private String createDateTime;

    private String loadId;

    private Placer placer;

    private String protocolDate;

    private String name;

    private String status;

    private Customer customer;
}
