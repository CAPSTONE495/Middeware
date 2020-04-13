package com.example.restservice.Constants;


import org.joda.time.DateTime;
import org.joda.time.Seconds;

import java.io.File;
import java.util.concurrent.ConcurrentHashMap;

public class Constants {
    public static final int KEYVALIDITY = 60;
    public static final int MAXSEATS = 15;
    public static final int MAXBUSSTOPS = 10;

    public static final String ACCEPTEDEMAILDOMAIN = "oswego.edu";
    public static final String PATHTOCREDENTIALS = System.getProperty("user.dir")+File.separator+"src"+File.separator+"main"+File.separator+"resources"+File.separator+"credentials.json";

    public static class PathConstants {
        public static final String DRIVERPATH = "/Driver";
        public static final String PASSENGERPATH = "/Passenger";
        public static final String ADMINPATH = "/Admin";
        public static final String PROFILEPATH = "/Profile";
        public static final String AUTHPATH = "/Auth";
        public static final String HISTORYPATH = "/History";
        public static final String ERROR = "/error";
    }

    public static class APIKeyMapper{
        private final static ConcurrentHashMap<String,DateTime> concurrentHashMap = new ConcurrentHashMap();
        private APIKeyMapper(){}

        public static void addAPIKey(String email,String apiKey){
            concurrentHashMap.put(email+apiKey,new DateTime().plusMinutes(KEYVALIDITY));
        }

        public static boolean checkAPIKey(String email,String apiKey){
            DateTime dt = new DateTime();
            DateTime apiTime = concurrentHashMap.get(email+apiKey);
            if(apiTime==null){
                return false;
            }else if(Seconds.secondsBetween(dt,apiTime).isLessThan(Seconds.ZERO)){
                return false;
            }else{
                return true;
            }
        }
    }

}
