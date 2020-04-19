package com.example.restservice.database;

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
    String destinationBusStopID;
    String startDate;
    String EndDate;
    int completed;
    int cancelled;
    String[] pickUpBusStopIDs; //max size 10
    String[] PassengerIDs; //max size 15

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

    public String getDestinationBusStopID() {
        return destinationBusStopID;
    }

    public void setDestinationBusStopID(String destinationBusStopID) {
        this.destinationBusStopID = destinationBusStopID;
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

    public String[] getPickUpBusStopIDs() {
        return pickUpBusStopIDs;
    }

    public void setPickUpBusStopIDs(String[] pickUpBusStopIDs) {
        this.pickUpBusStopIDs = pickUpBusStopIDs;
    }

    public String[] getPassengerIDs() {
        return PassengerIDs;
    }

    public void setPassengerIDs(String[] passengerIDs) {
        PassengerIDs = passengerIDs;
    }
}
