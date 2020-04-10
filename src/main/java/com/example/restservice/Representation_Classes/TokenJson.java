package com.example.restservice.Representation_Classes;

public class TokenJson {
    private String type = "TokenJson";
    private String key;
    private boolean newUser;
    public TokenJson(String key,boolean newUser){
        this.key=key; this.newUser=newUser;
    }

    public String getKey() {
        return key;
    }

    public boolean isNewUser() { return newUser; }
}
