package com.example.software2.Models;

/**
 * Contact Model Class for creating contacts and interacting with the DB.
 */
public class Contacts {

    private int Contact_ID;

    private String Contact_Name;

    private  String Email;

    /**
     * Constructor
     * @param contact_ID
     * @param contact_Name
     * @param email
     */
    public Contacts(int contact_ID, String contact_Name, String email) {
        Contact_ID = contact_ID;
        Contact_Name = contact_Name;
        Email = email;
    }

    /**
     * Getter for ContactID
     * @return ContactID
     */
    public int getContact_ID() {
        return Contact_ID;
    }

    /**
     * Setter for ContactID
     * @param contact_ID
     */
    public void setContact_ID(int contact_ID) {
        Contact_ID = contact_ID;
    }

    /**
     * Getter for contactName
     * @return Contact_Name
     */
    public String getContact_Name() {
        return Contact_Name;
    }

    /**
     * Setter for contact_name
     * @param contact_Name
     */
    public void setContact_Name(String contact_Name) {
        Contact_Name = contact_Name;
    }

    /**
     * Getter for email
     * @return email
     */
    public String getEmail() {
        return Email;
    }

    /**
     * Setter for email
     * @param email
     */
    public void setEmail(String email) {
        Email = email;
    }

    /**
     * To string
     * @return
     */
    @Override
    public String toString() {
        return   Contact_Name + '\'';

    }
}
