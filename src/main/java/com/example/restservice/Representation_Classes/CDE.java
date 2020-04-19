package com.example.restservice.Representation_Classes;

import org.joda.time.DateTime;


import java.util.ArrayList;
import java.util.List;

public class CDE {
    private String type = "Complete Database Entry";
    private List<Ride> rides = new ArrayList<>();

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<Ride> getRides() {
        return rides;
    }

    public void setRides(List<Ride> rides) {
        this.rides = rides;
    }

    public class Ride{
        String id;
        String driver;
        DateTime startDate;
        DateTime endDate;
        List<BusStop> destination;
        List<BusStop> pickups;
        List<Passenger> passengers;

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

        public List<BusStop> getDestination() {
            return destination;
        }

        public void setDestination(List<BusStop> destination) {
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

    public class BusStop{
        String locationName;
        String country;
        String state;
        String city;
        String street;
        String areaCode;
        boolean isDestination;
        List<Passenger> peopleToPickup;

        public String getLocationName() {
            return locationName;
        }

        public void setLocationName(String locationName) {
            this.locationName = locationName;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getStreet() {
            return street;
        }

        public void setStreet(String street) {
            this.street = street;
        }

        public String getAreaCode() {
            return areaCode;
        }

        public void setAreaCode(String areaCode) {
            this.areaCode = areaCode;
        }

        public boolean isDestination() {
            return isDestination;
        }

        public void setDestination(boolean destination) {
            isDestination = destination;
        }

        public List<Passenger> getPeopleToPickup() {
            return peopleToPickup;
        }

        public void setPeopleToPickup(List<Passenger> peopleToPickup) {
            this.peopleToPickup = peopleToPickup;
        }
    }

    public class Passenger{
        String rideID;
        String rider;
        DateTime startDate;
        DateTime endDate;

        public String getRideID() {
            return rideID;
        }

        public void setRideID(String rideID) {
            this.rideID = rideID;
        }

        public String getRider() {
            return rider;
        }

        public void setRider(String rider) {
            this.rider = rider;
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
    }
}
