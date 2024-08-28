package com.example.software2.DAO;

import com.example.software2.Models.Appointments;
import com.example.software2.helper.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

/***
 * Main DAO used for the appointments table.
 */
public class AppointmentsDAO {

    /***
     * Used to get all appointments within the DB using JDBC.
     * Converts all UTC times into the users ZoneID Timezone
     * @return
     */
    public ObservableList<Appointments> getAllAppointments() {
        ObservableList<Appointments> appointmentsList = FXCollections.observableArrayList();
        String query = "SELECT Appointment_ID,Title,Description,Location,Type,Start,End,Create_Date,Created_By," +
                "Last_Update,Last_Updated_By,Customer_ID, User_ID,Contact_ID FROM appointments";

        try (PreparedStatement ps = JDBC.connection.prepareStatement(query);
             ResultSet resultSet = ps.executeQuery();) {

            while (resultSet.next()) {
                int id = resultSet.getInt("Appointment_ID");
                String title = resultSet.getString("Title");
                String desc = resultSet.getString("Description");
                String location = resultSet.getString("Location");
                String Type = resultSet.getString("Type");
                Timestamp Start =resultSet.getTimestamp("Start");

               ZoneId defaultZone= ZoneId.systemDefault();

                Instant instant = Start.toInstant();
                // Specify the target time zone (replace with your desired time zone)
                ZoneId targetTimeZone = ZoneId.of(String.valueOf(ZoneId.systemDefault()));
                // Convert Instant to LocalDateTime in the target time zone
                //LocalDateTime localDateTime = instant.atZone(targetTimeZone).toLocalDateTime();

                ZonedDateTime ftime=ZonedDateTime.ofInstant(instant,defaultZone);
                LocalDateTime atime=ftime.toLocalDateTime();


                ZonedDateTime time= ZonedDateTime.of(Start.toLocalDateTime(),ZoneId.systemDefault());
                ZonedDateTime startTime;
                System.out.println(Start);
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                //LocalDateTime startLocalDateTime = LocalDateTime.parse(Start, formatter);
                Timestamp end=  resultSet.getTimestamp("End");
                //String end = resultSet.getString("End");
                //LocalDateTime endLocalDateTime = LocalDateTime.parse(end, formatter);

                String Create_Date = String.valueOf(resultSet.getString("Create_Date"));
                LocalDateTime createLocalDateTime = LocalDateTime.parse(Create_Date, formatter);

                String Created_By = resultSet.getString("Created_By");


                String last_update = resultSet.getString("Last_Update");
                LocalDateTime lastupdateLocalDateTime = LocalDateTime.parse(last_update, formatter);

                String last_update_by = resultSet.getString("Last_Updated_By");



                int customerID = resultSet.getInt("Customer_ID");
                int userID = resultSet.getInt("User_ID");
                int contactID = resultSet.getInt("Contact_ID");
                ContactDAO contactDAO= new ContactDAO();
                String contactName= contactDAO.getContactNameByID(contactID);
                Appointments appointment = new Appointments(id, title, desc, location, Type, Start.toLocalDateTime(), end.toLocalDateTime(), createLocalDateTime, Created_By, lastupdateLocalDateTime, last_update_by, customerID, userID, contactID);

                appointmentsList.add(appointment);
                appointment.setContactName(contactName);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return appointmentsList;
    }

    /***
     * Retrieves all appointments assigned to a specific customer by id.
     * Returns the amount of appointments with an int value.
     * @param id
     * @return int
     * @throws SQLException
     */
    public int getAllCustomerAppointmentsByID(int id) throws SQLException {
        int appointments=0;
        String query="SELECT COUNT(*) FROM Appointments WHERE Customer_ID=?";
        try(PreparedStatement ps = JDBC.connection.prepareStatement(query);
            ){
                ps.setInt(1,id);
                try(ResultSet resultSet = ps.executeQuery()){
                    if(resultSet==null){
                        return appointments;
                    }
                    else if(resultSet.next()){
                        appointments= resultSet.getInt(1);
                }
                }

        }
    return appointments;}


    /***
     * Creates an appointment in the DB using JDBC.
     * Converts the chosen localDateTimes into Timestamps and convers the ZoneID to UTC
     * @param title
     * @param desc
     * @param location
     * @param type
     * @param start
     * @param end
     * @param createDate
     * @param createdBy
     * @param lastUpdate
     * @param lastUpdatedBy
     * @param customerID
     * @param userID
     * @param contactID
     */
    public void createAppointment(String title, String desc, String location, String type,LocalDateTime start, LocalDateTime end, LocalDateTime createDate, String createdBy, LocalDateTime lastUpdate, String lastUpdatedBy, int customerID, int userID, int contactID ){
      /**
        ZoneId defaultZone=ZoneId.systemDefault();
        ZoneId zoneId = ZoneId.of("UTC");
        //ZonedDateTime zoneSameCreate= zonedCreateDate.withZoneSameInstant(zoneId);

        ZonedDateTime zonedCreateDate = ZonedDateTime.of(createDate, zoneId);
        ZonedDateTime zonedFinalCreateDate = ZonedDateTime.ofInstant(zonedCreateDate.toInstant(), zoneId);

        ZonedDateTime zonedLastUpdateDate = ZonedDateTime.of(lastUpdate, zoneId);
        ZonedDateTime zonedFinalLastUpdateDate = ZonedDateTime.ofInstant(zonedLastUpdateDate.toInstant(), zoneId);

        ZonedDateTime zonedStart = ZonedDateTime.of(start, zoneId);
        ZonedDateTime zonedFinalStart = ZonedDateTime.ofInstant(zonedStart.toInstant(), zoneId);

        ZonedDateTime zonedEnd = ZonedDateTime.of(end, zoneId);
        ZonedDateTime zonedFinalEnd = ZonedDateTime.ofInstant(zonedEnd.toInstant(), zoneId);

        Timestamp timestampCreateDate=Timestamp.from(zonedFinalCreateDate.toInstant());
        Timestamp timestampLastUpdate=Timestamp.from(zonedFinalLastUpdateDate.toInstant());
        Timestamp timestampStart=Timestamp.from(zonedFinalStart.toInstant());
        Timestamp timestampEnd=Timestamp.from(zonedFinalEnd.toInstant());
  */
        // Convert ZonedDateTime to Instant (UTC)
        String query="INSERT INTO appointments(Title,Description,Location,Type,Start,End,Create_Date,Created_By,Last_Update,Last_Updated_By,Customer_ID,User_ID,Contact_ID) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?)";
        try (PreparedStatement ps = JDBC.connection.prepareStatement(query)) {
            ps.setString(1, title);
            ps.setString(2, desc);
            ps.setString(3, location);
            ps.setString(4, type);
            ps.setTimestamp(5,Timestamp.valueOf(start) );
            ps.setTimestamp(6, Timestamp.valueOf(end));
            ps.setTimestamp(7, Timestamp.valueOf(createDate));
            ps.setString(8, createdBy);
            ps.setTimestamp(9, Timestamp.valueOf(lastUpdate));
            ps.setString(10,lastUpdatedBy);
            ps.setInt(11,customerID);
            ps.setInt(12,userID);
            ps.setInt(13,contactID);
            ps.executeUpdate();


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /***
     * Updates the chosne appointment in the DB using JDBC.
     *
     * @param title
     * @param desc
     * @param location
     * @param type
     * @param start
     * @param end
     * @param lastUpdate
     * @param customerID
     * @param userID
     * @param contactID
     * @param id
     */
    public void updateAppointment(String title, String desc, String location, String type,LocalDateTime start, LocalDateTime end, LocalDateTime lastUpdate, int customerID, int userID, int contactID, int id){
        String query="UPDATE appointments SET Title=?, Description=?, Location=?, Type=?, Start=?, End=?, Last_Update=?, Customer_ID=?, User_ID=?, Contact_ID=? WHERE Appointment_ID=?";

        ZoneId zoneId = ZoneId.of("UTC");


        ZonedDateTime zonedLastUpdateDate = ZonedDateTime.of(lastUpdate, zoneId);
        ZonedDateTime zonedFinalLastUpdateDate = ZonedDateTime.ofInstant(zonedLastUpdateDate.toInstant(), zoneId);

        ZonedDateTime zonedStart = ZonedDateTime.of(start, zoneId);
        ZonedDateTime zonedFinalStart = ZonedDateTime.ofInstant(zonedStart.toInstant(), zoneId);

        ZonedDateTime zonedEnd = ZonedDateTime.of(end, zoneId);
        ZonedDateTime zonedFinalEnd = ZonedDateTime.ofInstant(zonedEnd.toInstant(), zoneId);

        Timestamp timestampLastUpdate=Timestamp.from(zonedFinalLastUpdateDate.toInstant());
        Timestamp timestampStart=Timestamp.from(zonedFinalStart.toInstant());
        Timestamp timestampEnd=Timestamp.from(zonedFinalEnd.toInstant());

        try (PreparedStatement ps = JDBC.connection.prepareStatement(query)) {
            ps.setString(1, title);
            ps.setString(2, desc);
            ps.setString(3, location);
            ps.setString(4, type);
            ps.setTimestamp(5,timestampStart );
            ps.setTimestamp(6, timestampEnd);
            ps.setTimestamp(7, timestampLastUpdate);
            ps.setInt(8,customerID);
            ps.setInt(9,userID);
            ps.setInt(10,contactID);
            ps.setInt(11,id);
            ps.executeUpdate();


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /***
     * Deletes the chosen appointment in the DB by appointment ID using JDBC.
     * @param id
     */
    public void deleteAppointment(int id){
        String query="DELETE FROM appointments WHERE Appointment_ID=? ";
        try(PreparedStatement ps= JDBC.connection.prepareStatement(query)) {
            ps.setInt(1,id);
            ps.executeUpdate();


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    }



