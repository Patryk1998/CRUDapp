package com.zwirownia.controllers;

import com.zwirownia.entities.dto.KlientDto;
import com.zwirownia.services.CenyService;
import com.zwirownia.services.KlientService;
import com.zwirownia.services.RodzajTowaruService;
import com.zwirownia.services.SamochodyService;
import com.zwirownia.support.ModelConst;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableWebMvc
@Controller
@RequestMapping(value = "/klienci")
public class KlientController {

    @Autowired
    private KlientService klientService;

    @Autowired
    private ModelConst modelConst;

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public String getAll(Model model) {
        model.addAttribute("klienci", modelConst.getKlienci());
        model.addAttribute("template", "klienci");
        return "main";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String add(@ModelAttribute("klient") KlientDto klientDto) {
        klientService.add(klientDto);
        return "redirect:/klienci/all";
    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public String getEditForm(@PathVariable String id, Model model) {
        model.addAttribute("klient", klientService.getById(Long.parseLong(id)));
        model.addAttribute("samochody", klientService.getSamochodsOfKlient(Long.parseLong(id)));
        model.addAttribute("samochodyNotAttached", klientService.getSamochodyNotAttachedToKlient(Long.parseLong(id)));
        model.addAttribute("ceny", klientService.getCenyOfKlient(Long.parseLong(id)));
        model.addAttribute("rodzajeTowaru", modelConst.getRodzajeTowaru());
        model.addAttribute("operation", "edit");
        model.addAttribute("template", "klientForm");
        return "main";
    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.POST)
    public String edit(@ModelAttribute("klient") KlientDto klientDto, @PathVariable String id) {
        klientService.update(klientDto, Long.parseLong(id));
        return "redirect:/klienci/all";
    }

    @RequestMapping(value = "/deleteSamochod/{klientId}/{samochodId}", method = RequestMethod.GET)
    public String deleteSamochodFromKlient(Model model, @PathVariable String samochodId, @PathVariable String klientId) {
        klientService.deleteSamochodOfKlient(Long.parseLong(samochodId), Long.parseLong(klientId));
        model.addAttribute("klient", klientService.getById(Long.parseLong(klientId)));
        model.addAttribute("samochody", klientService.getSamochodsOfKlient(Long.parseLong(klientId)));
        model.addAttribute("samochodyNotAttached", klientService.getSamochodyNotAttachedToKlient(Long.parseLong(klientId)));
        return "klientParts :: samochodyList";
    }

    @RequestMapping(value = "/addSamochod/{klientId}/{samochodReje}", method = RequestMethod.GET)
    public String addSamochodToKlient(Model model, @PathVariable String samochodReje, @PathVariable String klientId) {
        klientService.addSamochodOfKlient(samochodReje, Long.parseLong(klientId));
        model.addAttribute("klient", klientService.getById(Long.parseLong(klientId)));
        model.addAttribute("samochody", klientService.getSamochodsOfKlient(Long.parseLong(klientId)));
        model.addAttribute("samochodyNotAttached", klientService.getSamochodyNotAttachedToKlient(Long.parseLong(klientId)));
        return "klientParts :: samochodyList";
    }

}
