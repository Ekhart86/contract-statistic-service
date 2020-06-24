package ru.ekhart86.contractservice.entity;

import lombok.Data;

import javax.persistence.*;
import java.sql.Date;

@Data
@Entity
@Table(name = "CONTRACTS")
public class Contract {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id", nullable = false)
    private Long id;

    @Column(name = "PRODUCT_CODE", length = 12, nullable = false)
    private String productCode;

    @Column(name = "REGION_CODE", nullable = false)
    private String regionCode;

    @Column(name = "SIGN_DATE", nullable = false)
    private Date signDate;

    @Column(name = "AMOUNT", nullable = false)
    private Long amount;

    @Column(name = "CURRENCY_CODE", nullable = false)
    private String currencyCode;
}

