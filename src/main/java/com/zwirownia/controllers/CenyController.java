package com.zwirownia.controllers;


import com.sun.org.apache.xpath.internal.operations.Mod;
import com.zwirownia.entities.dto.CennikDto;
import com.zwirownia.services.CenyService;
import com.zwirownia.support.ModelConst;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.time.LocalDate;


@EnableWebMvc
@Controller
@RequestMapping(value = "/ceny")
public class CenyController {

    @Autowired
    private CenyService cenyService;

    @Autowired
    private ModelConst modelConst;

    @RequestMapping(value = "/change/{id}", method = RequestMethod.POST)
    public String change(@PathVariable String id, @ModelAttribute("cena") CennikDto cennikDto, Model model) {
        cennikDto.setKlientId(Long.parseLong(id));
        cenyService.changeCena(cennikDto);
        model.addAttribute("ceny", modelConst.getCenyOfKlient());
        model.addAttribute("rodzajeTowaru", modelConst.getRodzajeTowaru());
        return "cennikParts :: cenySettings";
    }

    @RequestMapping(value = "/of/{id}", method = RequestMethod.GET)
    public String getOf(@PathVariable String id, Model model) {
        model.addAttribute("ceny", cenyService.getAllOfKlient(Long.parseLong(id)));
        model.addAttribute("rodzajeTowaru", modelConst.getRodzajeTowaru());
        return "cennikParts :: cenySettings";
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public String delete(@PathVariable String id, Model model) {
        cenyService.delete(Long.parseLong(id));
        model.addAttribute("ceny", modelConst.getCenyOfKlient());
        model.addAttribute("rodzajeTowaru", modelConst.getRodzajeTowaru());
        return "cennikParts :: cenySettings";
    }


}
