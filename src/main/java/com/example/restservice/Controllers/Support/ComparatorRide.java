package com.example.restservice.Controllers.Support;

import com.example.restservice.Constants.Constants;
import com.example.restservice.database.Rides;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.Comparator;

public class ComparatorRide implements Comparator<Rides> {
    DateTimeFormatter formatter = DateTimeFormat.forPattern(Constants.DATEFORMAT);
    @Override
    public int compare(Rides rides, Rides t1) {
        DateTime r1 = new DateTime(formatter.parseDateTime(rides.getStartDate()).getMillis());
        DateTime r2 = new DateTime(formatter.parseDateTime(t1.getStartDate()).getMillis());
        if(r1.isBefore(r2)){
            return 1;
        }else if(r1.isAfter(r2)){
            return -1;
        }else{
            return 0;
        }
    }
}