package com.example.software2.DAO;

import com.example.software2.helper.JDBC;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/***
 * Controller used to access the first level divisions table in the DB.
 */
public class FirstLevelDivisionDAO {

    /***
     * Returns the corresponding state by the id provided.
     * @param id
     * @return
     */
    public String getStateByDivisionID(int id) {
        String query = "SELECT Division FROM first_level_divisions WHERE Division_ID = " + id;
        String division;
        try (PreparedStatement ps = JDBC.connection.prepareStatement(query);
             ResultSet resultSet = ps.executeQuery();) {
            while (resultSet.next()) {
                division = resultSet.getString("Division");
                return division;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    /***
     * Returns the ID that correspondes to the passed in division
     * @param division
     * @return id
     * @throws SQLException
     */
    public int getIDByState(String division) throws SQLException {
        int divisionID=0;
        String query = "SELECT Division_ID FROM first_level_divisions WHERE Division = ? ";
        try (PreparedStatement ps = JDBC.connection.prepareStatement(query);) {
            ps.setString(1,division);
            ResultSet resultSet = ps.executeQuery();
            if(resultSet.next()) {
                divisionID = resultSet.getInt("Division_ID");
            }
        } catch (SQLException e) {

        }
   return divisionID; }

    /***
     * Returns an int specifying the countryID based on the division.
     * @param division
     * @return CountryID
     * @throws SQLException
     */
    public int getCountryIDByState(String division) throws SQLException {
        int divisionID=0;
        String query = "SELECT Country_ID FROM first_level_divisions WHERE Division = ? ";
        try (PreparedStatement ps = JDBC.connection.prepareStatement(query);) {
            ps.setString(1,division);
            ResultSet resultSet = ps.executeQuery();
            if(resultSet.next()) {
                divisionID = resultSet.getInt("Country_ID");
            }
        } catch (SQLException e) {

        }
        return divisionID; }


}