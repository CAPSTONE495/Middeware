package com.example.restservice.Representation_Classes;

import java.util.List;

public class BusStop{
    String locationName;
    String pickUpTime;
    String country;
    String state;
    String city;
    String street;
    String areaCode;
    boolean isDestination;
    List<Passenger> peopleToPickup;

    public String getPickUpTime() {
        return pickUpTime;
    }

    public void setPickUpTime(String pickUpTime) {
        this.pickUpTime = pickUpTime;
    }

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
