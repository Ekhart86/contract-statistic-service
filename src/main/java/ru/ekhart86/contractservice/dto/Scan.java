package ru.ekhart86.contractservice.dto;

import lombok.Data;

@Data
public class Scan {

    private String docDescription;

    private String fileName;

    private String url;
}
