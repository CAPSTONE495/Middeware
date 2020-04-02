package com.example.restservice.Controllers;


import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import com.example.restservice.Constants.Constants.PathConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.context.request.RequestAttributes;
//import org.springframework.web.context.request.ServletRequestAttributes;

import com.example.restservice.Representation_Classes.ErrorJson;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

@RestController
public class CustomErrorController implements ErrorController {

    @Autowired
    private ErrorAttributes errorAttributes;

    @RequestMapping(value = PathConstants.ERROR)
    ErrorJson error(HttpServletRequest request, HttpServletResponse response){
        return new ErrorJson(response.getStatus(),getErrorAttributes(request,false));
    }

    @Override
    public String getErrorPath() {
        return PathConstants.ERROR;
    }

    private Map<String,Object> getErrorAttributes(HttpServletRequest request, boolean includeStackTrace){
        //RequestAttributes requestAttributes = new ServletRequestAttributes(request);
        WebRequest webRequest = new ServletWebRequest(request);
        return errorAttributes.getErrorAttributes(webRequest,includeStackTrace);
    }
}
