package com.zwirownia.exceptions;

import lombok.Setter;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;


@Component
@Setter
public class ExceptionContainer {

    private Exception exception;

    public boolean isNotEmpty() {
        if(exception==null) return false;
        else return true;
    }

    public Model getException(Model model) {
        Exception exception = this.exception;
        this.exception = null;
        model.addAttribute("message", exception.getMessage());
        return model;
    }
}
