package com.example.restservice.Constants;


import java.io.File;

public class Constants {
    public static String ACCEPTEDEMAILDOMAIN = "oswego.edu";
    public static String PATHTOCREDENTIALS = System.getProperty("user.dir")+File.separator+"src"+File.separator+"main"+File.separator+"resources"+File.separator+"credentials.json";
    public static class PathConstants {
        public static final String DRIVERPATH = "/Driver";
        public static final String PASSENGERPATH = "/Passenger";
        public static final String ADMINPATH = "/Admin";
        public static final String PROFILEPATH = "/Profile";
        public static final String AUTHPATH = "/Auth";
        public static final String HISTORYPATH = "/History";
        public static final String ERROR = "/error";
    }


    public static void main(String[] args){

    }
}
