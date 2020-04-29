package com.example.restservice.Controllers;


import com.example.restservice.Representation_Classes.ResponseJson;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ConnectionTester {
    @GetMapping("/")
    public ResponseJson testConnection(){
        return new ResponseJson("test connection",true,"correct link");
    }
}
