package com.zwirownia.support;

import com.zwirownia.entities.*;
import com.zwirownia.services.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Collections;
import java.util.List;

@Getter
@Setter
@Component
public class ModelConst {

    @Autowired
    private RodzajTowaruService rodzajTowaruService;

    @Autowired
    private KlientService klientService;

    @Autowired
    private SamochodyService samochodyService;

    @Autowired
    private WuzetkiService wuzetkiService;

    @Autowired
    private CenyService cenyService;

    private List<RodzajTowaru> rodzajeTowaru;
    private List<String> nazwyKlientow;
    private List<Klient> klienci;
    private List<Samochod> samochody;
    private List<Wuzetka> wuzetki;
    private List<Cennik> cenyOfKlient;

    @PostConstruct
    public void init() {
        rodzajeTowaru = rodzajTowaruService.getAll();
        nazwyKlientow = klientService.getNazwy();
        klienci = klientService.getAll();
        samochody = samochodyService.getAll();
        wuzetki = wuzetkiService.getAll();
    }


    public void removeCennik(Long cennikId) {
        cenyOfKlient.remove(cenyOfKlient.stream().filter(cena -> cena.getId().longValue() == cennikId.longValue()).findFirst().get());
    }

    public void addCennik(Cennik cennik) {
        cenyOfKlient.add(cennik);
    }

    public void refreshWuzetki() {
        wuzetki = wuzetkiService.getAll();
    }
}
