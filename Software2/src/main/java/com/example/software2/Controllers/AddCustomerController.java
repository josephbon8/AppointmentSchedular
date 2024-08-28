package com.example.software2.Controllers;

import com.example.software2.DAO.CustomerDAO;
import com.example.software2.DAO.FirstLevelDivisionDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.ResourceBundle;

/***
 * Controller class that creates a new customer within the database.
 */

public class AddCustomerController implements Initializable {
    public TextField custID;
    public TextField custName;
    public TextField custAddress;
    public TextField custPhone;
    public ComboBox custState;
    @FXML
    public ComboBox custCountry;
    public TextField custPostal;

    static ObservableList<String> usStates=FXCollections.observableArrayList("Alabama", "Arizona", "Arkansas", "California",
            "Colorado", "Connecticut", "Delaware", "District of Columbia", "Florida", "Georgia", "Idaho", "Illinois",
            "Indiana", "Iowa", "Kansas", "Kentucky", "Louisiana", "Maine", "Maryland", "Massachusetts", "Michigan"
            , "Minnesota", "Mississippi", "Missouri", "Montana", "Nebraska", "New Hampshire", "New Jersey", "New Mexico", "New York", "North Carolina", "North Dakota"
            , "Ohio", "Oklahoma", "Oregon", "Pennsylvania", "Rhode Island", "South Carolina", "South Dakota",
            "Tennessee", "Texas", "Utah", "Vermont", "Virginia", "Washington", "West Virginia", "Wisconsin", "Wyoming", "Hawaii", "Alaska");

    static ObservableList<String> ukStates=FXCollections.observableArrayList("England", "Wales", "Scotland", "Northern Ireland");

    static  ObservableList<String> canadaStates=FXCollections.observableArrayList("Northwest Territories", "Alberta", "British Columbia", "Manitoba", "New Brunswick", "Nova Scotia", "Prince Edward Island", "Ontario", "Québec", "Saskatchewan", "Nunavut", "Yukon", "Newfoundland and Labrador");

    /***
     * Method that changes the state combo box values depending on which country is selected.
     * @param actionEvent
     */
    @FXML
    public void selectCountry(ActionEvent actionEvent){
        String s=custCountry.getSelectionModel().getSelectedItem().toString();
        if(s.equals("US")){
            custState.setItems(usStates);
        }
        else if(s.equals("Canada")){
            custState.setItems(canadaStates);
        }
        else if(s.equals("UK")){
            custState.setItems(ukStates);
        }
    }

    /***
     * Initialize method that initializes all combo boxes to contain the required items.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
         ObservableList<String> countries = FXCollections.observableArrayList("US","UK","Canada");

        custCountry.setItems(countries);

    }
        public void CountryStateMap() {
            HashMap<String, String[]> countryStateMap = new HashMap<>();
            countryStateMap.put("US", new String[]{"Alabama", "Arizona", "Arkansas", "California",
                    "Colorado", "Connecticut", "Delaware", "District of Columbia", "Florida", "Georgia", "Idaho", "Illinois",
                    "Indiana", "Iowa", "Kansas", "Kentucky", "Louisiana", "Maine", "Maryland", "Massachusetts", "Michigan"
                    , "Minnesota", "Mississippi", "Missouri", "Montana", "Nebraska", "New Hampshire", "New Jersey", "New Mexico", "New York", "North Carolina", "North Dakota"
                    , "Ohio", "Oklahoma", "Oregon", "Pennsylvania", "Rhode Island", "South Carolina", "South Dakota",
                    "Tennessee", "Texas", "Utah", "Vermont", "Virginia", "Washington", "West Virginia", "Wisconsin", "Wyoming", "Hawaii", "Alaska"}
            );
            countryStateMap.put("Canada", new String[]{"Northwest Territories", "Alberta", "British Columbia", "Manitoba", "New Brunswick", "Nova Scotia", "Prince Edward Island", "Ontario", "Québec", "Saskatchewan", "Nunavut", "Yukon", "Newfoundland and Labrador"});

            countryStateMap.put("UK", new String[]{"England", "Wales", "Scotland", "Northern Ireland"});
        }

    /***
     * Saves a customer to the DB by using JDBC from the DAO classes. Checkes that all exceptions pass before saving.
     * Returns to the main page after successfully creating a customer.
     * @param actionEvent
     * @throws SQLException
     * @throws NullPointerException
     */
    public void saveCustomer(ActionEvent actionEvent) throws SQLException, NullPointerException {
        String createdBy="script";
        String name= custName.getText();
        String address=custAddress.getText();
        String phone= custPhone.getText();
        String postalAsString= custPostal.getText();
        FirstLevelDivisionDAO fDao = new FirstLevelDivisionDAO();
        Timestamp utc= new Timestamp(System.currentTimeMillis());
         int divisionID=0;
        String state="";
        String finalState="";
        int postal=0;
        if(name.isBlank()|| address.isBlank() || phone.isBlank()|| postalAsString.isBlank()|| custCountry.getValue()==null|| custState.getValue()==null){
            Alert alert= new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Empty Fields");
            alert.setContentText("All Fields/Combo Boxes must be filled out");
            alert.showAndWait();
            return;
        }

        try {
            postal=Integer.parseInt(postalAsString);

        }catch (NumberFormatException e){
            Alert alert= new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Postal Not Integer");
            alert.setContentText("Postal/Zipcode must be an integer");
            alert.showAndWait();
            return;
        }

         if(custState.getValue()!=null){
              state= custState.getSelectionModel().getSelectedItem().toString();
            finalState= state.substring(0,state.length());

            divisionID=fDao.getIDByState(finalState);
             System.out.println(finalState);
            System.out.println(divisionID);
        }

        try{
            CustomerDAO dao= new CustomerDAO();
            System.out.println(postal);
            dao.insertIntoCustomers(name,address,postal,divisionID,phone,utc,createdBy,utc,createdBy);
        }catch (Exception e){
                e.printStackTrace();
        }

        try{
            Parent root= FXMLLoader.load(getClass().getResource("/com/example/software2/Appointments.fxml"));

            Stage stage= (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
            Scene scene= new Scene(root);
            stage.setTitle("Customer/Appointments");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


       /* Optional.ofNullable(custState).ifPresentOrElse(comboBox -> {
           try{ divisionID[0] = fDao.getIDByState(custState.getSelectionModel().getSelectedItem().toString());}
           catch (SQLException e){

           }

        }, ()->{

        });

        try {


            int postal=Integer.parseInt(custPostal.getText());
        }
        catch (NullPointerException e){
            e.printStackTrace();
            Alert alert= new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Empty Fields");
            alert.setContentText("All Fields/Combo Boxes must be filled out");

        }

        */
    }

    /***
     * Cancels the customer creation by returning to the main page.
     * @param actionEvent
     * @throws IOException
     */
    public void cancel(ActionEvent actionEvent) throws IOException {

        Parent root= FXMLLoader.load(getClass().getResource("/com/example/software2/Appointments.fxml"));

        Stage stage= (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene= new Scene(root);
        stage.setTitle("Customer/Appointments");
        stage.setScene(scene);
        stage.show();
    }
}
