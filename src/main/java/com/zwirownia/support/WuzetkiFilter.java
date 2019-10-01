package com.zwirownia.support;

import com.zwirownia.entities.Wuzetka;
import com.zwirownia.entities.dto.WuzetkiFilterDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.chrono.ChronoLocalDate;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@AllArgsConstructor
public class WuzetkiFilter {

    private WuzetkiFilterDto wuzetkiFilterDto;
    private List<Wuzetka> wuzetki;

    public void byDataFrom() {
        LocalDate from = LocalDate.parse(wuzetkiFilterDto.getFrom());
        wuzetki = wuzetki.stream()
                     .filter(wuzetka -> wuzetka.getData().isAfter(from) || wuzetka.getData().isEqual(from))
                     .collect(Collectors.toList());
    }

    public void byDataTo() {
        LocalDate to = LocalDate.parse(wuzetkiFilterDto.getTo());
        wuzetki = wuzetki.stream()
                     .filter(wuzetka -> wuzetka.getData().isBefore(to) || wuzetka.getData().isEqual(to))
                     .collect(Collectors.toList());
    }

    public void byRodzaj() {
        wuzetki = wuzetki.stream()
                     .filter(wuzetka -> wuzetkiFilterDto.getRodzajTowaru().contains(wuzetka.getRodzaj().getNazwa()))
                     .collect(Collectors.toList());
    }

    public void withoutKlienci() {
        wuzetki = wuzetki.stream()
                     .filter(wuzetka -> !wuzetkiFilterDto.getWithoutKlient().contains(wuzetka.getKlient().getNazwa()))
                     .collect(Collectors.toList());
    }

    public List<Wuzetka> getWuzetki() {
        return wuzetki;
    }
}
