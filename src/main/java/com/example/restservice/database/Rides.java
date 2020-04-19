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
    //TODO add dateTime vars, im not sure what type these should be.
    //DateTime startDate
    //DateTime EndDate
    int completed;
    int cancelled;
    String[] pickupBusStopIDs; //max size 10
    String[] PassengerIDs; //max size 15
}
