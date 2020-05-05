package com.example.restservice.Controllers;

import com.example.restservice.Constants.Constants;
import com.example.restservice.Representation_Classes.ResponseJson;
import com.example.restservice.database.Database;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static com.example.restservice.Controllers.AuthController.checker;

@RestController
public class PassengerController {

    @Autowired
    Database database;

    @RequestMapping(value= Constants.PathConstants.PASSENGERPATH+"/findPickups",method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseJson getRelatedBusStops(@RequestParam(value = "apiKey", defaultValue = "") String apiKey,
                                           @RequestParam(value = "tokenID", defaultValue = "") String tokenID,
                                           @RequestParam(value = "locationName", defaultValue = "") String location,
                                           @RequestParam(value = "timeOfArrival", defaultValue = "") String time,
                                           @RequestParam(value = "country", defaultValue = "") String country,
                                           @RequestParam(value = "state", defaultValue = "") String state,
                                           @RequestParam(value = "city", defaultValue = "") String city,
                                           @RequestParam(value = "street", defaultValue = "") String street,
                                           @RequestParam(value = "areaCode", defaultValue = "") String areaCode){
        Object value = checker("addRide",apiKey,tokenID,new String[] {location,country,state,time,city,street,areaCode});
        String email;
        if(value instanceof String){
            email = (String) value;
        }else{
            return (ResponseJson) value;
        }

        DateTimeFormatter formatter = DateTimeFormat.forPattern(Constants.DATEFORMAT);
        DateTime time1;
        try{
            time1 = formatter.parseDateTime(time);
        }catch(Exception e){
            return new ResponseJson("findRides",false,"Failed to convert time interval. Expected: "+Constants.DATEFORMAT);
        }

        return null;//ResponseJson("findRides",true,"",database.getSearchedBusStops(location,time1.toString(),country,state,city,street,areaCode));
    }

    @RequestMapping(value= Constants.PathConstants.PASSENGERPATH+"/getMyRides",method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseJson getMyRides(@RequestParam(value = "apiKey", defaultValue = "") String apiKey,
                                   @RequestParam(value = "tokenID", defaultValue = "") String tokenID){

        Object value = checker("addRide",apiKey,tokenID,new String[] {});
        String email;
        if(value instanceof String){
            email = (String) value;
        }else{
            return (ResponseJson) value;
        }

        String userID = database.emailToID(email);

        return null; //new ResponseJson("addRide",true,"",database.getMyRides(userID,true));
    }

    @RequestMapping(value= Constants.PathConstants.PASSENGERPATH+"/addRide",method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseJson addRide(@RequestParam(value = "apiKey", defaultValue = "") String apiKey,
                                @RequestParam(value = "tokenID", defaultValue = "") String tokenID,
                                @RequestParam(value = "startTime", defaultValue = "") String st,
                                @RequestParam(value = "endTime", defaultValue = "") String et,
                                @RequestParam(value = "busStopID", defaultValue = "") String busStopID){

        Object value = checker("addRide",apiKey,tokenID,new String[] {});
        String email;
        if(value instanceof String){
            email = (String) value;
        }else{
            return (ResponseJson) value;
        }

        DateTimeFormatter formatter = DateTimeFormat.forPattern(Constants.DATEFORMAT);
        DateTime start;
        DateTime end;
        try{
            start = formatter.parseDateTime(st);
            end = formatter.parseDateTime(et);
        }catch(Exception e){
            return new ResponseJson("findRides",false,"Failed to convert time interval. Expected: "+Constants.DATEFORMAT);
        }

        String userID = database.emailToID(email);

        //database.addPassenger(userID,start.toString(),end.toString(),busStopID);

        return new ResponseJson("addRide",true,"");
    }


    public ResponseJson cancelRide(@RequestParam(value = "apiKey", defaultValue = "") String apiKey,
                                   @RequestParam(value = "tokenID", defaultValue = "") String tokenID){
        Object value = checker("addRide",apiKey,tokenID,new String[] {});
        String email;
        if(value instanceof String){
            email = (String) value;
        }else{
            return (ResponseJson) value;
        }

        String userID = database.emailToID(email);

        //database.removePassenger(userID);
        return new ResponseJson("addRide",true,"");
    }
}
