package com.example.software2.DAO;

import com.example.software2.Models.Appointment;
import com.example.software2.helper.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/***
 * DAO to access the appointments table.
 */
public class AppointmentDAO {


    /***
     * Used to get all appointments from the appointment class. Used specifically for the reports page.
     * Forgot to make a new constructor so used this method instead.
     * @return ObservableList<Appointment>
     */
    public ObservableList<Appointment> getAllAppointments(){
        String query="SELECT MONTH(Start) as startMonth , Type, COUNT(*) AS Total FROM appointments GROUP BY startMonth, Type";
        ObservableList<Appointment> appList = FXCollections.observableArrayList();

        try(PreparedStatement ps = JDBC.connection.prepareStatement(query)){
            ResultSet resultSet = ps.executeQuery();
            while(resultSet.next()){
                int monthValue = resultSet.getInt("startMonth");

                String type = resultSet.getString("Type");
                int total = resultSet.getInt("Total");
                appList.add(new Appointment(monthValue, type, total));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
       return appList;}

}