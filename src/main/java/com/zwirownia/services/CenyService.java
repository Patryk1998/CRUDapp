package com.zwirownia.services;

import com.zwirownia.dao.CennikDao;
import com.zwirownia.entities.Cennik;
import com.zwirownia.entities.Klient;
import com.zwirownia.entities.RodzajTowaru;
import com.zwirownia.entities.dto.CennikDto;
import com.zwirownia.support.Mapper;
import com.zwirownia.support.ModelConst;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class CenyService implements IService<Cennik, CennikDto> {

    @Autowired
    private CennikDao cennikDao;

    @Autowired
    private Mapper mapper;

    @Autowired
    private KlientService klientService;

    @Autowired
    private ModelConst modelConst;

    @Override
    public List<Cennik> getAll() {
        return (List<Cennik>) cennikDao.findAll();
    }

    @Override
    public Cennik getById(Long id) {
        return cennikDao.findById(id).orElse(null);
    }

    public List<Cennik> getAllOfKlient(Long klientId) {
        List<Cennik> ceny = cennikDao.findByKlient(klientService.getById(klientId));
        modelConst.setCenyOfKlient(ceny);
        return ceny;
    }

    public List<Cennik> getAllOfKlient(Klient klient) {
        return cennikDao.findByKlient(klient);
    }

    @Override
    public void add(CennikDto cennikDto) {
        modelConst.addCennik(cennikDao.save(mapper.cennikDtoToCennik(cennikDto)));
    }

    @Override
    public void update(CennikDto cennikDto, Long id) {
        Cennik cennik = getById(id);
        cennikDao.save(updateComparison(cennik, cennikDto));
    }

    private Cennik updateComparison(Cennik cennik, CennikDto cennikDto) {
        Integer cenaNew = cennikDto.getCena();
        if(cennik.getCena()!=cenaNew) cennik.setCena(cenaNew);
        return cennik;
    }

    public void changeCena(CennikDto cennikDto) {
        Optional<Cennik> cena = getAllOfKlient(cennikDto.getKlientId()).stream()
                .filter(cennik -> cennik.getRodzajTowaru().getNazwa().equals(cennikDto.getRodzajTowaruNazwa())).findFirst();
        if(!cena.isPresent()) add(cennikDto);
        else update(cennikDto, cena.get().getId());
    }

    public void delete(Long cenaId) {
        modelConst.removeCennik(cenaId);
        cennikDao.deleteById(cenaId);
    }
}
