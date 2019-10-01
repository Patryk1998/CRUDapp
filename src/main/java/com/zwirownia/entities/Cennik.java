package com.zwirownia.entities;

import com.sun.istack.internal.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "CENNIK")
public class Cennik {

    @Id
    @GeneratedValue
    @NotNull
    @Column(name = "ID")
    private Long id;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "RODZAJ_TOWARU_ID")
    private RodzajTowaru rodzajTowaru;

    @Column(name = "CENA")
    private Integer cena;

    @ManyToOne(cascade = {CascadeType.PERSIST})
    @JoinColumn(name = "KLIENT_ID")
    private Klient klient;

    public Cennik(RodzajTowaru rodzajTowaru, Integer cena, Klient klient) {
        this.rodzajTowaru = rodzajTowaru;
        this.cena = cena;
        this.klient = klient;
    }
}
