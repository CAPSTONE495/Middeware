package com.example.restservice.database;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

/**
 * database scheme User Table{//TODO make this table Primary Key: int User ID
 * String FirstName String LastName String Email boolean/bit Is_Driver int seats
 * //TODO add this column if table is already made boolean/bit Is_Admin char[2]
 * Grade int Total_Rides_Taken int Total_Passengers_Taken double
 * Distance_Traveled double Total_Length_Of_Drives }
 * 
 * Rides Table{//TODO make this table Primary Key: int Ride ID (cant be null)
 * int Driver ID (linked to User ID in User Table) (cant be null) int
 * DestinationBusStopID (linked to values in BusStop Table) DateTime StartDate
 * (cant be null) (DateTime format: MM/dd/YYYY HH:mm:SS) DateTime EndDate (cant
 * be null) int Completed (default is 0) int Cancelled (default is 0) int
 * PickupBusStopID (repeat for 10 columns) (linked to values in BusStop Table)
 * int PassengerID (linked to values in BusStop Table)] (repeat for 15 columns)
 * }
 * 
 * BusStop Table{//TODO make this table Primary Key: int BusStopID String
 * locationName String country String state String city String street String
 * areaCode Boolean Destination int PassengerID (repeat for 15 columns)
 * 
 * }
 * 
 * Passenger Table{//TODO make this table Primary Key: int PassengerID int
 * RiderID (linked to User Table) int RideID (linked to Rides table) DateTime
 * StartDate (DateTime format: MM/dd/YYYY) DateTime EndDate (DateTime format:
 * MM/dd/YYYY) BusStopID pickupLocation (linked to values in BusStop Table) }
 */
@Service
public class Database {
	ApplicationContext ctx = new AnnotationConfigApplicationContext(connection.class);
	MongoOperations mongoOperation = (MongoOperations) ctx.getBean("connector");

	/**
	 * //TODO fill in method Requires autowire change the constructor as you need
	 * any creds should be stored in application.properties and called
	 * using @Value("${'value name'}")
	 */
	@Autowired
	public Database() {

	}

	/**
	 * //TODO fill in method updateGrade method, takes in a string to represent
	 * grade/type of user and email of user find user, replace grade. If any error
	 * occurs throw runtime exception affected tables: {User Table}
	 */
	public void updateGrade(String email, String grade) {
		Query lookup = new Query(Criteria.where("email").is(email));
		mongoOperation.updateFirst(lookup, Update.update("grade", grade), Users.class);

	}

	/**
	 * //TODO fill in method changeAdminStatus method, takes a user's email and a
	 * boolean that represents their new admin status True for being admin, false
	 * for being standard user. find user, change admin value to isAdmin
	 * representation if any error occurs throw runtime exception affected tables:
	 * {User Table}
	 */
	public void changeAdminStatus(String email, boolean isAdmin) {
		Query lookup = new Query(Criteria.where("email").is(email));
		mongoOperation.updateFirst(lookup, Update.update("admin", isAdmin), Users.class);

	}

	/**
	 * //TODO fill in method changeDriverStatus method, takes a user's email and a
	 * boolean that represents their new driver status True for being driver, false
	 * for not a driver. find user, change driver value to isDriver representation
	 * if any error occurs throw runtime exception affected tables: {User Table}
	 */
	public void changeDriverStatus(String email, boolean isDriver) {
		Query lookup = new Query(Criteria.where("email").is(email));
		mongoOperation.updateFirst(lookup, Update.update("driver", isDriver), Users.class);

	}

	/**
	 * //TODO fill in method isAdmin method, takes user's email and returns a
	 * boolean to tell if the account has admin priv or not find user, read isAdmin
	 * value, return isAdmin value if any error occurs throw runtime exception
	 * affected tables: {User Table}
	 */
	public boolean isAdmin(String email) {
		Query lookup = new Query(Criteria.where("email").is(email));
		try {
			Users user = mongoOperation.findOne(lookup, Users.class);
			return user.getAdminStatus();
		} catch (RuntimeException e) {
			throw e;
		}
	}

	/**
	 * //TODO fill in method Adduser method, takes in a string that is the users
	 * email and returns boolean if(email/user does not exist in db){ add user to db
	 * with values: (pkey(auto increment),email,false,false,null,0,0,0,0) return
	 * false; }else{ return true; } affected tables: {User Table}
	 */
	public boolean addUser(String email) {
		try {
			Users user = new Users("first", "last", email, "password");
			mongoOperation.save(user);
			return false;
		} catch (RuntimeException e) {
			return true;
		}
	}

	/**
	 * addName method, takes email, first name, and last name and returns boolean if
	 * update worked find user by email add firstName and lastname to column return
	 * true;
	 */
	public boolean addName(String email, String firstName, String lastName) {
		Query lookup = new Query(Criteria.where("email").is(email));
		try {
			mongoOperation.updateFirst(lookup, Update.update("firstname", firstName), Users.class);
			mongoOperation.updateFirst(lookup, Update.update("lastname", lastName), Users.class);
			return true;
		} catch (RuntimeException e) {
			throw e;
		}
	}
}
