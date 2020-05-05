package com.example.restservice.database;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.util.ArrayList;
import java.util.List;
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
	public boolean addUser(String email,String first,String last) {
		try {
			Users user = new Users(first, last, email);
			mongoOperation.save(user);
			return true;
		} catch (Exception e) {
			throw new RuntimeException("Error with addUser: "+e.getLocalizedMessage());
		}
	}

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
	public String getUserInfo(String email){
		try {
			Query lookup = new Query(Criteria.where("email").is(email));
			Users person = mongoOperation.findOne(lookup, Users.class);
			if(person==null)
				return "no driver found";
			return person.toString();
			
		}catch(Exception e) {
			throw new RuntimeException("Error with addName: "+e.getLocalizedMessage()+"  "+email);
		}
	}

	/*

	User methods that edit values---------------------------------------------------------------------------------------------------------------------------------------------------------

	 */
	public void updateGrade(String email, String grade) {
		try {
			Query lookup = new Query(Criteria.where("email").is(email));
			mongoOperation.updateFirst(lookup, Update.update("grade", grade), Users.class);
		}catch (Exception e){
			throw new RuntimeException("Failed to Update grade: "+e.getLocalizedMessage());
		}
	}
	public void changeAdminStatus(String email, boolean isAdmin) {
		try {
			Query lookup = new Query(Criteria.where("email").is(email));
			mongoOperation.updateFirst(lookup, Update.update("admin", isAdmin), Users.class);
		}catch(Exception e){
			throw new RuntimeException("failed to change admin status: "+e.getLocalizedMessage());
		}
	}
	public void changeDriverStatus(String email, boolean isDriver) {
		try {
			Query lookup = new Query(Criteria.where("email").is(email));
			mongoOperation.updateFirst(lookup, Update.update("driver", isDriver), Users.class);
		}catch(Exception e){
			throw new RuntimeException("Failed to change driver status: "+e.getLocalizedMessage());
		}
	}
	public void updateSeats(String email,int seats){
		try{
			Query lookup = new Query(Criteria.where("email").is(email));
			mongoOperation.updateFirst(lookup, Update.update("seats", seats), Users.class);
		}catch(Exception e){
			throw new RuntimeException("Failed to update seats: "+e.getLocalizedMessage());
		}
	}
	/*

	User Methods that pull values---------------------------------------------------------------------------------------------------------------------------------------------------

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
	public String emailToID(String email){
		try {
			Query lookup = new Query(Criteria.where("email").is(email));
			Users person = mongoOperation.findOne(lookup, Users.class);
			return person.getId();
		}catch(Exception e){
			throw new RuntimeException("Failed to change driver status: "+e.getLocalizedMessage());
		}
	}
	public boolean isDriver(String email){
		try {
			Query lookup = new Query(Criteria.where("email").is(email));
			Users person = mongoOperation.findOne(lookup, Users.class);
			if(person.getDriverStatus()) {
				return person.getDriverStatus();
			}else {
			return false;
			}
		}catch(Exception e){
			throw new RuntimeException("Failed to change driver status: "+e.getLocalizedMessage());
		}
	}
	/*

	Rides Methods that create or delete rides---------------------------------------------------------------------------------------------------------------------------------------------------

	 */
	public String addRide(Users driverID, String startDate,String endDate, ArrayList<BusStops> destination){
		try {
		Rides ride = new Rides(driverID, startDate, endDate, destination);
		mongoOperation.save(ride);
		return ride.getId();
		}catch(Exception e){
			throw new RuntimeException("Failed to change driver status: "+e.getLocalizedMessage());
		}
	}
	public void deleteRide(String rideID){
		try {
			Query lookup = new Query(Criteria.where("_id").is(rideID));
			Rides ride = mongoOperation.findOne(lookup, Rides.class);
			ArrayList<Users> passengers = ride.getPassengers();
			for(Users user : passengers) {
				Query person = new Query(Criteria.where("_id").is(user.getId()));
				mongoOperation.updateFirst(person,Update.update("pickupID", null), Rides.class);
			}
			mongoOperation.findAndRemove(lookup, Rides.class);
		}catch(Exception e){
			throw new RuntimeException("Failed to change driver status: "+e.getLocalizedMessage());
		}
	}

	/*

	Rides Methods that update ride values---------------------------------------------------------------------------------------------------------------------------------------------------

	 */

	/*

	Rides Methods that pull values---------------------------------------------------------------------------------------------------------------------------------------------------

	 */
	public List<Rides> getDriverRides(Users driver){
		try {
		Query lookup = new Query(Criteria.where("driverID").is(driver));
		return mongoOperation.find(lookup, Rides.class);
		}catch(Exception e) {
			throw new RuntimeException("Failed to change driver status: "+e.getLocalizedMessage());
		}
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
		try {
			Query lookup = new Query(Criteria.where("_id").is(rideID));
			Rides ride = mongoOperation.findOne(lookup, Rides.class);
			return ride.getPickUpBusStop().size();
			}catch(Exception e) {
				throw new RuntimeException("Failed to change driver status: "+e.getLocalizedMessage());
			}
	}

	/**
	 * TODO fill in method
	 * @param locationName
	 * @param time
	 * @param country
	 * @param state
	 * @param city
	 * @param address
	 * @param areaCode
	 * @return
	 *
	 * Go through all busStops, pull everyone that matches the criteria (you can make the time in a range of +-15 min from whatever time is given)
	 * Go through that list and remove any busStops that are not linked to an active Ride or if ride endTime is past Current Time.
	 * Go through that list and check if there is an open seat for the user.
	 * (Note: not enough time now, but its recommenced to make this shit parallel with a fork join of the org list because after enough uses that list is gonna be hug)
	 *
	 
	public List<Rides> getSearchedBusStops(String locationName, String time, String country, String state, String city, String address, String areaCode){

		return null;
	}
*/
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
	public String addBusStop(String rideID,String location,String time,String country,String state,String city,String street,String areaCode,boolean isDestination){

		return null;
	}
	*/

	public boolean addPassenger(String riderID, Users pass, String pickupID){
		try {
			Query ride = new Query(Criteria.where("_id").is(riderID));
			Rides new_ride = mongoOperation.findOne(ride, Rides.class);
			ArrayList<Users> passengers = new_ride.getPassengers();
			passengers.add(pass);
			pass.setPickUp(pickupID);
			mongoOperation.save(new_ride);
			mongoOperation.save(pass);
			
		}catch(Exception e) {
			throw new RuntimeException("Failed to change driver status: "+e.getLocalizedMessage());
		}

		return false;
	}
	public void removePassenger(String riderID, Users pass, String pickupID){
		try {
		Query ride = new Query(Criteria.where("_id").is(riderID));
		Rides new_ride = mongoOperation.findOne(ride, Rides.class);
		ArrayList<Users> passengers = new_ride.getPassengers();
		passengers.remove(pass);
		new_ride.setPassengers(passengers);
		pass.setPickUp(pickupID);
		mongoOperation.save(new_ride);
		mongoOperation.save(pass);
		}catch(Exception e){
			throw new RuntimeException("Failed to change driver status: "+e.getLocalizedMessage());
		}

	}

}
