package com.example.restservice.database;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
/*
User Table{//TODO make this table
    Primary Key: int User ID
    String FirstName
    String LastName
    String Email
    boolean/bit Is_Driver
    int seats //TODO add this column if table is already made
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
	String email;
	boolean driver;
	boolean admin;
	int grade;
	int num_seats;
	int rides_taken;
	int passenger_taken;
	double distance_traveled;
	double total_distance;
	String password;
	
	public Users(String firstname,String lastname, String email, String password) {
		super();
		this.firstname = firstname;
		this.lastname = lastname;
		this.email = email;
		this.password = password;
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
	
	public String getEmail() {
		return email;
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
	
	public int getGrade() {
		return grade;
	}

	public void setGrade(int grade) {
		this.grade = grade;
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
	
	public double getDistanceTraveled() {
		return distance_traveled;
	}
	

	public void setDistanceTraveled(int distance) {
		distance_traveled = distance;
	}
	
	public double getTotalDistance() {
		return distance_traveled;
	}

	public void setTotalDistance(int total) {
		total_distance = total;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}