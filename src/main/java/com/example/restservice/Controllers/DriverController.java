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

import static com.example.restservice.Controllers.AuthController.checker;

/*
TODO fill in the blanks, im not sure how to handle dateTime or arrays for mongodb.
Primary Key: int RideID (cant be null)
 * int DriverID (linked to User ID in User Table) (cant be null)
 * int DestinationBusStopID (linked to values in BusStop Table)
 * DateTime StartDate (cant be null) (DateTime format: MM/dd/YYYY HH:mm:SS)
 * DateTime EndDate (cant be null)
 * int Completed (default is 0)
 * int Cancelled (default is 0)
 * int PickupBusStopID (repeat for 10 columns) (linked to values in BusStop Table)
 * int PassengerID (linked to values in BusStop Table)] (repeat for 15 columns)
 */
/*
BusStop Table{//TODO make this table
 * Primary Key: int BusStopID
 * String locationName
 * String country
 * String state
 * String city
 * String street
 * String areaCode
 * Boolean Destination
 * int PassengerID (repeat for 15 columns)
 */
public class DriverController {
    @Autowired
    Database database;

    @RequestMapping(value= Constants.PathConstants.DRIVERPATH+"/addRide",method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseJson addRide(@RequestParam(value = "apiKey", defaultValue = "") String apiKey,
                                @RequestParam(value = "tokenID", defaultValue = "") String tokenID,
                                @RequestParam(value = "startDate", defaultValue = "") String start,
                                @RequestParam(value = "endDate", defaultValue = "") String end,
                                @RequestParam(value = "locationName", defaultValue = "") String location,
                                @RequestParam(value = "country", defaultValue = "") String country,
                                @RequestParam(value = "state", defaultValue = "") String state,
                                @RequestParam(value = "city", defaultValue = "") String city,
                                @RequestParam(value = "street", defaultValue = "") String street,
                                @RequestParam(value = "areaCode", defaultValue = "") String areaCode){
        Object value = checker("updateMaxSeats",apiKey,tokenID,new String[] {start,end,country,state,city,street,areaCode});
        String email;
        if(value instanceof String){
            email = (String) value;
        }else{
            return (ResponseJson) value;
        }

        DateTimeFormatter formatter = DateTimeFormat.forPattern(Constants.DATEFORMAT);
        DateTime startTime;
        DateTime endTime;
        try{
            startTime = formatter.parseDateTime(start);
            endTime = formatter.parseDateTime(end);
        }catch(Exception e){
            return new ResponseJson("addRide",false,"invalid time format: needs to be "+Constants.DATEFORMAT);
        }
        if(!(startTime.isAfterNow()&&startTime.isBefore(endTime))){
            return new ResponseJson("addRide",false,"invalid time");
        }

        //TODO probably will need some check to reverse changes if something breaks halfway through but currently just praying nothing breaks
        String busStopID = database.addBusStop(location,"",country,state,city,street,areaCode,true,"");

        String rideID = database.addRide(email,startTime,endTime,busStopID);

        database.linkRideAndBusStop(busStopID,rideID);

        return new ResponseJson("addRide",true,"");
    }

    @RequestMapping(value= Constants.PathConstants.DRIVERPATH+"/addBusStop",method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseJson addBusStop(@RequestParam(value = "apiKey", defaultValue = "") String apiKey,
                                   @RequestParam(value = "tokenID", defaultValue = "") String tokenID,
                                   @RequestParam(value = "rideID", defaultValue = "") String rideID,
                                   @RequestParam(value = "locationName", defaultValue = "") String location,
                                   @RequestParam(value = "startTime", defaultValue = "") String start,
                                   @RequestParam(value = "country", defaultValue = "") String country,
                                   @RequestParam(value = "state", defaultValue = "") String state,
                                   @RequestParam(value = "city", defaultValue = "") String city,
                                   @RequestParam(value = "street", defaultValue = "") String street,
                                   @RequestParam(value = "areaCode", defaultValue = "") String areaCode){

        Object value = checker("updateMaxSeats",apiKey,tokenID,new String[] {rideID,start,country,state,city,street,areaCode});
        String email;
        if(value instanceof String){
            email = (String) value;
        }else{
            return (ResponseJson) value;
        }

        database.addBusStop(location,start,country,state,city,street,areaCode,false,rideID);

        return new ResponseJson("addRide",true,"");
    }

    

}
