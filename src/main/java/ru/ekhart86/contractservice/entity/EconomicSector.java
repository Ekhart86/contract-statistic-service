package ru.ekhart86.contractservice.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "ECONOMIC_SECTORS")
public class EconomicSector {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id", nullable = false)
    private Long id;

    @Column(name = "CODE", length = 1, nullable = false)
    private String code;

    @Column(name = "DESCRIPTION", nullable = false)
    private String description;
}
