package com.example.software2.Models;


import javafx.fxml.Initializable;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

/**
 * Model Customers class for interacting with the DB.
 */
public class Customers implements Initializable {

    private int Customer_ID;
    private String Customer_Name;
    private String Address;
    private String Postal_Code;
    private String Phone;
    private LocalDateTime Create_Date;
    private String Created_By;
    private LocalDateTime Last_Update;
    private String Last_Updated_By;

    private String division;
    private int division_id;

    private String country;

    /**
     * Constructor
     * @param id
     * @param customer_Name
     * @param address
     * @param postal_Code
     * @param division
     * @param phone
     * @param create_Date
     * @param created_By
     * @param last_Update
     * @param last_Updated_By
     * @param divison_id
     */
    public Customers(int id,String customer_Name, String address, String postal_Code,String division, String phone, LocalDateTime create_Date, String created_By, LocalDateTime last_Update, String last_Updated_By, int divison_id) {
        this.Customer_ID=id;
        Customer_Name = customer_Name;
        Address = address;
        Postal_Code = postal_Code;
        Phone = phone;
        Create_Date = create_Date;
        Created_By = created_By;
        Last_Update = last_Update;
        Last_Updated_By = last_Updated_By;
        this.division_id=divison_id;
        this.division=division;
    }

    /**
     * Getter for country
     * @return country
     */
    public String getCountry() {
        return country;
    }

    /**
     * Setter for Country
     * @param division_id
     */
    public void setCountry(int division_id) {

        if(division_id >=1 && division_id <=54){
            this.country="US";
        }
       else if(division_id >54 && division_id <=72){
            this.country="Canada";
        }
       else if(division_id >72 && division_id <=104){
            this.country="United Kingdom";
        }
        else{
            throw new IllegalArgumentException("Invalid ID "+ division_id );
        }
    }

    /**
     * Getter for division
     * @return division
     */
    public String getDivision() {
        return division;
    }

    /**
     * Setter for division
     * @param division
     */
    public void setDivision(String division) {
        this.division = division;
    }

    /**
     * Getter for divisionID
     * @return divisionID
     */
    public int getDivision_id() {
        return division_id;
    }

    /**
     * Setter for divisionId
     * @param division_id
     */
    public void setDivision_id(int division_id) {
        this.division_id = division_id;
    }

    /**
     * Getter for ContactID
     * @return contactID
     */
    public  int getCustomer_ID() {
        return Customer_ID;
    }

    /**
     * Setter for ContactID
     * @param customer_ID
     */
    public  void setCustomer_ID(int customer_ID) {
        Customer_ID = customer_ID;
    }

    /**
     * Getter for Contact Name
     * @return ContactName
     */
    public String getCustomer_Name() {
        return Customer_Name;
    }

    /**
     * Setter for CustomerName
     * @param customer_Name
     */
    public void setCustomer_Name(String customer_Name) {
        Customer_Name = customer_Name;
    }

    /**
     * Getter for Address
     * @return Address
     */
    public String getAddress() {
        return Address;
    }

    /**
     * Setter for Address
     * @param address
     */
    public void setAddress(String address) {
        Address = address;
    }

    /**
     * Getter for postalCode
     * @return postalCode
     */
    public String getPostal_Code() {
        return Postal_Code;
    }

    /**
     * Setter for postalCode
     * @param postal_Code
     */
    public void setPostal_Code(String postal_Code) {
        Postal_Code = postal_Code;
    }

    /**
     * Getter for Phone
     * @return phone
     */
    public String getPhone() {
        return Phone;
    }

    /**
     * Setter for phone
     * @param phone
     */
    public void setPhone(String phone) {
        Phone = phone;
    }

    /**
     * Getter for createDate
     * @return createDate
     */
    public LocalDateTime getCreate_Date() {
        return Create_Date;
    }

    /**
     * Setter for createDate
     * @param create_Date
     */
    public void setCreate_Date(LocalDateTime create_Date) {
        Create_Date = create_Date;
    }

    /**
     * Getter for createdBy
     * @return createdBy
     */
    public String getCreated_By() {
        return Created_By;
    }

    /**
     * Setter for createdBy
     * @param created_By
     */
    public void setCreated_By(String created_By) {
        Created_By = created_By;
    }

    /**
     * Getter for lastUpdate
     * @return lastUpdate
     */
    public LocalDateTime getLast_Update() {
        return Last_Update;
    }

    /**
     * Setter for lastUpdate
     * @param last_Update
     */
    public void setLast_Update(LocalDateTime last_Update) {
        Last_Update = last_Update;
    }

    /**
     * Getter for lastUpdatedBy
     * @return lastUpdatedBy
     */
    public String getLast_Updated_By() {
        return Last_Updated_By;
    }

    /**
     * Setter for last updated by
     * @param last_Updated_By
     */
    public void setLast_Updated_By(String last_Updated_By) {
        Last_Updated_By = last_Updated_By;
    }

    /**
     * To string
     * @return
     */
    @Override
    public String toString() {
        return "Customers{" +
                "Customer_ID=" + Customer_ID +
                ", Customer_Name='" + Customer_Name + '\'' +
                ", Address='" + Address + '\'' +
                ", Postal_Code='" + Postal_Code + '\'' +
                ", Phone='" + Phone + '\'' +
                ", Create_Date=" + Create_Date +
                ", Created_By='" + Created_By + '\'' +
                ", Last_Update=" + Last_Update +
                ", Last_Updated_By='" + Last_Updated_By + '\'' +
                ", division='" + division + '\'' +
                ", division_id=" + division_id +
                ", country='" + country + '\'' +
                '}';
    }

    /**
     * Initialize method unused
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
