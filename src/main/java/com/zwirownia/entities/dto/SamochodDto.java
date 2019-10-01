package com.zwirownia.entities.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class SamochodDto {
    private String rejestracja;
    private Integer tara;
    private String info;
    private String klientName;
}
