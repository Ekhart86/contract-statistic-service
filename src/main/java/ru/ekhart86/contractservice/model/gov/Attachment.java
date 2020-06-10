package ru.ekhart86.contractservice.model.gov;

import lombok.Data;

@Data
public class Attachment {

    private String docDescription;

    private String fileName;

    private String registrationNumber;

    private String guid;

    private String url;
}
