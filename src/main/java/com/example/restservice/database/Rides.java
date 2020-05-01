package com.example.restservice.database;

import java.util.ArrayList;

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
    String driverID;
    BusStops destinationBusStop;
    String startDate;
    String EndDate;
    int completed;
    int cancelled;
    ArrayList<BusStops> pickUpBusStop; //max size 10
    ArrayList<Passengers> passengers; //max size 15
    ArrayList<String> queue;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDriverID() {
        return driverID;
    }

    public void setDriverID(String driverID) {
        this.driverID = driverID;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return EndDate;
    }

    public void setEndDate(String endDate) {
        EndDate = endDate;
    }

    public int getCompleted() {
        return completed;
    }

    public void setCompleted(int completed) {
        this.completed = completed;
    }

    public int getCancelled() {
        return cancelled;
    }

    public void setCancelled(int cancelled) {
        this.cancelled = cancelled;
    }

    public BusStops getDestinationBusStop() {
        return destinationBusStop;
    }

    public void setDestinationBusStop(BusStops destinationBusStop) {
        this.destinationBusStop = destinationBusStop;
    }

    public ArrayList<BusStops> getPickUpBusStop() {
        return pickUpBusStop;
    }

    public void setPickUpBusStop(ArrayList<BusStops> pickUpBusStop) {
        this.pickUpBusStop = pickUpBusStop;
    }

    public ArrayList<Passengers> getPassengers() {
        return passengers;
    }

    public void setPassengers(ArrayList<Passengers> passengers) {
        this.passengers = passengers;
    }

    public ArrayList<String> getQueue() {
        return queue;
    }

    public void setQueue(ArrayList<String> queue) {
        this.queue = queue;
    }
}
