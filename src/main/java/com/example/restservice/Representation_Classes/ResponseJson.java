package com.example.restservice.Representation_Classes;

public class ResponseJson {
    private String type = "ResponseJson";
    private String action;
    private boolean success;
    private String result;

    public ResponseJson(String action, boolean success, String result){
        this.action=action;this.result=result;this.success=success;
    }

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
}
