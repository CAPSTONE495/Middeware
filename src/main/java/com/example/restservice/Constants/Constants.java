package com.example.restservice.Constants;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Constants {

    public enum ControllerConstants {
        DRIVERPATH("/Driver"), PASSENGERPATH("/Passenger"),
        ADMINPATH("/Admin"), PROFILEPATH("/Profile"),
        AUTHPATH("/Auth"),HISTORYPATH("/History");

        private String ControllerConstants;

        private ControllerConstants(String constant) {
            this.ControllerConstants = constant;
        }
    }

    public static class DatabaseConstants {
        private static final File credentials = new File("Credentials.xml").getAbsoluteFile();

        private enum xmlConstants{
            //TODO figure out format when we start db
            DATABASE("Database",
                    new String[] {"",
                            ""}
                            );

            private String tag;
            private String[] allAttributes;
            private xmlConstants(String tag,String[]allAttributes){
                this.tag=tag;
                this.allAttributes=allAttributes;
            }
        }
        public boolean loadDatabaseCredials() throws ParserConfigurationException, IOException, SAXException {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(credentials);
            doc.getDocumentElement().normalize();

            xmlConstants[] listOfCreds = xmlConstants.values();
            ArrayList<NodeList> listOfNodes = new ArrayList<>();
            for(xmlConstants c : listOfCreds){
                listOfNodes.add(doc.getElementsByTagName(c.tag));
            }

            return false;
        }
    }

    //TODO delete this at the end, only used to test local code
    public static void main(String[] args){

    }
}
