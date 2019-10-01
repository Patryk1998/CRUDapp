package com.zwirownia.dao;

import com.zwirownia.entities.Samochod;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Transactional
@Repository
public interface SamochodDao extends CrudRepository<Samochod, Long> {
    Samochod findByRejestracja(String rejestracja);
}
