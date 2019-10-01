package com.zwirownia.services;

import com.zwirownia.dao.KlientDao;
import com.zwirownia.dao.SamochodDao;
import com.zwirownia.entities.Klient;

import com.zwirownia.entities.Samochod;
import com.zwirownia.entities.Wuzetka;
import com.zwirownia.entities.dto.SamochodDto;
import com.zwirownia.support.Mapper;
import com.zwirownia.support.ModelConst;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class SamochodyService implements IService<Samochod, SamochodDto> {

    @Autowired
    public SamochodDao samochodDao;

    @Autowired
    private KlientService klientService;

    @Autowired
    private Mapper mapper;

    @Autowired
    private ModelConst modelConst;


    public List<Samochod> getAll() {
        return (List<Samochod>) samochodDao.findAll();
    }

    public void add(SamochodDto samochod) {
        samochodDao.save(mapper.samochodDtoToSamochod(samochod));
        modelConst.setSamochody(getAll());
    }

    public Samochod getById(Long id) {
        return samochodDao.findById(id).get();
    }

    public void update(SamochodDto samochodDto, Long id) {
        Samochod samochod = getById(id);
        samochodDao.save(updateComparison(samochod, samochodDto));
        modelConst.setSamochody(getAll());
    }

    public void deleteKlientOfSamochod(Long samochodId, Long klientId) {
        Samochod samochod = getById(samochodId);
        Klient klient = klientService.getById(klientId);
        samochod.getKlienci().remove(klient);
        klient.getSamochody().remove(samochod);
        samochodDao.save(samochod);
    }

    public void addKlientOfSamochod(Long samochodId, String klientNazwa) {
        Samochod samochod = getById(samochodId);
        Klient klient = klientService.getByNazwa(klientNazwa);
        samochod.addKlient(klient);
        klient.addSamochod(samochod);
        samochodDao.save(samochod);
    }

    public Set<Klient> getKlientsOfSamochod(Long id) {
        return getById(id).getKlienci();
    }

    private Samochod updateComparison(Samochod samochod, SamochodDto samochodDto) {
        String rejestracjaNew = samochodDto.getRejestracja();
        Integer taraNew = samochodDto.getTara();
        String infoNew = samochodDto.getInfo();
        if(!samochod.getRejestracja().equals(rejestracjaNew)) samochod.setRejestracja(rejestracjaNew);
        if(samochod.getTara()!=taraNew) samochod.setTara(taraNew);
        if(!samochod.getInfo().equals(infoNew)) samochod.setInfo(infoNew);
        return samochod;
    }

    public Samochod getByRejestracja(String samochodReje) {
        return samochodDao.findByRejestracja(samochodReje);
    }

    public List<Samochod> getNotAttachedToKlient(Klient klient) {
        Set<Long> samochodyOfKlientIds = klient.getSamochody().stream()
                .map(Samochod::getId)
                .collect(Collectors.toSet());
        List<Samochod> filteredSamochody = getAll().stream()
                .filter(samochod -> !samochodyOfKlientIds.contains(samochod.getId()))
                .collect(Collectors.toList());
        return filteredSamochody;
    }

    public List<Klient> getKlienciNotAttachedToSamochod(Long samochodId) {
        return klientService.getNotAttachedToSamochod(getById(samochodId));
    }
}
