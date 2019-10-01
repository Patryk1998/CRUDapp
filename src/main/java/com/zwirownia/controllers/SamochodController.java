package com.zwirownia.controllers;

import com.zwirownia.entities.dto.SamochodDto;
import com.zwirownia.services.KlientService;
import com.zwirownia.services.SamochodyService;
import com.zwirownia.support.ModelConst;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableWebMvc
@Controller
@RequestMapping(value = "/tary")
public class SamochodController {

    @Autowired
    private SamochodyService samochodyService;

    @Autowired
    private ModelConst modelConst;

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public String getAll(Model model) {
        model.addAttribute("samochody", modelConst.getSamochody());
        model.addAttribute("nazwyKlientow", modelConst.getNazwyKlientow());
        model.addAttribute("template", "tary");
        return "main";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String add(@ModelAttribute("samochod") SamochodDto samochodDto) {
        samochodyService.add(samochodDto);
        return "redirect:/tary/all";
    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public String getEditForm(@PathVariable String id, Model model) {
        model.addAttribute("samochod", samochodyService.getById(Long.parseLong(id)));
        model.addAttribute("klienci", samochodyService.getKlientsOfSamochod(Long.parseLong(id)));
        model.addAttribute("klienciNotAttached", samochodyService.getKlienciNotAttachedToSamochod(Long.parseLong(id)));
        model.addAttribute("operation", "edit");
        model.addAttribute("template", "samochodForm");
        return "main";
    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.POST)
    public String edit(@ModelAttribute("samochod") SamochodDto samochodDto, @PathVariable String id) {
        samochodyService.update(samochodDto, Long.parseLong(id));
        return "redirect:/tary/all";
    }

    @RequestMapping(value = "/deleteKlient/{samochodId}/{klientId}", method = RequestMethod.GET)
    public String deleteKlientFromSamochod(Model model, @PathVariable String samochodId, @PathVariable String klientId) {
        samochodyService.deleteKlientOfSamochod(Long.parseLong(samochodId), Long.parseLong(klientId));
        model.addAttribute("samochod", samochodyService.getById(Long.parseLong(samochodId)));
        model.addAttribute("klienci", samochodyService.getKlientsOfSamochod(Long.parseLong(samochodId)));
        model.addAttribute("klienciNotAttached", samochodyService.getKlienciNotAttachedToSamochod(Long.parseLong(samochodId)));
        return "samochodParts :: klientsList";
    }

    @RequestMapping(value = "/addKlient/{samochodId}/{klientNazwa}", method = RequestMethod.GET)
    public String addKlientToSamochod(Model model, @PathVariable String samochodId, @PathVariable String klientNazwa) {
        samochodyService.addKlientOfSamochod(Long.parseLong(samochodId), klientNazwa);
        model.addAttribute("samochod", samochodyService.getById(Long.parseLong(samochodId)));
        model.addAttribute("klienci", samochodyService.getKlientsOfSamochod(Long.parseLong(samochodId)));
        model.addAttribute("klienciNotAttached", samochodyService.getKlienciNotAttachedToSamochod(Long.parseLong(samochodId)));
        return "samochodParts :: klientsList";
    }
}
