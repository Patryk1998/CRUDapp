package com.zwirownia.controllers;


import com.zwirownia.entities.dto.CennikDto;
import com.zwirownia.entities.dto.RodzajTowaruDto;
import com.zwirownia.exceptions.ExceptionContainer;
import com.zwirownia.services.RodzajTowaruService;
import com.zwirownia.support.ModelConst;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.servlet.http.HttpServletRequest;

@EnableWebMvc
@CrossOrigin("*")
@Controller
@RequestMapping(value = "/rodzaje")
public class RodzajeTowaruController {

    @Autowired
    private RodzajTowaruService rodzajeTowaruService;

    @Autowired
    private ModelConst modelConst;

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String change(@ModelAttribute("rodzajTowaru") RodzajTowaruDto rodzajTowaruDto, Model model) {
        rodzajeTowaruService.add(rodzajTowaruDto);
        model.addAttribute("rodzajeTowaru", modelConst.getRodzajeTowaru());
        return "rodzajeTowaruParts :: rodzajeList";
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public Object delete(@PathVariable String id, Model model) {
        rodzajeTowaruService.delete(Long.parseLong(id));
        model.addAttribute("rodzajeTowaru", modelConst.getRodzajeTowaru());
        if(rodzajeTowaruService.exContainer.isNotEmpty())  return new ResponseEntity(HttpStatus.BAD_REQUEST);
        else return "rodzajeTowaruParts :: rodzajeList";
    }

    @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
    public String update(final HttpServletRequest req, @PathVariable String id, Model model) {
        rodzajeTowaruService.update(new RodzajTowaruDto(req.getParameter("nazwa")), Long.parseLong(id));
        model.addAttribute("rodzajeTowaru", modelConst.getRodzajeTowaru());
        return "rodzajeTowaruParts :: rodzajeList";
    }
}
