package com.zwirownia.controllers;

import com.zwirownia.exceptions.ExceptionContainer;
import com.zwirownia.support.ModelConst;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;


@EnableWebMvc
@Controller
@RequestMapping(value = "/settings")
public class UstawieniaController {

    @Autowired
    private ModelConst modelConst;

    @Autowired
    private ExceptionContainer exContainer;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String getAll(Model model) {
        if(exContainer.isNotEmpty()) model = exContainer.getException(model);
        model.addAttribute("klienci", modelConst.getKlienci());
        model.addAttribute("template", "ustawienia");
        model.addAttribute("rodzajeTowaru", modelConst.getRodzajeTowaru());
        return "main";
    }
}
