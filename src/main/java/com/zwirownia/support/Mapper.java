package com.zwirownia.support;

import com.zwirownia.entities.*;
import com.zwirownia.entities.dto.*;
import com.zwirownia.exceptions.WuzetkaNumberException;
import com.zwirownia.services.KlientService;
import com.zwirownia.services.RodzajTowaruService;
import com.zwirownia.services.WuzetkiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class Mapper {

    @Autowired
    private KlientService klientService;

    @Autowired
    private WuzetkiService wuzetkiService;

    @Autowired
    private RodzajTowaruService rodzajTowaruService;

    public Samochod samochodDtoToSamochod(SamochodDto samochodDto) {
        Klient klient = klientService.getByNazwa(samochodDto.getKlientName());
        Samochod samochod = new Samochod(samochodDto.getRejestracja(), samochodDto.getTara(),
                samochodDto.getInfo());
        klient.addSamochod(samochod);
        samochod.addKlient(klient);
        return samochod;
    }

    public Klient klientDtoToKlient(KlientDto klientDto) {
        return new Klient(klientDto.getNazwa());
    }

    public Wuzetka wuzetkaDtoToWuzetka(WuzetkaDto wuzetkaDto) throws WuzetkaNumberException {
        Klient klient = klientService.getByNazwa(wuzetkaDto.getKlientName());
        RodzajTowaru rodzajTowaru = rodzajTowaruService.getByNazwa(wuzetkaDto.getRodzaj());
        Wuzetka wuzetka = new Wuzetka(wuzetkiService.verifyNumberOfWuzetka(wuzetkaDto.getNumer()),
                wuzetkaDto.getIlosc(), rodzajTowaru, LocalDate.parse(wuzetkaDto.getData()), klient);
        return wuzetka;
    }

    public Cennik cennikDtoToCennik(CennikDto cennikDto) {
        RodzajTowaru rodzajTowaru = rodzajTowaruService.getByNazwa(cennikDto.getRodzajTowaruNazwa());
        Klient klient = klientService.getById(cennikDto.getKlientId());
        return new Cennik(rodzajTowaru, cennikDto.getCena(), klient);
    }

    public RodzajTowaru rodzajTowaruDtoToRodzajTowaru(RodzajTowaruDto rodzajTowaruDto) {
        return new RodzajTowaru(rodzajTowaruDto.getNazwa());
    }
}
