package com.example.restservice.Representation_Classes;

import com.example.restservice.Constants.Constants;
import com.example.restservice.database.Rides;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;


import java.util.List;

public class Ride {
    String id;
    String driver;
    DateTime startDate;
    DateTime endDate;
    BusStop destination;
    List<BusStop> pickups;
    List<Passenger> passengers;

    public Ride(Rides rides){
        this.id=rides.getId();
        this.driver=rides.getDriverID();
        DateTimeFormatter formatter = DateTimeFormat.forPattern(Constants.DATEFORMAT);
        try{
            this.startDate = formatter.parseDateTime(rides.getStartDate());
            this.endDate = formatter.parseDateTime(rides.getEndDate());
        }catch(Exception e){
            throw new RuntimeException("failed to make ride object due to failure to parse time into DateTime");
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public DateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(DateTime startDate) {
        this.startDate = startDate;
    }

    public DateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(DateTime endDate) {
        this.endDate = endDate;
    }

    public BusStop getDestination() {
        return destination;
    }

    public void setDestination(BusStop destination) {
        this.destination = destination;
    }

    public List<BusStop> getPickups() {
        return pickups;
    }

    public void setPickups(List<BusStop> pickups) {
        this.pickups = pickups;
    }

    public List<Passenger> getPassengers() {
        return passengers;
    }

    public void setPassengers(List<Passenger> passengers) {
        this.passengers = passengers;
    }


}
