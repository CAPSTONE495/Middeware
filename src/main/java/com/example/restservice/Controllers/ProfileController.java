package com.example.restservice.Controllers;

import com.example.restservice.Constants.Constants;
import com.example.restservice.Representation_Classes.ResponseJson;
import com.example.restservice.database.Database;
import com.example.restservice.database.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

import static com.example.restservice.Controllers.AuthController.checker;

@RestController
public class ProfileController {
    @Autowired
    Database database;
    @RequestMapping(value= Constants.PathConstants.PROFILEPATH+"/addAccount",method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseJson addAccount(@RequestParam(value = "email", defaultValue = "") String email,
                                   @RequestParam(value = "firstName", defaultValue = "") String fName,
                                   @RequestParam(value = "lastName", defaultValue = "") String lName){

        database.addUser(fName,lName,email);

        return new ResponseJson("add name",true,"");
    }

    @RequestMapping(value= Constants.PathConstants.PROFILEPATH+"/getAccountInfo",method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)
    public Users getAccountInfo(@RequestParam(value = "email", defaultValue = "") String email){
        Users u = database.getUserInfo(email);
        if(u==null)
            throw new RuntimeException("unable to find user");
        return u;
    }

    @RequestMapping(value= Constants.PathConstants.PROFILEPATH+"/updateGrade",method = RequestMethod.PUT,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseJson updateGrade(@RequestParam(value = "email", defaultValue = "") String email,
                                    @RequestParam(value = "grade", defaultValue = "") String grade){



        if(!(grade.equals("fr")||grade.equals("so")||grade.equals("jr")||grade.equals("se")||grade.equals("gr")||grade.equals("fa")))
            return new ResponseJson("update grade",false,"Invalid grade entry");

        database.updateGrade(email,grade);

        return new ResponseJson("update grade",true,"");
    }
    @RequestMapping(value = Constants.PathConstants.PROFILEPATH+"/updateAdmin",method = RequestMethod.PUT,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseJson updateAdmin(@RequestParam(value = "email", defaultValue = "") String email,
                                    @RequestParam(value = "adminStatus", defaultValue = "") String adminStatus){

        boolean as;
        if(!(adminStatus.equals("true")||adminStatus.equals("false"))) {
            return new ResponseJson("update admin", false, "invalid status change");
        }
        else{
            as = Boolean.parseBoolean(adminStatus);
        }

        database.changeAdminStatus(email,as);

        return new ResponseJson("update admin",true,"");
    }
    @RequestMapping(value= Constants.PathConstants.PROFILEPATH+"/updateDriver",method = RequestMethod.PUT,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseJson updateDriver(@RequestParam(value = "email", defaultValue = "") String email,
                                     @RequestParam(value = "driverStatus", defaultValue = "") String driverStatus){

        boolean as;
        if(!(driverStatus.equals("true")||driverStatus.equals("false"))) {
            return new ResponseJson("update driver", false, "invalid status change");
        }
        else{
            as = Boolean.parseBoolean(driverStatus);
        }

        database.changeDriverStatus(email,as);

        return  new ResponseJson("update driver",true,"");
    }

    @RequestMapping(value= Constants.PathConstants.PROFILEPATH+"/updateSeats",method = RequestMethod.PUT,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseJson updateMaxSeats(@RequestParam(value = "email", defaultValue = "") String email,
                                       @RequestParam(value = "totalSeats", defaultValue = "") String seats){

        int openSeats;

        try{
            openSeats = Integer.parseInt(seats);
        }catch(Exception e){
            return new ResponseJson("updateSeats",false,"Failed because seat value is not numeric");
        }

        if(openSeats<=0 || openSeats >= Constants.MAXSEATS){
            return new ResponseJson("updateSeats",false,"Failed because seat value is not a valid number");
        }

        database.updateSeats(email,openSeats);

        return new ResponseJson("updateSeats",true,"");
    }




}
