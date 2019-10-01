package com.zwirownia.entities.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class WuzetkaDto {
    private String numer;
    private Integer ilosc;
    private String rodzaj;
    private String data;
    private String klientName;
}
