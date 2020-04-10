package com.example.restservice.Controllers;

import com.example.restservice.Constants.Constants;
import com.example.restservice.Constants.Constants.PathConstants;
import com.example.restservice.Representation_Classes.ResponseJson;
import com.example.restservice.Representation_Classes.TokenJson;
import com.example.restservice.database.Database;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.xml.bind.DatatypeConverter;
import java.io.FileReader;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.NoSuchAlgorithmException;
import java.util.Collections;

@RestController
public class AuthController {
    private static final String clientID = getClientID();
    private static final boolean DOMAINBYPASS = false;
    @Autowired
    Database database;


    @RequestMapping(value=PathConstants.AUTHPATH+"/gettoken",method = RequestMethod.POST)
    public TokenJson authTokin(@RequestParam(value = "tokenID", defaultValue = "") String tokenID){

        //check if required info is present
        if(tokenID.equals("")){
            throw new RuntimeException("No tokin ID");
        }else if(clientID.equals("")){
            throw new RuntimeException("No client ID. Server side issue.");
        }



        String email;
        try {
            GoogleIdToken token = getUserTokin(tokenID);
            if(token == null)
                throw new RuntimeException("invalid tokin");
            GoogleIdToken.Payload payload = token.getPayload();
            if(!payload.getEmailVerified())
                throw new RuntimeException("Unverified Email");
            if(!payload.getHostedDomain().equals(Constants.ACCEPTEDEMAILDOMAIN)|| DOMAINBYPASS)
                throw new RuntimeException("Invalid user");
            email = payload.getEmail();
        } catch (GeneralSecurityException e) {
            throw new RuntimeException("Account Security Issue: "+e.getLocalizedMessage());
        } catch (IOException e) {
            throw new RuntimeException("No tokin ID");
        } catch (Exception e){
            throw new RuntimeException("failed to verify tokin: "+e.getLocalizedMessage());
        }

        String apiKey;
        try {
            apiKey = generateAPIKey();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("key generation failure, :server side issue");
        }

        Constants.APIKeyMapper.addAPIKey(email,apiKey);

        return new TokenJson(apiKey,database.addUser(email));
    }

    private static String getClientID(){
        String clientID = "";
        try {
            JSONObject jo = (JSONObject) new JSONParser().parse(new FileReader(Constants.PATHTOCREDENTIALS));
            clientID = (String) ((JSONObject) jo.get("installed")).get("client_id");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return clientID;
    }

    private static String generateAPIKey() throws NoSuchAlgorithmException {
        KeyGenerator keyGen = KeyGenerator.getInstance("AES");
        keyGen.init(256);
        SecretKey secretKey = keyGen.generateKey();
        byte[] encoded = secretKey.getEncoded();
        return DatatypeConverter.printHexBinary(encoded);
    }

    //each instance will be thread safe as no global value manipulation is occuring
    private static GoogleIdToken getUserTokin(String tokenID) throws GeneralSecurityException, IOException {
        GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(), new GsonFactory())
                .setAudience(Collections.singletonList(clientID))
                .build();
        return verifier.verify(tokenID);
    }

    private static String getEmail(String tokenID){
        try {
            return getUserTokin(tokenID).getPayload().getEmail();
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Object checker(String action, String apiKey,String tokenID,String[] nonNullValues){
        if(tokenID.equals("")||tokenID.equals(""))
            return new ResponseJson(action,false,"Missing an entry");
        for(String x:nonNullValues){
            if(x.equals(""))
                return new ResponseJson(action,false,"Missing an entry");
        }

        String email = getEmail(tokenID);
        if(email==null)
            return new ResponseJson("update grade",false,"unable to extract email");

        if(!Constants.APIKeyMapper.checkAPIKey(email,apiKey))
            return new ResponseJson("update grade",false,"invalid apiKey");

        return email;
    }

    public static void main(String[] args){
        System.out.println("testing getClientID");
    }

}
