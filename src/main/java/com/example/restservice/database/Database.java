package com.example.restservice.database;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
/**
database scheme
User Table{//TODO make this table
    Primary Key: int User ID
    String Email
    boolean/bit is_driver
    int seats //TODO add this column if table is already made
    boolean/bit is_admin
    char[2] grade
    int total_rides_taken
    int total_passengers-taken
    double distance_traveled
    double total_length_of_drives
}

Rides Table{//TODO make this table
    Primary Key: int Ride ID (cant be null)
    int Driver ID (linked to User ID in User Table) (cant be null)
    int DestinationBusStopID (linked to values in BusStop Table)
    DateTime StartDate (cant be null) (DateTime format: MM/dd/YYYY HH:mm:SS)
    DateTime EndDate (cant be null)
    int Completed (default is 0)
    int Cancelled (default is 0)
    int PickupBusStopID (repeat for 10 columns) (linked to values in BusStop Table)
    int PassengerID (linked to values in BusStop Table)] (repeat for 15 columns)
 }

 BusStop Table{//TODO make this table
    Primary Key: int BusStopID
    String locationName
    String country
    String state
    String city
    String street
    String areaCode
    Boolean Destination
    int PassengerID (repeat for 15 columns)

 }

 Passenger Table{//TODO make this table
    Primary Key: int PassengerID
    int RiderID (linked to User Table)
    int RideID (linked to Rides table)
    DateTime StartDate (DateTime format: MM/dd/YYYY)
    DateTime EndDate (DateTime format: MM/dd/YYYY)
    BusStopID pickupLocation (linked to values in BusStop Table)
 }
 */
@Service
public class Database {
    private static String JDBC_DRIVER;
    private static String DB_URL;
    private static String USER;
    private static String PASS;


    /**
     //TODO fill in method
    Requires autowire
     change the constructor as you need
     any creds should be stored in application.properties
     and called using @Value("${'value name'}")
     */
    @Autowired
    public Database(){

    }
    
    public MongoDatabase Connection() {
		MongoClient mongoClient = MongoClients.create("mongodb+srv://nmolina:OMYTXMcswUTHvdFd@cluster0-v76zg.mongodb.net/test?retryWrites=true&w=majority");
		MongoDatabase database = mongoClient.getDatabase("Capstone");
		return database;
	}


    /**
     //TODO fill in method
    updateGrade method, takes in a string to represent grade/type of user and email of user
    find user, replace grade.
     If any error occurs throw runtime exception
     affected tables: {User Table}
     */
    public void updateGrade(String email,String grade){

    }

    /**
     //TODO fill in method
    changeAdminStatus method, takes a user's email and a boolean that represents their new admin status
    True for being admin, false for being standard user.
     find user, change admin value to isAdmin representation
     if any error occurs throw runtime exception
     affected tables: {User Table}
     */
    public void changeAdminStatus(String email, boolean isAdmin){

    }

    /**
     //TODO fill in method
     changeDriverStatus method, takes a user's email and a boolean that represents their new driver status
     True for being driver, false for not a driver.
     find user, change driver value to isDriver representation
     if any error occurs throw runtime exception
     affected tables: {User Table}
     */
    public void changeDriverStatus(String email,boolean isDriver){

    }

    /**
     //TODO fill in method
    isAdmin method, takes user's email and returns a boolean to tell if the account has admin priv or not
     find user, read isAdmin value, return isAdmin value
     if any error occurs throw runtime exception
     affected tables: {User Table}
     */
    public boolean isAdmin(String email){
        return false;
    }
    /**
     //TODO fill in method
    Adduser method, takes in a string that is the users email and returns boolean
     if(email/user does not exist in db){
        add user to db
            with values:
            (pkey(auto increment),email,false,false,null,0,0,0,0)
        return false;
     }else{
        return true;
     }
     affected tables: {User Table}
     */
    public boolean addUser(String email){
        return false;
    }

    /**
    Closing connections and statements usually require a lot repetitive
     try catching, these methods handle all the try catching to reduce key strokes
     ----possibly not needed with mongedb.
     */
    private void closeConnection(Connection connection){
        if(connection!=null) {
            try {
                connection.close();
            } catch (SQLException e) {}
        }
    }
    private void closeConnection(Statement statement){
        if(statement!=null) {
            try {
                statement.close();
            } catch (SQLException e) {}
        }
    }


    /*
    If you need to test a non static method, test it in RestServiceApplication.java
     */

}
