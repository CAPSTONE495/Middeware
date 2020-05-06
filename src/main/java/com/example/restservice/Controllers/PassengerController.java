package com.example.restservice.Controllers;

import com.example.restservice.Constants.Constants;
import com.example.restservice.Controllers.Support.ComparatorRide;
import com.example.restservice.Representation_Classes.ResponseJson;
import com.example.restservice.database.Database;
import com.example.restservice.database.Rides;
import com.example.restservice.database.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;



@RestController
public class PassengerController {

    @Autowired
    Database database;


    @RequestMapping(value= Constants.PathConstants.PASSENGERPATH+"/getMyRides",method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Rides> getMyRides(@RequestParam(value = "email", defaultValue = "") String email){

        List<Rides> myRides = database.getDrives(true);
        if(myRides==null)
            return null;
        outerloop:
        for(Iterator<Rides> iterator = myRides.iterator();iterator.hasNext();){
            Rides ride = iterator.next();
            for(Users user : ride.getPassengers()){
                if(user.getEmail().equals(email)&&user.getStatus().equals("accepted")){
                    continue outerloop;
                }
            }
            iterator.remove();
        }
        Collections.sort(myRides,new ComparatorRide());
        return myRides; //new ResponseJson("addRide",true,"",database.getMyRides(userID,true));
    }

    @RequestMapping(value= Constants.PathConstants.PASSENGERPATH+"/requestRide",method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseJson requestRide(@RequestParam(value = "email", defaultValue = "") String email,
                                    @RequestParam(value = "rideID", defaultValue = "") String rideID){


        Rides ride = database.getRide(rideID);
        if(ride.getPassengers().size()>=ride.getDriverID().getSeats())
            return new ResponseJson("requestRide",false,"drive is filled");

        Users user = database.getUserInfo(email);
        user.setStatus("Pending");
        ride.getPassengers().add(user);
        database.updateRide(ride);
        return new ResponseJson("requestRide",true,"request Sent");
    }

    @RequestMapping(value= Constants.PathConstants.PASSENGERPATH+"/requestRide",method = RequestMethod.PUT,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseJson cancelRide(@RequestParam(value = "email", defaultValue = "") String email,
                                   @RequestParam(value = "rideID", defaultValue = "") String rideID){

        Rides ride = database.getRide(rideID);
        boolean edit = false;
        for(Iterator<Users> iterator = ride.getPassengers().iterator();iterator.hasNext();){
            Users user = iterator.next();
            if(user.getEmail().equals(email)){
                iterator.remove();
                edit=true;
                break;
            }
        }
        if(edit)
            database.updateRide(ride);

        return new ResponseJson("requestRide",edit,"");
    }
}
