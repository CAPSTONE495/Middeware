package com.example.restservice.database;
/*
 * Passenger Table{//TODO make this table
 * Primary Key: int PassengerID
 * int RiderID (linked to User Table)
 * int RideID (linked to Rides table)
 * DateTime StartDate (DateTime format: MM/dd/YYYY)
 * DateTime EndDate (DateTime format: MM/dd/YYYY)
 * int BusStopID pickupLocation (linked to values in BusStop Table) }
 */
public class Passengers {

    String id;
    String riderID;
    String rideID;
    String startDate;
    String endDate;
    String[] pickupLocations;

}
