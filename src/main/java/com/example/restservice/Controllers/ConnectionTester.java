package com.example.restservice.Controllers;


import com.example.restservice.Representation_Classes.ResponseJson;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;

public class ConnectionTester {
    @RequestMapping(value= "/",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseJson testConnection(){
        return new ResponseJson("test connection",true,"correct link");
    }
}
