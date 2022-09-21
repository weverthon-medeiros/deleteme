package com.aylien.secret.santa.service;

/*
* This File is part of an Application for managing Secret Santa games. 
*
* The Service connects to a DB which has a list of names stores in a table.
* From this list, it takes all the names and pairs them up, so that each person
* is a Secret Santa for some other person in the list.
*/

import java.sql.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TeammatesServiceImpl {

    final String MY_URL = "jdbc:mysql://localhost:3306/santa";
    final String USER = "root";
    final String PASSWORD = "password123";

    // DB connection
    Connection conn = null;

    /* 
     * getSecretSantaPairing produces and returns a list of paired up Secret Santa participants
     *
     * Example output:
     * List<String>(
     *  "Audrey is Secret Santa for Russel", 
     *  "Eddie is Secret Santa for Margo", 
     *  "Russel is Secret Santa for Eddie",
     *  "Margo is Secret Santa for Audrey")
     */
    public List<String> getSecretSantaPairing(int year) {

        // List that will hold all the names of Secret Santa participants
        List<String> names = new ArrayList<>();

        try {

            //Open connection to DB
            conn = DriverManager.getConnection(MY_URL, USER, PASSWORD);

            //Get all Names of the participants in the Secret Santa game from the DB
            System.out.println("retrieving pairs");
            Statement st = conn.createStatement();
            String query = "SELECT first_name, last_name from teammates where year_joined <= " + year;
            ResultSet rs = st.executeQuery(query);

            //Write all the names into the local "names" and the global "allNames" list
            while(rs.next()){
                names.add(rs.getString("last_name") + "," + rs.getString("first_name"));
            }

            //Close Connection to DB
            st.close();
            conn.close();

        } catch (SQLException e) {
            return null;
        }

        return getPairings(names);

    }

    /*
     * getPairings assembles the pairings for all Secret Santa participants
     * using random numbers and removing a name once picked
     */
    private List<String> getPairings(List<String> names){

        // every participant will be a santa for someone and a recipient
        // so we create two lists with all the names
        List<String> santas = new ArrayList<>(names);
        List<String> recipients = new ArrayList<>(names);

        // List of Secret Santa participant pairs (i.e. all pairs of "santa" and "gift recipient")
        List<String> pairings = new ArrayList<>();

        while (santas.size() > 1) {

            Random rn = new Random();

            //Draw a random person from the list of possible Santas
            //then remove them from the list
            int santaIndex = rn.nextInt(santas.size());
            String santa = santas.get(santaIndex);
            santas.remove(santaIndex);

            //Draw a random person from the list of recipients
            //then remove them from the list.
            int recipientIndex = rn.nextInt(recipients.size());
            String recipient = recipients.get(recipientIndex);
            recipients.remove(recipientIndex);

            //Add the new pair to the list of pairings that is to be returned
            pairings.add(santa + " is Secret Santa for " + recipient);

        }

        return pairings;
    }

}
