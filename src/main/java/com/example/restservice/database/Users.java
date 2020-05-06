package com.example.restservice.database;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
/*
User Table{
    Primary Key: int User ID
    String FirstName
    String LastName
    String Email
    boolean/bit Is_Driver
    int seats
    boolean/bit Is_Admin
    char[2] Grade
    int Total_Rides_Taken
    int Total_Passengers_Taken
    double Distance_Traveled
    double Total_Length_Of_Drives
}
*/
@Document(collection = "Users")
public class Users {
	
	@Id
	private String id;
	String firstname;
	String lastname;
	String pickupID;
	String grade;
	String email;
	String about;
	String status;
	boolean driver;
	boolean admin;
	int num_seats;
	int rides_taken;
	int passenger_taken;
	int ratings;
	int raters;
	double distance_traveled;
	double total_time;//this value represents the time for all the rides
	
	public Users(String firstname,String lastname, String email) {
		super();
		this.firstname = firstname;
		this.lastname = lastname;
		this.email = email;
		this.pickupID = null;
		this.grade = null;
		this.about = null;
		this.status = null;
		this.driver=false;
		this.admin=false;
		ratings = 0;
		raters = 0;
		this.num_seats = 0;
		this.rides_taken = 0;
		this.passenger_taken = 0;
		this.distance_traveled=0;
		this.total_time=0;


	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getfirstName() {
		return firstname;
	}

	public void setfirstName(String fname) {
		this.firstname = fname;
	}
	
	public String getlastName() {
		return firstname;
	}

	public void setlastname(String lname) {
		this.lastname = lname;
	}
	
	public String getPickUp() {
		return pickupID;
	}
	
	public void setPickUp(String pickupID) {
		this.pickupID = pickupID;
	}
	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}
	
	public String getEmail() {
		return email;
	}
	
	public String getAbout(){
		return about;
	}
	
	public void setAbout(String about){
		this.about = about;
	}
	
	public String getStatus(){
		return status;
	}
	
	public void setStatus(String status){
		this.status = status;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public boolean getAdminStatus() {
		return admin;
	}

	public void setAdminStatus(boolean isAdmin) {
		this.admin = isAdmin;
	}
	
	public boolean getDriverStatus() {
		return driver;
	}

	public void setDriverStatus(boolean isDriver) {
		this.driver = isDriver;
	}
	
	public int getSeats() {
		return num_seats;
	}

	public void setSeats(int seats) {
		num_seats = seats;
	}
	
	public int getRidesTaken() {
		return rides_taken;
	}

	public void setRidesTaken(int rides) {
		rides_taken = rides;
	}
	
	public int getPassengerTaken() {
		return passenger_taken;
	}

	public void setPassengerTaken(int passenger) {
		passenger_taken = passenger;
	}

	public int getRatings() {
		return ratings;
	}

	public void setRatings(int ratings) {
		this.ratings = ratings;
	}

	public int getRaters() {
		return raters;
	}

	public void setRaters(int raters) {
		this.raters = raters;
	}

	public double getDistanceTraveled() {
		return distance_traveled;
	}
	

	public void setDistanceTraveled(int distance) {
		distance_traveled = distance;
	}
	
	public double getTotalTime() {
		return total_time;
	}

	public void setTotalTime(int total) {
		total_time = total;
	}

}
