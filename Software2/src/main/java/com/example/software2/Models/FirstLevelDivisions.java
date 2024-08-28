package com.example.software2.Models;

import java.time.LocalDateTime;

/**
 * first_level_divisions model class for interacting with DB.
 */
public class FirstLevelDivisions {

    private int Division_ID;
    private String Division;
    private LocalDateTime Create_Date;
    private String Created_By;
    private LocalDateTime Last_Update;
    private String Last_Updated_By;
    private int Country_ID;

    /**
     * Constructor
     * @param division_ID
     * @param division
     * @param create_Date
     * @param created_By
     * @param last_Update
     * @param last_Updated_By
     * @param country_ID
     */
    public FirstLevelDivisions(int division_ID, String division, LocalDateTime create_Date, String created_By, LocalDateTime last_Update, String last_Updated_By, int country_ID) {
        Division_ID = division_ID;
        Division = division;
        Create_Date = create_Date;
        Created_By = created_By;
        Last_Update = last_Update;
        Last_Updated_By = last_Updated_By;
        Country_ID = country_ID;
    }
}
