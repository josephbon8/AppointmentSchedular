package com.example.software2.Models;

/**
 * Class used in the reports page to populate the appointment table
 */
public class Appointment {

    private int month;

    private String type;
    private int total;

    /**
     * Contructor
     * @param month
     * @param type
     * @param total
     */
    public Appointment(int month, String type, int total) {
        this.month = month;
        this.type = type;
        this.total = total;
    }

    /**
     * Getter for month
     * @return month
     */
    public int getMonth() {
        return month;
    }

    /**
     * Setter for month
     * @param month
     */
    public void setMonth(int month) {
        this.month = month;
    }

    /**
     * Getter for type
     * @return type
     */
    public String getType() {
        return type;
    }

    /**
     * Setter for type
     * @param type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Getter for total
     * @return total
     */
    public int getTotal() {
        return total;
    }

    /**
     * Setter for total
     * @param total
     */
    public void setTotal(int total) {
        this.total = total;
    }
}
