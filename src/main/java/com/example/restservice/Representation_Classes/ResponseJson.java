package com.example.restservice.Representation_Classes;

import com.example.restservice.database.Rides;

import java.util.List;

public class ResponseJson {


    private String type = "ResponseJson";
    private String action;
    private boolean success;
    private String result;
    private List<Rides> rides;

    public ResponseJson(String action, boolean success, String result){
        this.action=action;this.result=result;this.success=success;rides=null;
    }
    public ResponseJson(String action, boolean success, String result,List<Rides> rides){
        this.action=action;this.result=result;this.success=success;this.rides=rides;
    }

    public String getType() { return type; }

    public void setType(String type) {this.type = type; }

    public boolean isSuccess() {
        return success;
    }

    public String getAction() {
        return action;
    }

    public String getResult() {
        return result;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public List<Rides> getRide() {
        return rides;
    }

    public void setRide(List<Rides> ride) {
        this.rides = ride;
    }
}
