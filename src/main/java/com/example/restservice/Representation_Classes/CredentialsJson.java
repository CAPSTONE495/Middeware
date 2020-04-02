package com.example.restservice.Representation_Classes;

import java.util.List;

public class CredentialsJson {
    private List installed;
    public List getInstalled(){
        return installed;
    }
    public void setInstalled(List installed){
        this.installed=installed;
    }
}
