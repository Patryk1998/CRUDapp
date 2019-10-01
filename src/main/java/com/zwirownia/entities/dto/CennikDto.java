package com.zwirownia.entities.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class CennikDto {
    private String rodzajTowaruNazwa;
    private Integer cena;
    private Long klientId;
}
