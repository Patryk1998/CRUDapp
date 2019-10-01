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
@Table(name = "RODZAJE_TOWARU")
public class RodzajTowaru {

    @Id
    @GeneratedValue
    @NotNull
    @Column(name = "ID")
    private Long id;

    @Column(name = "NAZWA")
    private String nazwa;

    @OneToMany(
            targetEntity = Wuzetka.class,
            mappedBy = "rodzaj",
            fetch = FetchType.EAGER
    )
    private Set<Wuzetka> wuzetki = new HashSet<>();

    @OneToMany(
            targetEntity = Cennik.class,
            mappedBy = "rodzajTowaru",
            fetch = FetchType.EAGER
    )
    private Set<Cennik> ceny = new HashSet<>();

    public RodzajTowaru(String nazwa) {
        this.nazwa = nazwa;
    }
}
