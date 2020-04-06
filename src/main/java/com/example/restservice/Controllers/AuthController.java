package com.example.restservice.Controllers;

import com.example.restservice.Constants.Constants;
import com.example.restservice.Constants.Constants.PathConstants;
import com.example.restservice.Representation_Classes.TokenJson;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
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
    private static final String clientID;
    private static final boolean DOMAINBYPASS = false;

    /*@PostConstruct
    public void initialize(){
        if(clientID==null)
            clientID=getClientID();
    }*/

    static{
        clientID = getClientID();
    }

    @RequestMapping(value=PathConstants.AUTHPATH+"/gettoken",method = RequestMethod.POST)
    public static TokenJson authTokin(@RequestParam(value = "tokenID", defaultValue = "") String tokenID){

        //check if required info is present
        if(tokenID.equals("")){
            throw new RuntimeException("No tokin ID");
        }else if(clientID.equals("")){
            throw new RuntimeException("No client ID. Server side issue.");
        }

        GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(), new GsonFactory())
                .setAudience(Collections.singletonList(clientID))
                .build();

        String email;
        try {
            GoogleIdToken token = verifier.verify(tokenID);
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

        //TODO add a check to make sure email/user is in database

        return new TokenJson(apiKey);
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

    public static void main(String[] args){
        System.out.println("testing getClientID");
        System.out.println(authTokin("dafdas"));
    }

}
