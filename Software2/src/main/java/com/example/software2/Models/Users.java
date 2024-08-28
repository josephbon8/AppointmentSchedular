package com.example.software2.Models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Users model class for interacting with DB.
 */
public class Users {
            private int id;
            private String User_Name;
            private String Password;

            private ObservableList<Users> allUsers= FXCollections.observableArrayList();

    /**
     * Constructor
     * @param id
     * @param User_Name
     * @param password
     */
            public Users(int id,String User_Name, String password){
                this.id=id;
                this.User_Name=User_Name;
                this.Password=password;
            }

    /**
     * Getter for userID
     * @return
     */
    public int getId() {
        return id;
    }

    /**
     * Setter for userID
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Getter for userName
     * @return userName
     */
    public String getUser_Name() {
        return User_Name;
    }

    /**
     * Setter for userName
     * @param user_Name
     */
    public void setUser_Name(String user_Name) {
        User_Name = user_Name;
    }

    /**
     * Getter for password
     * @return password
     */
    public String getPassword() {
        return Password;
    }

    /**
     * Setter for password
     * @param password
     */
    public void setPassword(String password) {
        Password = password;
    }

    /**
     * ToString
     * @return
     */
    @Override
    public String toString() {
        return "Users{" +
                "id=" + id +
                ", User_Name='" + User_Name + '\'' +
                ", Password='" + Password + '\'' +
                '}';
    }
}
