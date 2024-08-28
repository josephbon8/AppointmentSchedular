package com.example.software2.helper;

import javafx.scene.control.Alert;

import java.util.Locale;
import java.util.ResourceBundle;

/***
 * Alert class used in the Login Controller for incorrect logINs
 */
public class LoginAlert extends Alert {

    private String title;
    private String message;

    /***
     * Constructor to initialize Alert varaibles
     * @param title
     * @param message
     * @param rb
     */
    public LoginAlert(String title, String message, ResourceBundle rb){
        super(AlertType.ERROR);
        this.message=message;
        this.title=title;

        Locale locale= Locale.getDefault();


    setTitle(rb.getString(title));
    setHeaderText(rb.getString(title));
    setContentText(rb.getString(message));
    }




}
