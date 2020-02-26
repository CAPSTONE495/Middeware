package com.example.restservice.Controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.ServletWebRequest;



@Controller
public class CustomErrorController implements ErrorController {
    private static final String PATH = "/error";
    private static boolean TRACE = false;

    @Autowired
    private ErrorAttributes errorAttributes;

    @RequestMapping(value = PATH)
    public String error(){
        return "error handling";
    }


    @Override
    public String getErrorPath() {
        return PATH;
    }
}
