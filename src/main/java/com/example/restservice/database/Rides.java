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
    String[] queue1;
    String[] queue2;
    String[] queue3;
    String[] queue4;
    String[] queue5;
    String[] queue6;
    String[] queue7;
    String[] queue8;
    String[] queue9;
    String[] queue10;
    String[] queue11;
    String[] queue12;
    String[] queue13;
    String[] queue14;
    String[] queue15;

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

    public String[] getQueue1() {
        return queue1;
    }

    public void setQueue1(String[] queue1) {
        this.queue1 = queue1;
    }

    public String[] getQueue2() {
        return queue2;
    }

    public void setQueue2(String[] queue2) {
        this.queue2 = queue2;
    }

    public String[] getQueue3() {
        return queue3;
    }

    public void setQueue3(String[] queue3) {
        this.queue3 = queue3;
    }

    public String[] getQueue4() {
        return queue4;
    }

    public void setQueue4(String[] queue4) {
        this.queue4 = queue4;
    }

    public String[] getQueue5() {
        return queue5;
    }

    public void setQueue5(String[] queue5) {
        this.queue5 = queue5;
    }

    public String[] getQueue6() {
        return queue6;
    }

    public void setQueue6(String[] queue6) {
        this.queue6 = queue6;
    }

    public String[] getQueue7() {
        return queue7;
    }

    public void setQueue7(String[] queue7) {
        this.queue7 = queue7;
    }

    public String[] getQueue8() {
        return queue8;
    }

    public void setQueue8(String[] queue8) {
        this.queue8 = queue8;
    }

    public String[] getQueue9() {
        return queue9;
    }

    public void setQueue9(String[] queue9) {
        this.queue9 = queue9;
    }

    public String[] getQueue10() {
        return queue10;
    }

    public void setQueue10(String[] queue10) {
        this.queue10 = queue10;
    }

    public String[] getQueue11() {
        return queue11;
    }

    public void setQueue11(String[] queue11) {
        this.queue11 = queue11;
    }

    public String[] getQueue12() {
        return queue12;
    }

    public void setQueue12(String[] queue12) {
        this.queue12 = queue12;
    }

    public String[] getQueue13() {
        return queue13;
    }

    public void setQueue13(String[] queue13) {
        this.queue13 = queue13;
    }

    public String[] getQueue14() {
        return queue14;
    }

    public void setQueue14(String[] queue14) {
        this.queue14 = queue14;
    }

    public String[] getQueue15() {
        return queue15;
    }

    public void setQueue15(String[] queue15) {
        this.queue15 = queue15;
    }
}
