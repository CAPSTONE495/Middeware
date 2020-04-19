package com.example.restservice.Controllers;

import com.example.restservice.Constants.Constants;
import com.example.restservice.Representation_Classes.ResponseJson;
import com.example.restservice.Representation_Classes.Ride;
import com.example.restservice.database.Database;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

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
                                @RequestParam(value = "timeOfArrival", defaultValue = "") String time,
                                @RequestParam(value = "country", defaultValue = "") String country,
                                @RequestParam(value = "state", defaultValue = "") String state,
                                @RequestParam(value = "city", defaultValue = "") String city,
                                @RequestParam(value = "street", defaultValue = "") String street,
                                @RequestParam(value = "areaCode", defaultValue = "") String areaCode){
        Object value = checker("addRide",apiKey,tokenID,new String[] {start,end,country,state,time,city,street,areaCode});
        String email;
        if(value instanceof String){
            email = (String) value;
        }else{
            return (ResponseJson) value;
        }

        DateTimeFormatter formatter = DateTimeFormat.forPattern(Constants.DATEFORMAT);
        DateTime startTime;
        DateTime endTime;
        DateTime timeOfArrival;
        try{
            startTime = formatter.parseDateTime(start);
            endTime = formatter.parseDateTime(end);
            timeOfArrival = formatter.parseDateTime(time);
        }catch(Exception e){
            return new ResponseJson("addRide",false,"invalid time format: needs to be "+Constants.DATEFORMAT);
        }
        if(!(startTime.isAfterNow()&&startTime.isBefore(endTime))){
            return new ResponseJson("addRide",false,"invalid time");
        }

        if(!database.isDriver(email))
            return new ResponseJson("addRide",false,"Not a valid Driver");

        //TODO probably will need some check to reverse changes if something breaks halfway through but currently just praying nothing breaks
        String busStopID = database.addBusStop("",location,timeOfArrival.toString(),country,state,city,street,areaCode,true);
        String userID = database.emailToID(email);
        String rideID = database.addRide(userID,startTime.toString(),endTime.toString(),busStopID);

        database.linkRideAndBusStop(busStopID,rideID);

        return new ResponseJson("addRide",true,"");
    }

    @RequestMapping(value= Constants.PathConstants.DRIVERPATH+"/addBusStop",method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseJson addBusStop(@RequestParam(value = "apiKey", defaultValue = "") String apiKey,
                                   @RequestParam(value = "tokenID", defaultValue = "") String tokenID,
                                   @RequestParam(value = "rideID", defaultValue = "") String rideID,
                                   @RequestParam(value = "locationName", defaultValue = "") String location,
                                   @RequestParam(value = "pickUpTime", defaultValue = "") String time,
                                   @RequestParam(value = "country", defaultValue = "") String country,
                                   @RequestParam(value = "state", defaultValue = "") String state,
                                   @RequestParam(value = "city", defaultValue = "") String city,
                                   @RequestParam(value = "street", defaultValue = "") String street,
                                   @RequestParam(value = "areaCode", defaultValue = "") String areaCode){

        Object value = checker("addBusStop",apiKey,tokenID,new String[] {rideID,time,country,state,city,street,areaCode});
        String email;
        if(value instanceof String){
            email = (String) value;
        }else{
            return (ResponseJson) value;
        }

        DateTimeFormatter formatter = DateTimeFormat.forPattern(Constants.DATEFORMAT);
        DateTime pickUpTime;
        try{
            pickUpTime = formatter.parseDateTime(time);
        }catch (Exception e){
            return new ResponseJson("addBusStop",false,"invalid time format: needs to be "+Constants.DATEFORMAT);
        }

        String userID = database.emailToID(email);
        if(userID==null)
            return  new ResponseJson("addBusStop",false,"couldn't find userID");

        if(database.numOfActivePickUps(rideID)<Constants.MAXBUSSTOPS)
            return new ResponseJson("addBusStop",false,"Reached max amount of pickups");

        if(!database.isDriver(email))
            return new ResponseJson("addRide",false,"Not a valid Driver");

        database.addBusStop(rideID,location,pickUpTime.toString(),country,state,city,street,areaCode,false);

        return new ResponseJson("addRide",true,"");
    }


    @RequestMapping(value= Constants.PathConstants.DRIVERPATH+"/getMyRides",method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseJson getMyRides(@RequestParam(value = "apiKey", defaultValue = "") String apiKey,
                                   @RequestParam(value = "tokenID", defaultValue = "") String tokenID){

        Object value = checker("getMyRides",apiKey,tokenID,new String[] {});
        String email;
        if(value instanceof String){
            email = (String) value;
        }else{
            return (ResponseJson) value;
        }

        String userID = database.emailToID(email);

        if(userID==null)
            return new ResponseJson("getMyRides",false,"Failed to find userID");

        if(!database.isDriver(email))
            return new ResponseJson("addRide",false,"Not a valid Driver");

        List<Ride> rides = database.getDriverRides(userID,true);

        return new ResponseJson("getMyRides",true,"",rides);

    }


    @RequestMapping(value= Constants.PathConstants.DRIVERPATH+"/deleteRide",method = RequestMethod.DELETE,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseJson deleteRide(@RequestParam(value = "apiKey", defaultValue = "") String apiKey,
                                   @RequestParam(value = "tokenID", defaultValue = "") String tokenID,
                                   @RequestParam(value = "rideID", defaultValue = "") String rideID){
        Object value = checker("getMyRides",apiKey,tokenID,new String[] {rideID});
        String email;
        if(value instanceof String){
            email = (String) value;
        }else{
            return (ResponseJson) value;
        }



        return new ResponseJson("getMyRides",true,"");
    }

    @RequestMapping(value= Constants.PathConstants.DRIVERPATH+"/cancelRide",method = RequestMethod.DELETE,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseJson cancelRide(@RequestParam(value = "apiKey", defaultValue = "") String apiKey,
                                   @RequestParam(value = "tokenID", defaultValue = "") String tokenID,
                                   @RequestParam(value = "rideID", defaultValue = "") String rideID){
        Object value = checker("getMyRides",apiKey,tokenID,new String[] {rideID});
        String email;
        if(value instanceof String){
            email = (String) value;
        }else{
            return (ResponseJson) value;
        }



        return new ResponseJson("getMyRides",true,"");
    }





}
