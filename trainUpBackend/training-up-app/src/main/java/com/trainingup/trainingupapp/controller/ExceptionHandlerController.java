package com.trainingup.trainingupapp.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ExceptionHandlerController implements ErrorController {

    @Override
    @RequestMapping("/error")
    public String getErrorPath() {
        return "errorPage";
    }
}