package com.example.restservice.Controllers;

import com.example.restservice.Constants.Constants;
import com.example.restservice.Representation_Classes.ResponseJson;
import com.example.restservice.database.Database;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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

    @RequestMapping(value= Constants.PathConstants.PROFILEPATH+"/updateGrade",method = RequestMethod.PUT,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseJson updateGrade(@RequestParam(value = "apiKey", defaultValue = "") String apiKey,
                                    @RequestParam(value = "tokenID", defaultValue = "") String tokenID,
                                    @RequestParam(value = "grade", defaultValue = "") String grade){

        Object value = checker("updateGrade",apiKey,tokenID,new String[] {grade});

        if(!(grade.equals("fr")||grade.equals("so")||grade.equals("jr")||grade.equals("se")||grade.equals("gr")||grade.equals("fa")))
            return new ResponseJson("update grade",false,"Invalid grade entry");

        String email;

        if(value instanceof String){
            email = (String) value;
        }else{
            return (ResponseJson) value;
        }

        database.updateGrade(email,grade);

        return new ResponseJson("update grade",true,"");
    }
    @RequestMapping(value = Constants.PathConstants.PROFILEPATH+"/updateAdmin",method = RequestMethod.PUT,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseJson updateAdmin(@RequestParam(value = "apiKey", defaultValue = "") String apiKey,
                                    @RequestParam(value = "tokenID", defaultValue = "") String tokenID,
                                    @RequestParam(value = "user", defaultValue = "") String user,
                                    @RequestParam(value = "adminStatus", defaultValue = "") String adminStatus){
        Object value = checker("updateToAdmin",apiKey,tokenID,new String[] {user,adminStatus});

        String email;

        if(value instanceof String){
            email = (String) value;
        }else{
            return (ResponseJson) value;
        }
        boolean as;
        if(!(adminStatus.equals("true")||adminStatus.equals("false"))) {
            return new ResponseJson("update admin", false, "invalid status change");
        }
        else{
            as = Boolean.parseBoolean(adminStatus);
        }

        if(!database.isAdmin(email))
            return new ResponseJson("update admin", false, "invalid permissions");

        database.changeAdminStatus(email,as);

        return new ResponseJson("update admin",true,"");
    }
    @RequestMapping(value= Constants.PathConstants.PROFILEPATH+"/updateDriver",method = RequestMethod.PUT,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseJson updateDriver(@RequestParam(value = "apiKey", defaultValue = "") String apiKey,
                                     @RequestParam(value = "tokenID", defaultValue = "") String tokenID,
                                     @RequestParam(value = "user", defaultValue = "") String user,
                                     @RequestParam(value = "driverStatus", defaultValue = "") String driverStatus){
        Object value = checker("updateDriver",apiKey,tokenID,new String[] {user,driverStatus});

        String email;

        if(value instanceof String){
            email = (String) value;
        }else{
            return (ResponseJson) value;
        }
        boolean as;
        if(!(driverStatus.equals("true")||driverStatus.equals("false"))) {
            return new ResponseJson("update driver", false, "invalid status change");
        }
        else{
            as = Boolean.parseBoolean(driverStatus);
        }

        if(!database.isAdmin(email))
            return new ResponseJson("update driver", false, "invalid permissions");


        database.changeDriverStatus(email,as);

        return  new ResponseJson("update driver",true,"");
    }

    @RequestMapping(value= Constants.PathConstants.PROFILEPATH+"/updateSeats",method = RequestMethod.PUT,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseJson updateMaxSeats(@RequestParam(value = "apiKey", defaultValue = "") String apiKey,
                                       @RequestParam(value = "tokenID", defaultValue = "") String tokenID,
                                       @RequestParam(value = "totalSeats", defaultValue = "") String seats){
        Object value = checker("updateMaxSeats",apiKey,tokenID,new String[] {seats});
        String email;
        int openSeats;
        if(value instanceof String){
            email = (String) value;
        }else{
            return (ResponseJson) value;
        }

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

    @RequestMapping(value= Constants.PathConstants.PROFILEPATH+"/getAccountInfo",method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseJson getAccountInfo(@RequestParam(value = "email", defaultValue = "") String email){


        return new ResponseJson("getAccountInfo",true,database.getUserInfo(email));
    }


}
