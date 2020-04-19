package com.example.restservice.Controllers;

import com.example.restservice.Constants.Constants;
import com.example.restservice.Representation_Classes.ResponseJson;
import com.example.restservice.database.Database;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import static com.example.restservice.Controllers.AuthController.checker;


public class ProfileController {
    @Autowired
    Database database;
    @RequestMapping(value= Constants.PathConstants.PROFILEPATH+"/addName",method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseJson addName(@RequestParam(value = "apiKey", defaultValue = "") String apiKey,
                                @RequestParam(value = "tokenID", defaultValue = "") String tokenID,
                                @RequestParam(value = "firstName", defaultValue = "") String fName,
                                @RequestParam(value = "lastName", defaultValue = "") String lName){

        Object value = checker("updateGrade",apiKey,tokenID,new String[] {fName,lName});

        String email;
        if(value instanceof String){
            email = (String) value;
        }else{
            return (ResponseJson) value;
        }



        return new ResponseJson("add name",true,"");
    }

    @RequestMapping(value= Constants.PathConstants.PROFILEPATH+"/updateGrade",method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseJson updateGrade(@RequestParam(value = "apiKey", defaultValue = "") String apiKey,
                                    @RequestParam(value = "tokenID", defaultValue = "") String tokenID,
                                    @RequestParam(value = "grade", defaultValue = "") String grade){

        Object value = checker("updateGrade",apiKey,tokenID,new String[] {grade});

        if(!(grade.equals("fr")||grade.equals("so")||grade.equals("jr")||grade.equals("se")||grade.equals("gr")||grade.equals("fa")))
            return new ResponseJson("update grade",false,"Invalid entry");

        String email;

        if(value instanceof String){
            email = (String) value;
        }else{
            return (ResponseJson) value;
        }

        database.updateGrade(email,grade);

        return new ResponseJson("update grade",true,"");
    }
    @RequestMapping(value = Constants.PathConstants.PROFILEPATH+"/updateAdmin",method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE)
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
    @RequestMapping(value= Constants.PathConstants.PROFILEPATH+"/updateDriver",method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseJson updateDriver(@RequestParam(value = "apiKey", defaultValue = "") String apiKey,
                                     @RequestParam(value = "tokenID", defaultValue = "") String tokenID,
                                     @RequestParam(value = "user", defaultValue = "") String user,
                                     @RequestParam(value = "driverStatus", defaultValue = "") String driverStatus){
        Object value = checker("updateToAdmin",apiKey,tokenID,new String[] {user,driverStatus});

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




}
