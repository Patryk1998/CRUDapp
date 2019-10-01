package com.zwirownia.entities;

import com.sun.istack.internal.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "KLIENCI")
public class Klient {

    @Id
    @GeneratedValue
    @NotNull
    @Column(name = "ID")
    private Long id;

    @Column(name = "NAZWA")
    private String nazwa;

    @ManyToMany(mappedBy = "klienci")
    private Set<Samochod> samochody = new HashSet<>();

    @OneToMany(
            targetEntity = Wuzetka.class,
            mappedBy = "klient",
            fetch = FetchType.EAGER
    )
    private Set<Wuzetka> wuzetki = new HashSet<>();

    @OneToMany(
            targetEntity = Cennik.class,
            mappedBy = "klient",
            fetch = FetchType.EAGER
    )
    private Set<Cennik> ceny = new HashSet<>();

    public void addSamochod(Samochod samochod) {
        samochody.add(samochod);
    }

    public void addWuzetka(Wuzetka wuzetka) {
        wuzetki.add(wuzetka);
    }

    public Klient(String nazwa) {
        this.nazwa = nazwa;
    }
}