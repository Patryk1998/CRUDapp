package com.zwirownia.entities.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class WuzetkiFilterDto {
    private List<String> rodzajTowaru;
    private List<String> withoutKlient;
    private String from;
    private String to;
    private String klientName;

    public WuzetkiFilterDto(List<String> rodzajTowaru, List<String> withoutKlient, String from, String to, String klientName) {
        if(rodzajTowaru!=null) this.rodzajTowaru = rodzajTowaru;
            else this.rodzajTowaru = new ArrayList<>();
        if(withoutKlient!=null) this.withoutKlient = withoutKlient;
            else this.withoutKlient = new ArrayList<>();
        this.from = from;
        this.to = to;
        this.klientName = klientName;
    }
}
