package com.zwirownia.dao;

import com.zwirownia.entities.Cennik;
import com.zwirownia.entities.Klient;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Repository
public interface CennikDao extends CrudRepository<Cennik, Long> {
    List<Cennik> findByKlient(Klient klient);
}
