package ru.kata.spring.boot_security.demo.Controller;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.persistence.EntityNotFoundException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    private ModelAndView handleException(Exception ex) {
        ModelAndView modelAndView = new ModelAndView("/error");
        modelAndView.addObject("errorMessage", ex.getMessage());
        return modelAndView;
    }
}
