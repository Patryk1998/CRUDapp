package com.zwirownia.services;

import com.zwirownia.dao.RodzajTowaruDao;
import com.zwirownia.entities.RodzajTowaru;
import com.zwirownia.entities.dto.RodzajTowaruDto;
import com.zwirownia.exceptions.ExceptionContainer;
import com.zwirownia.exceptions.NotNullRelationException;
import com.zwirownia.support.Mapper;
import com.zwirownia.support.ModelConst;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class RodzajTowaruService implements IService<RodzajTowaru, RodzajTowaruDto> {

    @Autowired
    private RodzajTowaruDao rodzajTowaruDao;

    @Autowired
    private Mapper mapper;

    @Autowired
    private ModelConst modelConst;

    @Autowired
    public ExceptionContainer exContainer;

    @Override
    public List<RodzajTowaru> getAll() {
        return (List<RodzajTowaru>) rodzajTowaruDao.findAll();
    }

    @Override
    public RodzajTowaru getById(Long id) {
        return rodzajTowaruDao.findById(id).get();
    }

    public RodzajTowaru getByNazwa(String nazwa) {
        return rodzajTowaruDao.findByNazwa(nazwa);
    }

    @Override
    public void add(RodzajTowaruDto rodzajTowaruDto) {
        rodzajTowaruDao.save(mapper.rodzajTowaruDtoToRodzajTowaru(rodzajTowaruDto));
        modelConst.setRodzajeTowaru(getAll());
    }

    @Override
    public void update(RodzajTowaruDto rodzajTowaruDto, Long id) {
        RodzajTowaru rodzajTowaru = getById(id);
        if(!rodzajTowaruDto.getNazwa().equals(rodzajTowaru.getNazwa())) {
            rodzajTowaruDao.save(updateComparison(rodzajTowaru, rodzajTowaruDto));
            modelConst.setRodzajeTowaru(getAll());
            modelConst.refreshWuzetki();
        }
    }

    public void delete(Long rodzajTowaruId) {
        try {
            rodzajTowaruDao.delete(relationsCheck(getById(rodzajTowaruId)));
            modelConst.setRodzajeTowaru(getAll());
        } catch (NotNullRelationException e) {
            exContainer.setException(e);
        }
    }

    private RodzajTowaru relationsCheck(RodzajTowaru rodzajTowaru) throws NotNullRelationException {
        StringBuilder message = new StringBuilder("Nie można usunąć rodzaju (" + rodzajTowaru.getNazwa() + ").");
        String testMessage = message.toString();
        if(!rodzajTowaru.getWuzetki().isEmpty()) message.append("\nIstnieją wuzetki przypisane do rodzaju.");
        if(!rodzajTowaru.getCeny().isEmpty()) message.append("\nPodany rodzaj ma wyznaczone ceny przypisane do klientów");
        if(message.toString().equals(testMessage)) return rodzajTowaru;
        else throw new NotNullRelationException(message.toString());
    }

    private RodzajTowaru updateComparison(RodzajTowaru rodzajTowaru, RodzajTowaruDto rodzajTowaruDto) {
        String nazwaNew = rodzajTowaruDto.getNazwa();
        if(!rodzajTowaru.getNazwa().equals(nazwaNew)) rodzajTowaru.setNazwa(nazwaNew);
        return rodzajTowaru;
    }

}
