package com.zwirownia.dao;

import com.zwirownia.entities.Wuzetka;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Transactional
@Repository
public interface WuzetkaDao extends CrudRepository<Wuzetka, Long> {
}
