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
@Table(name = "SAMOCHODY")
public class Samochod {

    @Id
    @GeneratedValue
    @NotNull
    @Column(name = "ID")
    private Long id;

    @Column(name = "REJESTRACJA")
    private String rejestracja;

    @Column(name = "TARA")
    private Integer tara;

    @Column(name = "INFO")
    private String info;

    @ManyToMany(cascade = { CascadeType.ALL }, fetch = FetchType.EAGER)
    @JoinTable(
            name = "SAMOCHODY_KLIENCI",
            joinColumns = { @JoinColumn(name = "SAMOCHOD_ID") },
            inverseJoinColumns = { @JoinColumn(name = "KLIENT_ID") }
    )
    private Set<Klient> klienci = new HashSet<>();

    public void addKlient(Klient klient) {
        klienci.add(klient);
    }

    public Samochod(String rejestracja, Integer tara, String info) {
        this.rejestracja = rejestracja;
        this.tara = tara;
        this.info = info;
    }
}
