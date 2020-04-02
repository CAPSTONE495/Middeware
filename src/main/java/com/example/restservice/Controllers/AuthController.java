package com.example.restservice.Controllers;

import com.example.restservice.Constants.Constants;
import com.example.restservice.Constants.Constants.PathConstants;
import com.example.restservice.Representation_Classes.TokinJson;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.web.bind.annotation.*;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;

import javax.annotation.PostConstruct;
import java.io.FileReader;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;

@RestController
public class AuthController {
    private String clientID;

    @PostConstruct
    public void initialize(){
        clientID=getClientID();
    }

    @RequestMapping(value=PathConstants.AUTHPATH+"/gettoken",method = RequestMethod.POST)
    public TokinJson authTokin(@RequestParam(value = "tokenID", defaultValue = "") String tokenID){

        if(tokenID.equals("")){
            throw new RuntimeException("No tokin ID");
        }else if(clientID.equals("")){
            throw new RuntimeException("No client ID. Server side issue.");
        }

        NetHttpTransport transport = new NetHttpTransport();
        GsonFactory jsonFactory = new GsonFactory();
        GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(transport, jsonFactory)
                .setAudience(Collections.singletonList(clientID))
                .build();
        try {
            GoogleIdToken token = verifier.verify(tokenID);
            if(token == null)
                throw new RuntimeException("invalid tokin");
            GoogleIdToken.Payload payload = token.getPayload();
            if(!payload.getEmailVerified())
                throw new RuntimeException("Unverified Email");
            if(!payload.getHostedDomain().equals(Constants.ACCEPTEDEMAILDOMAIN))
                throw new RuntimeException("No tokin ID");
        } catch (GeneralSecurityException e) {
            throw new RuntimeException("Account Security Issue: "+e.getLocalizedMessage());
        } catch (IOException e) {
            throw new RuntimeException("No tokin ID");
        }

        //TODO generate access tokin
        return null;
    }

    private static String getClientID(){
        String clientID = "";
        try {
            JSONObject jo = (JSONObject) new JSONParser().parse(new FileReader(Constants.PATHTOCREDENTIALS));
            clientID = (String) ((JSONObject) jo.get("installed")).get("client_id");
            System.out.println("d");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return clientID;
    }

    public static void main(String[] args){
        System.out.println("testing getClientID");
        System.out.println(getClientID());
    }

}
