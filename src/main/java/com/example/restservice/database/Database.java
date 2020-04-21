package com.example.restservice.database;

import com.example.restservice.Representation_Classes.Ride;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.util.List;

/**
 * database scheme User Table{
 * Primary Key: int User ID
 * String FirstName
 * String LastName
 * String Email
 * boolean/bit Is_Driver
 * int seats
 * boolean/bit Is_Admin
 * char[2] Grade
 * int Total_Rides_Taken
 * int Total_Passengers_Taken
 * double Distance_Traveled double Total_Length_Of_Drives }
 * 
 * Rides Table{//TODO make this table
 * Primary Key: int Ride ID (cant be null)
 * int Driver ID (linked to User ID in User Table) (cant be null)
 * int DestinationBusStopID (linked to values in BusStop Table)
 * DateTime StartDate (cant be null) (DateTime format: MM/dd/YYYY HH:mm:SS)
 * DateTime EndDate (cant be null)
 * int Completed (default is 0)
 * int Cancelled (default is 0)
 * int PickupBusStopID (repeat for 10 columns) (linked to values in BusStop Table)
 * int PassengerID (linked to values in BusStop Table)] (repeat for 15 columns)
 * }
 * 
 * BusStop Table{//TODO make this table
 * Primary Key: int BusStopID
 * String locationName
 * String country
 * String state
 * String city
 * String street
 * String areaCode
 * Boolean Destination
 * int PassengerID (repeat for 15 columns)
 * 
 * }
 * 
 * Passenger Table{//TODO make this table
 * Primary Key: int PassengerID
 * int RiderID (linked to User Table)
 * int RideID (linked to Rides table)
 * DateTime StartDate (DateTime format: MM/dd/YYYY)
 * DateTime EndDate (DateTime format: MM/dd/YYYY)
 * int BusStopID pickupLocation (linked to values in BusStop Table) }
 *
 *
 * method overview
 * Users{
 *     addUser,getUser,updateGrade,changeAdminStatus,changeDriverStatus,isAdmin
 * }
 *
 * rides{
 *
 * }
 *
 * busStops{
 *
 * }
 */
@Service
public class Database {
	ApplicationContext ctx = new AnnotationConfigApplicationContext(connection.class);
	MongoOperations mongoOperation = (MongoOperations) ctx.getBean("connector");

	@Autowired
	public Database() {

	}


	/*

	User methods that create or finalize entries---------------------------------------------------------------------------------------------------------------------------------------------

	 */
	/**
	 * @param email
	 * make user and add to db
	 * affected tables: {User Table}
	 */
	public boolean addUser(String email) {
		try {
			Users user = new Users("first", "last", email, "password");
			mongoOperation.save(user);
			return false;
		} catch (Exception e) {
			throw new RuntimeException("Error with addUser: "+e.getLocalizedMessage());
		}
	}

	/**
	 * @param email
	 * @param firstName
	 * @param lastName
	 * addName method, takes email, first name, and last name and returns boolean if
	 * update worked find user by email add firstName and lastname to column return
	 * true;
	 * affected tables: {User Table}
	 */
	public boolean addName(String email, String firstName, String lastName) {
		Query lookup = new Query(Criteria.where("email").is(email));
		try {
			mongoOperation.updateFirst(lookup, Update.update("firstname", firstName), Users.class);
			mongoOperation.updateFirst(lookup, Update.update("lastname", lastName), Users.class);
			return true;
		} catch (Exception e) {
			throw new RuntimeException("Error with addName: "+e.getLocalizedMessage());
		}
	}
	/**
	 * @param email
	 * TODO fill in this method
	 * Take an email of user, find user by their email, return their account info in the form of a string
	 * affected tables: {User Table}
	*/
	public String getUserInfo(String email){
		return null;
	}

	/*

	User methods that edit values---------------------------------------------------------------------------------------------------------------------------------------------------------

	 */

	/**
	 *
	 * @param email
	 * @param grade
	 *
	 * updateGrade method, takes in a string to represent
	 * grade/type of user and email of user find user, replace grade. If any error
	 * occurs throw runtime exception
	 * affected tables: {User Table}
	 */
	public void updateGrade(String email, String grade) {
		try {
			Query lookup = new Query(Criteria.where("email").is(email));
			mongoOperation.updateFirst(lookup, Update.update("grade", grade), Users.class);
		}catch (Exception e){
			throw new RuntimeException("Failed to Update grade: "+e.getLocalizedMessage());
		}
	}


	/**
	 *
	 * @param email
	 * @param isAdmin
	 *
	 * changeAdminStatus method, takes a user's email and a
	 * boolean that represents their new admin status True for being admin, false
	 * for being standard user. find user, change admin value to isAdmin
	 * representation if any error occurs throw runtime exception
	 * affected tables: {User Table}
	 */
	public void changeAdminStatus(String email, boolean isAdmin) {
		try {
			Query lookup = new Query(Criteria.where("email").is(email));
			mongoOperation.updateFirst(lookup, Update.update("admin", isAdmin), Users.class);
		}catch(Exception e){
			throw new RuntimeException("failed to change admin status: "+e.getLocalizedMessage());
		}
	}


	/**
	 *
	 * @param email
	 * @param isDriver
	 *
	 * changeDriverStatus method, takes a user's email and a
	 * boolean that represents their new driver status True for being driver, false
	 * for not a driver. find user, change driver value to isDriver representation
	 * if any error occurs throw runtime exception
	 * affected tables: {User Table}
	 */
	public void changeDriverStatus(String email, boolean isDriver) {
		try {
			Query lookup = new Query(Criteria.where("email").is(email));
			mongoOperation.updateFirst(lookup, Update.update("driver", isDriver), Users.class);
		}catch(Exception e){
			throw new RuntimeException("Failed to change driver status: "+e.getLocalizedMessage());
		}
	}


	/**
	 *
	 * @param email
	 * @param seats
	 * TODO fill in this method
	 * find the user by email, update seat value with seats.
	 * affected tables: {User Table}
	 */
	public void updateSeats(String email,int seats){
		try{

		}catch(Exception e){
			throw new RuntimeException("Failed to update seats: "+e.getLocalizedMessage());
		}
	}


	/*

	User Methods that pull values---------------------------------------------------------------------------------------------------------------------------------------------------

	 */

	/**
	 *
	 * @param email
	 * @return
	 *
	 * isAdmin method, takes user's email and returns a
	 * boolean to tell if the account has admin priv or not find user, read isAdmin
	 * value, return isAdmin value if any error occurs throw runtime exception
	 * affected tables: {User Table}
	 */
	public boolean isAdmin(String email) {
		Query lookup = new Query(Criteria.where("email").is(email));
		try {
			Users user = mongoOperation.findOne(lookup, Users.class);
			return user.getAdminStatus();
		} catch (Exception e) {
			throw new RuntimeException("Error with isAdmin: "+e.getLocalizedMessage());
		}
	}

	/**
	 * TODO fill in method pls
	 * @param email
	 * @return
	 *
	 * use email to get me the id for the user entry
	 * (this should never happen but) if you cant find the user just return me null, I will check for it
	 */
	public String emailToID(String email){
		return null;
	}

	/**
	 * TODO fill in method pls
	 * @param email
	 * @return
	 *
	 * use email to find user and return isDriver var
	 */
	public boolean isDriver(String email){
		return false;
	}
	/*

	Rides Methods that create or delete rides---------------------------------------------------------------------------------------------------------------------------------------------------

	 */

	/**
	 *
	 * @param driverID
	 * @param startTime
	 * @param endTime
	 * @param busStopID
	 *
	 * TODO fill in method
	 * make a ride object with these values. the int values can be set to 0;
	 * Return the id of entry after its been added
	 */
	public String addRide(String driverID, String startTime,String endTime,String busStopID){

		return null;
	}

	/**
	 * TODO fill in method pls
	 * @param rideID
	 *
	 * find ride with that rideID
	 * Remove all passengers if any are there
	 * empty all queues of riders
	 * Change active value to false;
	 */
	public void deleteRide(String rideID){

	}

	/*

	Rides Methods that update ride values---------------------------------------------------------------------------------------------------------------------------------------------------

	 */

	/**
	 * TODO fill in method pls
	 * @param rideID
	 * @return
	 * Find the ride that they want to cancel
	 * go through active riders, if their start date is on the cancel date then push their startDate a week forward. (must check if start Date is passed end date, if so then remove that person and push person in queue up)
	 * return array of passengerIDs that lost their ride
	 */
	public String[] cancelRide(String rideID,String date){

		return null;
	}

	/*

	Rides Methods that pull values---------------------------------------------------------------------------------------------------------------------------------------------------

	 */

	/**
	 * TODO fill in method
	 * @param driverID
	 * @return
	 *
	 * search for all rides that matches driverID and isActive
	 * with these rides, regenerate completed entries by pulling info you need
	 * Return that list
	 */
	public List<Ride> getDriverRides(String driverID, boolean isActive){

		return null;
	}

	/**
	 *
	 * @param rideID
	 * @return
	 * TODO fill in method
	 * Search for ride with matching ride id
	 * return the length of pickupBusStopIDs
	 */
	public int numOfActivePickUps(String rideID){

		return 0;
	}

	/*

	BusStops Methods that create BusStops---------------------------------------------------------------------------------------------------------------------------------------------------

	 */

	/**
	 *
	 * @param location
	 * @param country
	 * @param state
	 * @param city
	 * @param street
	 * @param areaCode
	 * @param isDestination
	 * @return
	 * /TODO fill in method pls
	 * make a busStop entry and then return its id,
	 *
	 */
	public String addBusStop(String rideID,String location,String time,String country,String state,String city,String street,String areaCode,boolean isDestination){

		return null;
	}

	/*

	BusStops Methods that edit values---------------------------------------------------------------------------------------------------------------------------------------------------

	 */

	/**
	 *
	 * @param rideID
	 * @param busStopID
	 * TODO make method
	 * Find the busStop entry by id
	 * Then set rideID = to rideID
	 */
	public void linkRideAndBusStop(String busStopID, String rideID){

	}


	/*

	BusStop Methods that read values---------------------------------------------------------------------------------------------------------------------------------------------------

	 */


}
