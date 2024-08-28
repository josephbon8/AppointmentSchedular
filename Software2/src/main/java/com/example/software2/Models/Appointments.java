package com.example.software2.Models;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

/***
 * Model class for appointment objects representing the appointments table in the DB
 */
public class Appointments {
     private int id;
    private static int Appointment_ID=3;
    private String Title;
    private String Description;
    private String Location;
    private String Type;
    private LocalDateTime Start;

    private LocalDateTime StartInLocalTime;
    private LocalDateTime End;
    private LocalDateTime Create_Date;
    private String Created_By;
    private LocalDateTime Last_Update;
    private String Last_Updated_By;

    private int customer_id;

    private int user_id;

    private int contact_id;

    private String contactName;

    /**
     * Constructor
     * @param id
     * @param title
     * @param description
     * @param location
     * @param type
     * @param start
     * @param end
     * @param create_Date
     * @param created_By
     * @param last_Update
     * @param last_Updated_By
     * @param customer_id
     * @param user_id
     * @param contact_id
     */
    public Appointments(int id, String title, String description, String location, String type, LocalDateTime start, LocalDateTime end, LocalDateTime create_Date, String created_By, LocalDateTime last_Update, String last_Updated_By,int customer_id,int user_id, int contact_id) {
       this.id=id;
        Appointment_ID++;
        Title = title;
        Description = description;
        Location = location;
        Type = type;
        Start = start;
        End = end;
        Create_Date = create_Date;
        Created_By = created_By;
        Last_Update = last_Update;
        Last_Updated_By = last_Updated_By;
        this.customer_id=customer_id;
        this.user_id=user_id;
        this.contact_id=contact_id;
    }

    /**
     * Overloaded Constructor
     * @param appointment_ID
     * @param title
     * @param type
     * @param desc
     * @param start
     * @param end
     * @param customer_id
     */
   public Appointments(int appointment_ID, String title, String type, String desc,LocalDateTime start,LocalDateTime end, int customer_id){
        this.id=appointment_ID;
        this.Title=title;
        this.Type=type;
        this.Description=desc;
        this.customer_id=customer_id;
        this.Start=start;
        this.End=end;
   }

    /**
     * Overloaded Constructor
     * @param localDateTime
     * @param id
     */
   public Appointments(LocalDateTime localDateTime, int id){
        this.Start=localDateTime;
        this.id=id;
   }

    /**
     * Getter for contactName
     * @return contactname
     */
    public String getContactName() {
        return contactName;
    }

    /**
     * Setter for contact name
     * @param contactName
     */
    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    /**
     * Getter for appointment ID
     * @return id
     */
    public int getId() {
        return id;
    }

    /**
     * Setter for app Id
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Getter for customer id
     * @return customer id
     */
    public int getCustomer_id() {
        return customer_id;
    }

    /**
     * Setter for customer id
     * @param customer_id
     */
    public void setCustomer_id(int customer_id) {
        this.customer_id = customer_id;
    }

    /**
     * Getter for userID
     * @return userID
     */
    public int getUser_id() {
        return user_id;
    }

    /**
     * Setter for user ID
     * @param user_id
     */
    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    /**
     * Getter for contact ID
     * @return contact ID
     */
    public int getContact_id() {
        return contact_id;
    }

    /**
     * Setter for contactID
     * @param contact_id
     */
    public void setContact_id(int contact_id) {
        this.contact_id = contact_id;
    }


    /**
     * Getter for title
     * @return title
     */
    public String getTitle() {
        return Title;
    }

    /**
     * Setter for title
     * @param title
     */
    public void setTitle(String title) {
        Title = title;
    }

    /**
     * Getter for Description
     * @return desc
     */
    public String getDescription() {
        return Description;
    }

    /**
     * Setter for Description
     * @param description
     */
    public void setDescription(String description) {
        Description = description;
    }

    /**
     * Getter for location
     * @return location
     */
    public String getLocation() {
        return Location;
    }

    /**
     * Setter for location
     * @param location
     */
    public void setLocation(String location) {
        Location = location;
    }

    /**
     * Getter for type
     * @return type
     */
    public String getType() {
        return Type;
    }

    /**
     * Setter for type
     * @param type
     */
    public void setType(String type) {
        Type = type;
    }

    /**
     * Getter for Start
     * @return LocalDateTime
     */
    public LocalDateTime getStart() {

        return Start;
    }

    /**
     * Converts from utc to user local time
     *
     * @return Localdatetime
     */
    public LocalDateTime getStartInLocalTime() {
        Timestamp utcTimestamp = Timestamp.valueOf(this.Start);
        Instant instant = utcTimestamp.toInstant();
        ZoneId zoneId = ZoneId.systemDefault();
        StartInLocalTime=instant.atZone(zoneId).toLocalDateTime();
        return instant.atZone(zoneId).toLocalDateTime();
    }

    /**
     * Setter for Start time
     * @param start
     */
    public void setStart(LocalDateTime start) {
        Timestamp utcTimestamp= Timestamp.valueOf(start);
        Instant instant=utcTimestamp.toInstant();
        ZoneId zoneId= ZoneId.systemDefault();
        LocalDateTime localStart=instant.atZone(zoneId).toLocalDateTime();
        Start = start;
    }

    /**
     * Getter for app end time
     * @return end
     */
    public LocalDateTime getEnd() {
        return End;
    }

    /**
     * Setter for app end time
     * @param end
     */
    public void setEnd(LocalDateTime end) {
        End = end;
    }

    /**
     * Getter for CreateDate
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
     * Getter for created_by
     * @return Created_By
     */
    public String getCreated_By() {
        return Created_By;
    }

    /**
     * Setter for created by
     * @param created_By
     */
    public void setCreated_By(String created_By) {
        Created_By = created_By;
    }

    /**
     * Getter for lastUpdate
     * @return LastUpdate
     */
    public LocalDateTime getLast_Update() {
        return Last_Update;
    }

    /**
     * Setter for LastUpdate
     * @param last_Update
     */
    public void setLast_Update(LocalDateTime last_Update) {
        Last_Update = last_Update;
    }

    /***
     * Getter for lastUpdatedBy
     * @returnlastUpdatedBy
     */
    public String getLast_Updated_By() {
        return Last_Updated_By;
    }

    /**
     * Setter for lastUpdatedBy
     * @param last_Updated_By
     */
    public void setLast_Updated_By(String last_Updated_By) {
        Last_Updated_By = last_Updated_By;
    }

    /**
     * To String method
     * @return
     */
    @Override
    public String toString() {
        return "Appointments{" +
                "Title='" + Title + '\'' +
                ", Description='" + Description + '\'' +
                ", Location='" + Location + '\'' +
                ", Type='" + Type + '\'' +
                ", Start=" + Start +
                ", End=" + End +
                ", Create_Date=" + Create_Date +
                ", Created_By='" + Created_By + '\'' +
                ", Last_Update=" + Last_Update +
                ", Last_Updated_By='" + Last_Updated_By + '\'' +
                ", customer_id=" + customer_id +
                ", user_id=" + user_id +
                ", contact_id=" + contact_id +
                '}';
    }
}
