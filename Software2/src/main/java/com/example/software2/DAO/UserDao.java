package com.example.software2.DAO;

import com.example.software2.Models.Appointments;
import com.example.software2.Models.Users;
import com.example.software2.helper.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

/***
 * DAO used to access the user table using JDBC.
 */
public class UserDao {

    private static String select_users_query= "SELECT User_ID, User_Name ,Password FROM users";

    /***
     * Returns a list of all users in the DB using JDBC.
     * @return
     */
    public ObservableList<Users> getUsers(){
        ObservableList<Users> users= FXCollections.observableArrayList();

        try(PreparedStatement ps= JDBC.connection.prepareStatement(select_users_query);
            ResultSet resultSet= ps.executeQuery();){

            while(resultSet.next()){
                int id= resultSet.getInt("User_ID");
                String username= resultSet.getString("User_Name");
                String password= resultSet.getString("Password");
                Users user= new Users(id,username,password);
                users.add(user);
            }
        }
        catch(SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    /***
     * Method to retrieve all start localDatetimes based on the user ID
     * @param id
     * @return ObservableList<LocalDateTime>
     */
    public ObservableList<LocalDateTime> getAllUserAppointmentTimes(int id){
        ObservableList<LocalDateTime> timeList=FXCollections.observableArrayList();
        String query="SELECT Start FROM appointments WHERE User_ID=?";
        try(PreparedStatement ps= JDBC.connection.prepareStatement(query)) {
            ps.setInt(1,id);
        ResultSet rs=  ps.executeQuery();
        while(rs.next()){
            Timestamp timestamp= rs.getTimestamp("Start");
            LocalDateTime time=timestamp.toLocalDateTime();
            timeList.add(time);
        }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
   return timeList; }

    /***
     * Returns all user appointments by the users ID.
     * @param id
     * @return ObservableList<Appointments>
     */
    public ObservableList<Appointments> getAllUserAppointmentsByID(int id){
        ObservableList<Appointments> appList=FXCollections.observableArrayList();
        String query="SELECT Appointment_ID,Start FROM appointments WHERE User_ID=?";
        try(PreparedStatement ps= JDBC.connection.prepareStatement(query)) {
            ps.setInt(1,id);
            ResultSet rs=  ps.executeQuery();
            while(rs.next()){
                Timestamp timestamp= rs.getTimestamp("Start");
                LocalDateTime time=timestamp.toLocalDateTime();
                  int appID=rs.getInt("Appointment_ID");
                  appList.add(new Appointments(time,appID));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return appList;
    }
}
