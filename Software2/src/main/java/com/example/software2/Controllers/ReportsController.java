package com.example.software2.Controllers;

import com.example.software2.DAO.AppointmentDAO;
import com.example.software2.DAO.ContactDAO;
import com.example.software2.Models.Contacts;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ReportsController implements Initializable {
    public TableColumn custID;
    public TableView divisionTable;
    public TableView customerTable;
    public TableColumn customerTitle;
    public TableColumn customerType;
    public TableColumn customerDesc;
    public TableColumn customerStartDate;
    public TableColumn customerEndDate;
    public TableColumn customerID;
    public TableView appointmentTable;
    public TableColumn appMonth;
    public TableColumn appType;
    public TableColumn appTotal;
    public TableView contactTable;
    public TableColumn contactName;
    public TableColumn contactEmail;
    public TableColumn contactID;
    public ComboBox contactCombo;

    /***
     * Used to initialize the tables on this page with the required data.
     * I used a lambda here to get all the Contact names from a list containing each contact.
     * This helped to simply the code.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        AppointmentDAO appointmentsDAO = new AppointmentDAO();

        appType.setCellValueFactory(new PropertyValueFactory<>("type"));
        appMonth.setCellValueFactory(new PropertyValueFactory<>("month"));
        appTotal.setCellValueFactory(new PropertyValueFactory<>("total"));
        appointmentTable.setItems(appointmentsDAO.getAllAppointments());

        contactName.setCellValueFactory(new PropertyValueFactory<>("Contact_Name"));
        contactEmail.setCellValueFactory(new PropertyValueFactory<>("Email"));

        ContactDAO contactDAO = new ContactDAO();

        contactTable.setItems(contactDAO.getAllContact());

        contactID.setCellValueFactory(new PropertyValueFactory<>("id"));
        customerID.setCellValueFactory(new PropertyValueFactory<>("customer_id"));
        customerTitle.setCellValueFactory(new PropertyValueFactory<>("Title"));
        customerType.setCellValueFactory(new PropertyValueFactory<>("Type"));
        customerDesc.setCellValueFactory(new PropertyValueFactory<>("Description"));
        customerStartDate.setCellValueFactory(new PropertyValueFactory<>("Start"));
        customerEndDate.setCellValueFactory(new PropertyValueFactory<>("End"));


       // contactCombo.setItems(contactDAO.getAllContact());



            ObservableList<String> names = FXCollections.observableArrayList();
        ObservableList<Contacts> contactList=contactDAO.getAllContact();
            contactList.forEach(contact-> names.add(contact.getContact_Name()));

            contactCombo.setItems(names);


        }



    /***
     * Used to navigate back to the main page.
     * @param actionEvent
     */
    public void exitHandler(ActionEvent actionEvent) {
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/software2/Appointments.fxml"));
            Parent root = loader.load();
            Stage stage= (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
            Scene scene= new Scene(root);
            stage.setTitle("Customer/Appointments");
            stage.setScene(scene);
            stage.show();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    /**
     * Used to get a ContactsSchedule by clicking on their name in a combo box.
     * @param actionEvent
     */
    public void getContact(ActionEvent actionEvent) {
        try {
            ContactDAO contactDAO = new ContactDAO();
            ObservableList<String> names= FXCollections.observableArrayList();
            for(Contacts contacts: contactDAO.getAllContact()){
                names.add(contacts.getContact_Name());
            }
            contactCombo.setItems(names);
            contactCombo.getValue().toString();
            if(!names.isEmpty()){
               customerTable.setItems(contactDAO.getContactSchedule(contactCombo.getValue().toString()));


            }

           // customerTable.setItems(contactDAO.getContactSchedule();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
