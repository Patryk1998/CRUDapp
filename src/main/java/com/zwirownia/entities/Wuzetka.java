package com.zwirownia.entities;


import com.sun.istack.internal.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "WUZETKI")
public class Wuzetka implements Comparable<Wuzetka> {

    @Id
    @GeneratedValue
    @NotNull
    @Column(name = "ID")
    private Long id;

    @Column(name = "NUMER")
    private String numer;

    @Column(name = "ILOSC")
    private Integer ilosc;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "RODZAJ_TOWARU")
    private RodzajTowaru rodzaj;

    @Column(name = "DATA")
    private LocalDate data;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "KLIENT_ID")
    private Klient klient;

    public Wuzetka(String numer, Integer ilosc, RodzajTowaru rodzaj, LocalDate data, Klient klient) {
        this.numer = numer;
        this.ilosc = ilosc;
        this.rodzaj = rodzaj;
        this.data = data;
        this.klient = klient;
    }

    @Override
    public int compareTo(Wuzetka otherWuzetka) {
        Integer thisNumber = Integer.parseInt(this.getNumer().substring(0, this.getNumer().indexOf('/')));
        Integer otherNumber = Integer.parseInt(otherWuzetka.getNumer().substring(0, otherWuzetka.getNumer().indexOf('/')));
        return otherNumber - thisNumber;
    }
}
