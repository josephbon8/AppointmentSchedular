package com.example.software2.Controllers;

import com.example.software2.DAO.CustomerDAO;
import com.example.software2.DAO.FirstLevelDivisionDAO;
import com.example.software2.Models.Customers;
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
import java.util.ResourceBundle;

/***
 * Controller used to modify the customer selected in the main page.
 */
public class ModifyCustomerController implements Initializable {
    public TextField modifyID;
    public TextField modifyName;
    public TextField modifyAddress;
    public TextField modifyPhone;
    public ComboBox modifyState;
    public ComboBox modifyCountry;
    public TextField modifyPostal;

    private static Customers customer;

    /***
     * Initializes each field and combo box with the customer data from the main page.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        customer = CustomerandAppointmentController.getCustomer();
        System.out.println(customer);
        modifyName.setText(customer.getCustomer_Name());
        modifyAddress.setText(customer.getAddress());
        modifyPhone.setText(customer.getPhone());
        modifyPostal.setText(customer.getPostal_Code());
        ObservableList<String> list = FXCollections.observableArrayList("US", "UK", "Canada");
        modifyCountry.setItems(list);
        FirstLevelDivisionDAO dao= new FirstLevelDivisionDAO();
       // dao.getCountryIDByState(cu)
        String country = ModifyCustomerController.getState(customer.getCustomer_ID());
        modifyCountry.setValue(customer.getCountry());

        modifyState.setValue(customer.getDivision());
    }

    /***
     * Sets the state combo box items depending on which Country is chosen.
     * @param id
     * @return
     */
    public static String getState(int id) {
        if (customer.getDivision_id() <= 0 && customer.getDivision_id() <= 54) {
            return "US";
        }
        if (customer.getDivision_id() > 54 && customer.getDivision_id() <= 72) {
            return "Canada";
        }
        if (customer.getDivision_id() > 72 && customer.getDivision_id() <= 104) {
            return "UK";
        }
        return "";
    }

    /***
     * Used to reset the country combo box after being clicked.
     * @param actionEvent
     */
    @FXML
    public void selectCountry(ActionEvent actionEvent) {
        String s = modifyCountry.getSelectionModel().getSelectedItem().toString();
        if (s.equals("US")) {
            modifyState.setItems(AddCustomerController.usStates);
            modifyState.setValue(null);
        } else if (s.equals("Canada")) {
            modifyState.setItems(AddCustomerController.canadaStates);
            modifyState.setValue(null);
        } else if (s.equals("UK")) {
            modifyState.setItems(AddCustomerController.ukStates);
            modifyState.setValue(null);
        }
    }

    /***
     * Saves the modified customer after it passes all required exceptions.
     * Updates this customer in the DB using JDBC and updates in the tableview on the main page.
     * @param actionEvent
     * @throws SQLException
     */
    @FXML
    public void saveCustomer(ActionEvent actionEvent) throws SQLException {
        String createdBy = "script";
        String name = modifyName.getText();
        String address = modifyAddress.getText();
        String phone = modifyPhone.getText();
        String postalAsString = modifyPostal.getText();
        FirstLevelDivisionDAO fDao = new FirstLevelDivisionDAO();
        Timestamp utc = new Timestamp(System.currentTimeMillis());
        int divisionID = 0;
        String state = "";
        String finalState = "";
        int postal = 0;
        if (name.isBlank() || address.isBlank() || phone.isBlank() || postalAsString.isBlank() || modifyCountry.getValue() == null || modifyState.getValue() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Empty Fields");
            alert.setContentText("All Fields/Combo Boxes must be filled out");
            alert.showAndWait();
            return;
        }

        try {
            postal = Integer.parseInt(postalAsString);

        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Postal Not Integer");
            alert.setContentText("Postal/Zipcode must be an integer");
            alert.showAndWait();
            return;
        }

        if (modifyState.getValue() != null) {
            state = modifyState.getSelectionModel().getSelectedItem().toString();
            finalState = state.substring(0, state.length());

            divisionID = fDao.getIDByState(finalState);
            System.out.println(finalState);
            System.out.println(divisionID);
        }
        try {
            CustomerDAO dao = new CustomerDAO();
            dao.UpdateCustomer(name, address, postal, divisionID, phone, utc, customer.getCustomer_ID());
        } catch (Exception e) {
            e.printStackTrace();
        }

        try{
            Parent root = FXMLLoader.load(getClass().getResource("/com/example/software2/Appointments.fxml"));

            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setTitle("Customer/Appointments");
            stage.setScene(scene);
            stage.show();

        }catch (IOException e){
            e.printStackTrace();
        }
    }


    /***
     * Cancels the customer creation by returning to the main page.
     * @param actionEvent
     * @throws IOException
     */
    @FXML
    public void cancelCustomer(ActionEvent actionEvent) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/com/example/software2/Appointments.fxml"));

            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setTitle("Customer/Appointments");
            stage.setScene(scene);
            stage.show();


        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}