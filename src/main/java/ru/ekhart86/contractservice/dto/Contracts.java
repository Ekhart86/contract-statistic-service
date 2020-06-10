package ru.ekhart86.contractservice.dto;

import lombok.Data;

import java.util.ArrayList;

@Data
public class Contracts {

    private float total;
    private ArrayList<Contract> data;
    private float page;
    private float perpage;
}
