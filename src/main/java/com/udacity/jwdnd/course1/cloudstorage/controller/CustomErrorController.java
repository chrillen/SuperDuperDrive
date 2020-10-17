package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.ResultTypes;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class CustomErrorController implements ErrorController {

    private static final String PATH = "/error";

    @RequestMapping(value = PATH)
    public String error(Model model, HttpServletRequest request) {
        var statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        var exception = (Exception) request.getAttribute("javax.servlet.error.exception");
        var errorMessage = exception==null? "N/A": exception.getMessage();
        model.addAttribute("errorMessage","Http statuscode: " + statusCode + " Error occured with: " +  errorMessage);
        model.addAttribute("resultType", ResultTypes.ErrorWithMessage);
        return "result";
    }

    @Override
    public String getErrorPath() {
        return PATH;
    }

}