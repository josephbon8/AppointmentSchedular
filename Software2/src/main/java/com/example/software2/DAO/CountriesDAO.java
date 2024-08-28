package com.example.software2.DAO;

import com.example.software2.helper.JDBC;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/***
 * DAO used to access the countries table in the DB using JDBC
 */
public class CountriesDAO {

    /***
     * Retrieves the country name based on the country id.
     * @param id
     * @return Country
     * @throws SQLException
     */
    public String getCountryByCountryID(int id) throws SQLException {
        String country="";
        String query = "SELECT Country FROM countries WHERE Country_ID = ? ";
        try (PreparedStatement ps = JDBC.connection.prepareStatement(query);) {
            ps.setInt(1,id);
            ResultSet resultSet = ps.executeQuery();
            if(resultSet.next()) {
               country = resultSet.getString("Country");
            }
        } catch (SQLException e) {

        }
        return country; }

}
