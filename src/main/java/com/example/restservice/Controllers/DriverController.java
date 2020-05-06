package com.example.restservice.Controllers;

import com.example.restservice.Constants.Constants;
import com.example.restservice.Controllers.Support.ComparatorRide;
import com.example.restservice.Representation_Classes.ResponseJson;
import com.example.restservice.database.BusStops;
import com.example.restservice.database.Database;
import com.example.restservice.database.Rides;
import com.example.restservice.database.Users;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;



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
@RestController
public class DriverController {
    @Autowired
    Database database;

    @RequestMapping(value= Constants.PathConstants.DRIVERPATH+"/addRide",method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseJson addRide(@RequestParam(value = "email", defaultValue = "") String userEmail,
                                @RequestParam(value = "locationName", defaultValue = "") String location,
                                @RequestParam(value = "timeOfArrival", defaultValue = "") String time,
                                @RequestParam(value = "country", defaultValue = "") String country,
                                @RequestParam(value = "state", defaultValue = "") String state,
                                @RequestParam(value = "city", defaultValue = "") String city,
                                @RequestParam(value = "street", defaultValue = "") String street,
                                @RequestParam(value = "areaCode", defaultValue = "") String areaCode,
                                @RequestParam(value = "repeat", defaultValue = "") String repeated){

        int repeat;
        repeat = Integer.parseInt(repeated);
        if(repeat<0||52<repeat)
            return new ResponseJson("addRide",false,"Invalid number for repeated");
        DateTimeFormatter formatter = DateTimeFormat.forPattern(Constants.DATEFORMAT);


        Users user = database.getUserInfo(userEmail);
        BusStops des = new BusStops("0",location,country,state,city,street,areaCode,true);
        if(user==null)
            return new ResponseJson("addRide",false,"failed to pull userID");

        DateTime timeOfArrival;
        try{
            timeOfArrival = new DateTime(formatter.parseDateTime(time).getMillis());

        }catch(Exception e){
            return new ResponseJson("addRide",false,"invalid time format: needs to be "+Constants.DATEFORMAT);
        }
        if(timeOfArrival.isAfterNow()){
            return new ResponseJson("addRide",false,"invalid time");
        }
        database.addRide(user, timeOfArrival.toString(), des, new ArrayList<BusStops>());

        for(int i=0;i<repeat;i++) {
            timeOfArrival = timeOfArrival.plusWeeks(1);
            database.addRide(user,timeOfArrival.toString(),des,new ArrayList<BusStops>());
        }





        return new ResponseJson("addRide",true,"");
    }

    @RequestMapping(value= Constants.PathConstants.DRIVERPATH+"/addBusStop",method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseJson addBusStop(@RequestParam(value = "rideID", defaultValue = "") String rideID,
                                   @RequestParam(value = "locationName", defaultValue = "") String location,
                                   @RequestParam(value = "country", defaultValue = "") String country,
                                   @RequestParam(value = "state", defaultValue = "") String state,
                                   @RequestParam(value = "city", defaultValue = "") String city,
                                   @RequestParam(value = "street", defaultValue = "") String street,
                                   @RequestParam(value = "areaCode", defaultValue = "") String areaCode){


        if(database.numOfActivePickUps(rideID)>=Constants.MAXBUSSTOPS)
            return new ResponseJson("addBusStop",false,"Reached max amount of pickups");

        BusStops des;
        try {
            des = new BusStops(AuthController.generateAPIKey(),location,country,state,city,street,areaCode,false);
        } catch (NoSuchAlgorithmException e) {
            return new ResponseJson("addRide",false,"failed to generate location id");
        }

        boolean added = database.addBusStop(rideID,des);
        String message = "";
        if(!added){
            message = "could not find ride entry";
        }

        return new ResponseJson("addRide",added,message);
    }

    @RequestMapping(value= Constants.PathConstants.DRIVERPATH+"/acceptPassenger",method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)
    public Rides acceptPassenger(@RequestParam(value = "rideID", defaultValue = "") String rideID,
                                @RequestParam(value = "email", defaultValue = "") String email,
                                @RequestParam(value = "accepted", defaultValue = "") String accpeted){
        Rides ride = database.getRide(rideID);
        boolean accept;
        boolean edit = false;
        try{
            accept = Boolean.parseBoolean(accpeted);
        }catch(Exception e){
            throw new RuntimeException("failed to parse accepted");
        }
        for(Iterator<Users> iterator = ride.getPassengers().iterator();iterator.hasNext();){
            Users user = iterator.next();
            if(!user.getEmail().equals(email)) {
                continue;
            }else if(accept){
                user.setStatus("Accepted");
            }else{
                iterator.remove();
            }
            edit = true;
            break;
        }

        if(edit)
            database.updateRide(ride);

        return ride;
    }


    @RequestMapping(value= Constants.PathConstants.DRIVERPATH+"/getMyDrives",method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Rides> getMyDrives(@RequestParam(value = "email", defaultValue = "") String email){

        List<Rides> rides = database.getDrives(true);
        for(Iterator<Rides> iterator = rides.iterator();iterator.hasNext();){
            if(!iterator.next().getDriverID().getEmail().equals(email)){
                iterator.remove();
            }
        }

        Collections.sort(rides,new ComparatorRide());
        return rides;

    }


    @RequestMapping(value= Constants.PathConstants.DRIVERPATH+"/deleteRide",method = RequestMethod.DELETE,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseJson deleteRide(@RequestParam(value = "rideID", defaultValue = "") String rideID){

        database.deleteRide(rideID);

        return new ResponseJson("deleteRide",true,"deleted "+rideID);
    }

    @RequestMapping(value= Constants.PathConstants.DRIVERPATH+"/deleteBusStop",method = RequestMethod.DELETE,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseJson deleteBusStop(@RequestParam(value = "rideID", defaultValue = "") String rideID,
                                      @RequestParam(value = "busStopID", defaultValue = "") String busStopID){

        boolean val = database.deleteBusStop(rideID,busStopID);
        String message = "deleted "+rideID;
        if(!val)
            message = "couldn't find ride or busStop";
        return new ResponseJson("deleteRide",val,message);
    }






}
