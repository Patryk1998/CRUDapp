package com.zwirownia.dao;

import com.zwirownia.entities.RodzajTowaru;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Transactional
@Repository
public interface RodzajTowaruDao extends CrudRepository<RodzajTowaru, Long> {
    RodzajTowaru findByNazwa(String nazwa);
}
