package com.zwirownia.dao;

import com.zwirownia.entities.Klient;
import com.zwirownia.entities.Samochod;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Transactional
@Repository
public interface KlientDao extends CrudRepository<Klient, Long> {
    Klient findByNazwa(String name);
}
