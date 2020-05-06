package com.example.restservice.database;

import java.util.ArrayList;
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
public class Rides {

    String id;
    Users driverID;
    String startDate;
    String endDate;
    boolean active;
    BusStops destinationBusStop;
    List<BusStops> pickUpBusStop; //max size 10
    List<Users> passengers; //max size 15
    
    public Rides(Users driverID, String startDate, BusStops destinationBusStop, ArrayList<BusStops> pickUpBusStop) {
    	this.driverID = driverID;
    	this.startDate = startDate;
    	this.endDate = null;
    	this.destinationBusStop=destinationBusStop;
    	this.pickUpBusStop = pickUpBusStop;
    	this.passengers = new ArrayList<>();
    	this.active = true;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Users getDriverID() {
        return driverID;
    }

    public void setDriverID(Users driverID) {
        this.driverID = driverID;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }


    public BusStops getDestinationBusStop() {
        return destinationBusStop;
    }

    public void setDestinationBusStop(BusStops destinationBusStop) {
        this.destinationBusStop = destinationBusStop;
    }

    public List<BusStops> getPickUpBusStop() {
        return pickUpBusStop;
    }

    public void setPickUpBusStop(List<BusStops> pickUpBusStop) {
        this.pickUpBusStop = pickUpBusStop;
    }

    public List<Users> getPassengers() {
        return passengers;
    }

    public void setPassengers(List<Users> passengers) {
        this.passengers = passengers;
    }
}
