package com.zwirownia.services;

import com.zwirownia.entities.Klient;
import com.zwirownia.entities.RodzajTowaru;
import com.zwirownia.entities.Wuzetka;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class WuzetkiServiceTest {

    @Autowired
    private WuzetkiService wuzetkiService;

//    @Test()
//    void verifyNumberOfWuzetka() {
//        //Given
//        Integer month = LocalDate.now().getMonth().getValue();
//        Integer year = LocalDate.now().getYear();
//        String number1 = "432/" + month + "/" + year;
//        String number2 = "432";
//        String number3 = "aaa";
//        String number4 = "aaaa/";
//        String number5 = "42//21";
//        String number6 = "544/3/2014";
//
//
//        //When
//        String finalNumber1 = wuzetkiService.verifyNumberOfWuzetka(number1);
//        String finalNumber2 = wuzetkiService.verifyNumberOfWuzetka(number2);
//
//        //Then
//        assertEquals(number1, finalNumber1);
//        assertEquals(number2+'/'+month+'/'+year, finalNumber2);
//        assertThrows(NumberFormatException.class, () -> wuzetkiService.verifyNumberOfWuzetka(number3));
//        assertThrows(NumberFormatException.class, () -> wuzetkiService.verifyNumberOfWuzetka(number4));
//        assertThrows(NumberFormatException.class, () -> wuzetkiService.verifyNumberOfWuzetka(number5));
//        assertThrows(NumberFormatException.class, () -> wuzetkiService.verifyNumberOfWuzetka(number6));
//    }

    @Test
    void getTotalValues() {
        //Given
        RodzajTowaru kamien = new RodzajTowaru("KAMIEN");
        RodzajTowaru piasek = new RodzajTowaru("PIASEK");
        Wuzetka wuzetka1 = new Wuzetka("111/9/2019", 1, piasek, LocalDate.now(), null);
        Wuzetka wuzetka2 = new Wuzetka("111/9/2019", 2, kamien, LocalDate.now(), null);
        Wuzetka wuzetka3 = new Wuzetka("111/9/2019", 3, piasek, LocalDate.now(), null);
        Wuzetka wuzetka4 = new Wuzetka("111/9/2019", 4, kamien, LocalDate.now(), null);
        Wuzetka wuzetka5 = new Wuzetka("111/9/2019", 5, piasek, LocalDate.now(), null);

        List<Wuzetka> wuzetki = new ArrayList<>();
        wuzetki.add(wuzetka1);
        wuzetki.add(wuzetka2);
        wuzetki.add(wuzetka3);
        wuzetki.add(wuzetka4);
        wuzetki.add(wuzetka5);

        Map<RodzajTowaru, Long> predictableMap = new HashMap<>();
        predictableMap.put(piasek, 9L);
        predictableMap.put(kamien, 6L);

        //When
        Map<RodzajTowaru, Long> resultMap = wuzetkiService.getTotalValues(wuzetki);

        //Then
        assertTrue(resultMap.equals(predictableMap));
    }

    @Test
    void wuzetkiComparatorTest() {
        List<Wuzetka> wuzetki = wuzetkiService.getAll().stream().filter(e -> e.getKlient().getNazwa().equals("MOTA")).collect(Collectors.toList());
        Collections.sort(wuzetki);
        String asd;

    }
}
