package com.zwirownia.controllers;


import com.zwirownia.entities.Wuzetka;
import com.zwirownia.entities.dto.WuzetkiFilterDto;
import com.zwirownia.entities.dto.WuzetkaDto;
import com.zwirownia.exceptions.ExceptionContainer;
import com.zwirownia.services.WuzetkiService;
import com.zwirownia.support.ModelConst;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.List;

@EnableWebMvc
@Controller
@RequestMapping(value = "/wuzetki")
public class WuzetkiController {

    @Autowired
    private WuzetkiService wuzetkiService;

    @Autowired
    private ModelConst modelConst;

    @Autowired
    private ExceptionContainer exContainer;

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public String getAll(Model model) {
        wuzetkiService.filteredWuzetki = null;
        List<Wuzetka> wuzetki = modelConst.getWuzetki();
        if(exContainer.isNotEmpty()) model = exContainer.getException(model);
        model.addAttribute("wuzetki", wuzetki);
        model.addAttribute("ilosci", wuzetkiService.getTotalValues(wuzetki));
        model.addAttribute("nazwyKlientow", modelConst.getNazwyKlientow());
        model.addAttribute("rodzajeTowaru", modelConst.getRodzajeTowaru());
        model.addAttribute("template", "wuzetki");
        return "main";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String add(@ModelAttribute("wuzetka") WuzetkaDto wuzetkaDto) {
        wuzetkiService.add(wuzetkaDto);
        return "redirect:/wuzetki/all";
    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public String getEditForm(@PathVariable String id, Model model) {
        model.addAttribute("wuzetka", wuzetkiService.getById(Long.parseLong(id)));
        model.addAttribute("operation", "edit");
        model.addAttribute("nazwyKlientow", modelConst.getNazwyKlientow());
        model.addAttribute("rodzajeTowaru", modelConst.getRodzajeTowaru());
        model.addAttribute("template", "wuzetkaForm");
        return "main";
    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.POST)
    public String edit(@ModelAttribute("wuzetka") WuzetkaDto wuzetkaDto, @PathVariable String id) {
        wuzetkiService.update(wuzetkaDto, Long.parseLong(id));
        return "redirect:/wuzetki/all";
    }

    @RequestMapping(value = "/filters", method = RequestMethod.POST)
    public String filter(@ModelAttribute("filtry") WuzetkiFilterDto filters, Model model) {
        List<Wuzetka> filteredWuzetki = wuzetkiService.getFiltered(filters);
        model.addAttribute("filters", filters);
        model.addAttribute("wuzetki", filteredWuzetki);
        model.addAttribute("ilosci", wuzetkiService.getTotalValues(filteredWuzetki));
        model.addAttribute("nazwyKlientow", modelConst.getNazwyKlientow());
        model.addAttribute("rodzajeTowaru", modelConst.getRodzajeTowaru());
        model.addAttribute("template", "wuzetki");
        return "main";
    }

    @RequestMapping(value = "/pay/{id}", method = RequestMethod.GET)
    public String pay(@PathVariable String id, Model model) {
        wuzetkiService.delete(Long.parseLong(id));
        List<Wuzetka> wuzetki = modelConst.getWuzetki();
        model.addAttribute("wuzetki", wuzetki);
        model.addAttribute("ilosci", wuzetkiService.getTotalValues(wuzetki));
        model.addAttribute("nazwyKlientow", modelConst.getNazwyKlientow());
        model.addAttribute("rodzajeTowaru", modelConst.getRodzajeTowaru());
        model.addAttribute("template", "wuzetki");
        return "main";
    }

    @RequestMapping(value = "/pay", method = RequestMethod.POST)
    public String pay(Model model) {
        wuzetkiService.deleteFiltered();
        return "main";
    }
}

