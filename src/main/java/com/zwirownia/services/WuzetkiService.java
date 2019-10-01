package com.zwirownia.services;

import com.zwirownia.entities.RodzajTowaru;
import com.zwirownia.exceptions.ExceptionContainer;
import com.zwirownia.exceptions.WuzetkaNumberException;
import com.zwirownia.support.WuzetkiFilter;
import com.zwirownia.dao.WuzetkaDao;
import com.zwirownia.entities.Wuzetka;
import com.zwirownia.entities.dto.WuzetkiFilterDto;
import com.zwirownia.entities.dto.WuzetkaDto;
import com.zwirownia.support.Mapper;
import com.zwirownia.support.ModelConst;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class WuzetkiService implements IService<Wuzetka, WuzetkaDto> {

    public List<Wuzetka> filteredWuzetki = new ArrayList<>();

    @Autowired
    private WuzetkaDao wuzetkaDao;

    @Autowired
    private KlientService klientService;

    @Autowired
    private RodzajTowaruService rodzajTowaruService;

    @Autowired
    private Mapper mapper;

    @Autowired
    private ModelConst modelConst;

    @Autowired
    private ExceptionContainer exContainer;

    @Override
    public List<Wuzetka> getAll() {
        List<Wuzetka> all = (List<Wuzetka>) wuzetkaDao.findAll();
        Collections.sort(all);
        return all;
    }

    @Override
    public Wuzetka getById(Long id) {
        return wuzetkaDao.findById(id).get();
    }

    @Override
    public void add(WuzetkaDto wuzetkaDto) {
        try {
            wuzetkaDao.save(mapper.wuzetkaDtoToWuzetka(wuzetkaDto));
            modelConst.setWuzetki(getAll());
        } catch (WuzetkaNumberException e) {
            exContainer.setException(e);
        }
    }

    public String verifyNumberOfWuzetka(String number) throws WuzetkaNumberException {
        char[] seq = number.toCharArray();
        int month = LocalDate.now().getMonth().getValue();
        int year = LocalDate.now().getYear();
        String monthCheck = "/";
        String yearCheck = "/";
        int signPlace = number.indexOf('/');
        if(signPlace != -1) {
            if(month>=10 && seq.length>=(8+signPlace)) { //kiedy format = n/mm/yyyy
                monthCheck = String.valueOf(seq[signPlace+1] + seq[signPlace+2]);// string może się nie zgadzać np monthCheck='8/'
                yearCheck = String.valueOf(new char[] {seq[signPlace+4], seq[signPlace+5], seq[signPlace+6], seq[signPlace+7]});
            } else if(seq.length>=(7+signPlace)){
                monthCheck = String.valueOf(seq[signPlace+1]);
                yearCheck = String.valueOf(new char[] {seq[signPlace+3], seq[signPlace+4], seq[signPlace+5], seq[signPlace+6]});
            }
            try {
                if(month==Integer.parseInt(monthCheck) && year==Integer.parseInt(yearCheck)) { // próba sparsowania i sprawdzenia poprawności miesiąca oraz roku
                    return number;
                } else throw new WuzetkaNumberException("Podany misiąc ("+monthCheck
                +") oraz rok ("+yearCheck+") są nieprawidłowe"); // jeżeli udało się sparsować ale nie zgadzają się miesiąc i rok
            } catch (NumberFormatException e) {
                throw new WuzetkaNumberException("Miesiąc oraz rok mają nieprawidłowy format"); // jeżeli nie udało się sparsować bo miesiąc i rok maja nieprawidłowy format
            }
        }
        // kiedy number to sama cyfra bez znaku '/'
        if(number.matches("[0-9]+")) {
            return number+'/'+month+'/'+year;
        } else throw new WuzetkaNumberException("Numer wuzetki jest nieprawidłowy (  "+number+"  )");
    }

    @Override
    public void update(WuzetkaDto wuzetkaDto, Long id) {
        Wuzetka wuzetka = getById(id);
        wuzetkaDao.save(updateComparison(wuzetka, wuzetkaDto));
        modelConst.setWuzetki(getAll());
    }

    private Wuzetka updateComparison(Wuzetka wuzetka, WuzetkaDto wuzetkaDto) {
        String numerNew = wuzetkaDto.getNumer();
        Integer iloscNew = wuzetkaDto.getIlosc();
        String dataNew = wuzetkaDto.getData();
        String rodzajNew = wuzetkaDto.getRodzaj();
        String klientNew = wuzetkaDto.getKlientName();
        if(!wuzetka.getNumer().equals(numerNew)) wuzetka.setNumer(numerNew);
        if(wuzetka.getIlosc()!=iloscNew) wuzetka.setIlosc(iloscNew);
        if(!wuzetka.getData().toString().equals(dataNew)) wuzetka.setData(LocalDate.parse(dataNew));
        if(!wuzetka.getRodzaj().equals(rodzajNew)) wuzetka.setRodzaj(rodzajTowaruService.getByNazwa(rodzajNew));
        if(!wuzetka.getKlient().getNazwa().equals(klientNew))
            wuzetka.setKlient(klientService.getByNazwa(klientNew));
        return wuzetka;
    }

    public List<Wuzetka> getFiltered(WuzetkiFilterDto wuzetkiFilterDto) {
        List<Wuzetka> wuzetki;
        if(!wuzetkiFilterDto.getKlientName().isEmpty()) {
            wuzetki = klientService.getWuzetkiOfKlient(wuzetkiFilterDto.getKlientName());
        } else wuzetki = getAll();

        WuzetkiFilter filter = new WuzetkiFilter(wuzetkiFilterDto, wuzetki);

        if(!wuzetkiFilterDto.getFrom().isEmpty()) filter.byDataFrom();
        if(!wuzetkiFilterDto.getTo().isEmpty()) filter.byDataTo();
        if(!wuzetkiFilterDto.getRodzajTowaru().isEmpty()) filter.byRodzaj();
        if(!wuzetkiFilterDto.getWithoutKlient().isEmpty()) filter.withoutKlienci();
        wuzetki = filter.getWuzetki();
        Collections.sort(wuzetki);
        filteredWuzetki = wuzetki;
        return wuzetki;
    }

    public Map<RodzajTowaru, Long> getTotalValues(List<Wuzetka> filteredWuzetki) {
        return filteredWuzetki.stream().collect(Collectors.groupingBy(Wuzetka::getRodzaj, Collectors.summingLong(Wuzetka::getIlosc)));
    }

    public void delete(Long wuzetkaId) {
        wuzetkaDao.deleteById(wuzetkaId);
        modelConst.setWuzetki(getAll());
    }

    public void deleteFiltered() {
        if(!filteredWuzetki.isEmpty()) wuzetkaDao.deleteAll(filteredWuzetki);
        filteredWuzetki=null;
        modelConst.setWuzetki(getAll());
    }
}
