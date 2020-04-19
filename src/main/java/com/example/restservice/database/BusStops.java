package com.example.restservice.database;

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
public class BusStops {

    String id;
    String rideID;
    String locationName;
    String country;
    String state;
    String city;
    String street;
    String areaCode;
    boolean isDestination;
    String pickupTime;
    String[] passengerIDs;

}
