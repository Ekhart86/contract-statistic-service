package ru.ekhart86.contractservice.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "PRODUCTS")
public class Product {

    @Id
    @GeneratedValue
    @Column(name = "Id", nullable = false)
    private Long id;

    @Column(name = "code", length = 12, nullable = false)
    private String code;

    @Column(name = "description")
    private String description;

    @Column(name = "economic_code")
    private String economicCode;

}
