package com.example.software2.DAO;

import com.example.software2.Models.Customers;
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
 * DAO used to access the customer table and retrieve information using JDBC.
 */
public class CustomerDAO {

    private String getAllQuery = "SELECT c.Customer_ID, c.Customer_Name, c.Address, c.Postal_Code, c.Phone, c.Create_Date, c.Created_By, c.Last_Update, c.Last_Updated_By, c.Division_ID, d.Division" +
            " FROM customers c JOIN first_level_divisions d ON c.Division_ID= d.Division_ID";

    /***
     * Returns a list of all the customers in the DB.
     * @return ObservableList<Customers>
     */
    public ObservableList<Customers> getAllCustomers() {
        ObservableList<Customers> customers = FXCollections.observableArrayList();

        try (PreparedStatement ps = JDBC.connection.prepareStatement(getAllQuery);
             ResultSet resultSet = ps.executeQuery();) {

            while (resultSet.next()) {
                int id = resultSet.getInt("Customer_ID");
                String name = resultSet.getString("Customer_Name");
                String address = resultSet.getString("Address");
                String postalCode = resultSet.getString("Postal_Code");
                String phone = resultSet.getString("Phone");
                String createDate = String.valueOf(resultSet.getString("Create_Date"));
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                LocalDateTime createDateLocalDateTime = LocalDateTime.parse(createDate, formatter);
                String createdBy = resultSet.getString("Created_By");
                String lastUpdate = String.valueOf(resultSet.getString("Last_Update"));
                LocalDateTime lastUpdateLocalDateTime = LocalDateTime.parse(lastUpdate, formatter);

                String lastUpdatedBy = resultSet.getString("Last_Updated_By");
                int divisionID = resultSet.getInt("Division_ID");

                String division = resultSet.getString("Division");

                Customers customer = new Customers(id, name, address, postalCode, division, phone, createDateLocalDateTime, createdBy, lastUpdateLocalDateTime, lastUpdatedBy, divisionID);
                customer.setCountry(divisionID);
                customers.add(customer);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }


        return customers;
    }

    /***
     * Creates a new customer and inserts it into the DB using JDBC.
     * @param name
     * @param address
     * @param postalCode
     * @param division
     * @param phone
     * @param createTime
     * @param createdBy
     * @param lastUpdateTime
     * @param lastUpdatedBy
     */
    public void insertIntoCustomers( String name, String address, int postalCode, int division, String phone, Timestamp createTime, String createdBy, Timestamp lastUpdateTime, String lastUpdatedBy) {

        ZoneId zoneId = ZoneId.of("UTC");
        ZonedDateTime zonedCreateDateTime = ZonedDateTime.of(createTime.toLocalDateTime(), zoneId);
        ZonedDateTime zonedLastUpdateDateTime = ZonedDateTime.of(lastUpdateTime.toLocalDateTime(), zoneId);

        // Convert ZonedDateTime to Instant (UTC)
        ZonedDateTime finalcreateTime= ZonedDateTime.ofInstant(zonedCreateDateTime.toInstant(),zoneId);
        ZonedDateTime finalLastUpdateTime= ZonedDateTime.ofInstant(zonedLastUpdateDateTime.toInstant(),zoneId);

        Instant instantCreate = zonedCreateDateTime.toInstant();
        Instant instantLastUpdate = zonedLastUpdateDateTime.toInstant();

        String query = "INSERT INTO customers ( Customer_Name, Address, Postal_Code, Division_ID, Phone, Create_Date, Created_By,Last_Update, Last_Updated_By) VALUES (?,?,?,?,?,?,?,?,?)";
        try (PreparedStatement ps = JDBC.connection.prepareStatement(query)) {
            Timestamp createTime1= Timestamp.from(finalcreateTime.toInstant());
            Timestamp lastUpdateTime1= Timestamp.from(finalLastUpdateTime.toInstant());

            ps.setString(1, name);
            ps.setString(2, address);
            ps.setInt(3, postalCode);
            ps.setInt(4, division);
            ps.setString(5, phone);
            ps.setTimestamp(6, Timestamp.valueOf(createTime.toLocalDateTime()));
            ps.setString(7, createdBy);
            ps.setTimestamp(8, Timestamp.valueOf(lastUpdateTime.toLocalDateTime()));
            ps.setString(9, lastUpdatedBy);


            ps.executeUpdate();


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /***
     * Updates the specified customer in the DB using JDBC.
     * @param name
     * @param address
     * @param postalCode
     * @param division
     * @param phone
     * @param lastUpdateTime
     * @param id
     */
        public void UpdateCustomer( String name, String address, int postalCode, int division, String phone,  Timestamp lastUpdateTime, int id) {
            String query="UPDATE Customers SET Customer_Name=?, Address=?, Postal_Code=?, Division_ID=?, Phone=? ,Last_Update=? WHERE Customer_ID=? ";
            try(PreparedStatement ps= JDBC.connection.prepareStatement(query)) {
                ZoneId zoneId=ZoneId.of("UTC");
                ZonedDateTime zonedLastUpdateDateTime = ZonedDateTime.of(lastUpdateTime.toLocalDateTime(), zoneId);
                ZonedDateTime finalLastUpdateTime= ZonedDateTime.ofInstant(zonedLastUpdateDateTime.toInstant(),zoneId);
                Timestamp lastUpdateTime1= Timestamp.from(finalLastUpdateTime.toInstant());

                ps.setString(1,name);
                ps.setString(2,address);
                ps.setInt(3,postalCode);
                ps.setInt(4,division);
                ps.setString(5,phone);
                ps.setTimestamp(6,Timestamp.valueOf(lastUpdateTime.toLocalDateTime()));
                ps.setInt(7,id);

                ps.executeUpdate();


            } catch (SQLException e) {
                e.printStackTrace();
            }
    }

    /***
     * Deletes the specified customer from the db using JDBC.
     * @param id
     */
        public void deleteCustomer(int id){
            String query="DELETE FROM customers WHERE Customer_ID=? ";
            try(PreparedStatement ps= JDBC.connection.prepareStatement(query)) {
                ps.setInt(1,id);
             ps.executeUpdate();


            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

}