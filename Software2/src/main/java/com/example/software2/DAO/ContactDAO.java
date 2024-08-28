package com.example.software2.DAO;

import com.example.software2.Models.Appointments;
import com.example.software2.Models.Contacts;
import com.example.software2.helper.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

/***
 * DAO using to retrieve information from the contact table.
 */
public class ContactDAO {


    /***
     * Method used to retrieve all contacts in the DB using JDBC.
     * @return
     */
    public ObservableList<Contacts> getAllContact() {
        ObservableList<Contacts> contactsList = FXCollections.observableArrayList();
        String query = "SELECT Contact_ID, Contact_Name, Email FROM contacts";
        try (PreparedStatement ps = JDBC.connection.prepareStatement(query);
             ResultSet resultSet = ps.executeQuery();) {

            while (resultSet.next()) {
                int id = resultSet.getInt("Contact_ID");
                String name = resultSet.getString("Contact_Name");
                String email = resultSet.getString("Email");

                Contacts contact = new Contacts(id, name, email);
                contactsList.add(contact);
            }
        } catch (SQLException e) {

        }
        return contactsList;
    }

    /***
     * Method to retrieve the contactID by the param name.
     * @param name
     * @return int representing contact id.
     */
    public int getContactIDByName(String name) {
        int id = 0;
        String query = "SELECT Contact_ID FROM contacts WHERE Contact_Name=?";
        try (PreparedStatement ps = JDBC.connection.prepareStatement(query)) {
            ps.setString(1, name);
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                id = resultSet.getInt("Contact_ID");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return id;
    }

    /***
     * Retrieves the contact name by the param id.
     * @param id
     * @return Contact name
     */
    public String getContactNameByID(int id) {
        String name = "";
        String query = "SELECT Contact_Name FROM contacts WHERE Contact_ID=?";
        try (PreparedStatement ps = JDBC.connection.prepareStatement(query)) {
            ps.setInt(1, id);
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                name = resultSet.getString("Contact_Name");

            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return name;
    }

    /***
     * Retrieves all contact names in the DB using JDBC.
     * @return ObservableList<String>
     */
    public ObservableList<String> getContactNames() {
        ObservableList<String> names=FXCollections.observableArrayList();
        String name = "";
        String query = "SELECT Contact_Name FROM contacts";
        try (PreparedStatement ps = JDBC.connection.prepareStatement(query)) {

            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                 name = resultSet.getString("Contact_Name");
                names.add(name);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return names;
    }

    /***
     * Returns a list of appointments assigned to a contact based on name.
     * @param str
     * @return ObservableList<Appointments>
     */
    public ObservableList<Appointments> getContactSchedule(String str) {
        ObservableList< Appointments> appList =FXCollections.observableArrayList();
        String query = "SELECT Appointment_ID, Title,Type, Description, Start,End,Customer_ID,Contact_Name FROM appointments AS app " +
                "JOIN contacts AS c ON c.Contact_ID= app.Contact_ID WHERE c.Contact_Name=? ";
        try (PreparedStatement ps = JDBC.connection.prepareStatement(query)) {
           {
            ps.setString(1, str);
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("Appointment_ID");
                String title = resultSet.getString("Title");
                String desc = resultSet.getString("Description");
                String type = resultSet.getString("Type");

                Timestamp start = resultSet.getTimestamp("Start");
                LocalDateTime startDate = start.toLocalDateTime();
                Timestamp end = resultSet.getTimestamp("End");
                LocalDateTime endDate = end.toLocalDateTime();
                int customerID = resultSet.getInt("Customer_ID");
                Appointments appointments = new Appointments(id, title, desc, type, startDate, endDate, customerID);
                appList.add(appointments);
            }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
   return appList; }
}