package com.example.software2.Controllers;

import com.example.software2.DAO.AppointmentsDAO;
import com.example.software2.DAO.ContactDAO;
import com.example.software2.DAO.CustomerDAO;
import com.example.software2.DAO.UserDao;
import com.example.software2.Models.Appointments;
import com.example.software2.Models.Contacts;
import com.example.software2.Models.Customers;
import com.example.software2.Models.Users;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.*;
import java.util.ResourceBundle;

/***
 * Controller used to modify an existing appointment.
 *
 */
public class ModifyAppointmentController implements Initializable {

    public TextField modifyTitle;
    public TextField modifyType;
    public TextField modifyDesc;
    public TextField modifyLocation;
    public DatePicker modifyStartDate;
    public DatePicker modifyEndDate;
    public ComboBox modifyStartTime;
    public ComboBox modifyEndTime;
    public ComboBox modifyUserID;
    public ComboBox modifyCustomerID;
    public ComboBox modifyContact;

    private static Appointments getModifiedApp;

    private ObservableList<LocalDateTime> appTimes=FXCollections.observableArrayList();

    /***
     * Initializes the fields and combo boxes with the selected appointment from the main page.
     * I used a lambda here to get each userID from a list of Users and set it inside a new list so that the values could be set in the userID combo box upon initialization.
     * I used this lambda for the customer ID's as well, providing a fast and simple method that looks cleaner then additional enhanced for loops.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        ObservableList<LocalTime> localTimes= FXCollections.observableArrayList();
        localTimes.add(LocalTime.of(3,00));
        localTimes.add(LocalTime.of(3,30));
        localTimes.add(LocalTime.of(4,0));
        localTimes.add(LocalTime.of(5,30));
        localTimes.add(LocalTime.of(6,0));
        localTimes.add(LocalTime.of(6,30));
        localTimes.add(LocalTime.of(7,0));
        localTimes.add(LocalTime.of(7,30));

        localTimes.add(LocalTime.of(8,0));
        localTimes.add(LocalTime.of(8,30));
        localTimes.add(LocalTime.of(9,0));
        localTimes.add(LocalTime.of(9,30));
        localTimes.add(LocalTime.of(10,00));
        localTimes.add(LocalTime.of(10,30));
        localTimes.add(LocalTime.of(11,00));
        localTimes.add(LocalTime.of(11,30));
        localTimes.add(LocalTime.of(12,00));
        localTimes.add(LocalTime.of(12,30));
        localTimes.add(LocalTime.of(13,00));
        localTimes.add(LocalTime.of(13,30));
        localTimes.add(LocalTime.of(14,00));
        localTimes.add(LocalTime.of(14,30));
        localTimes.add(LocalTime.of(15,00));
        localTimes.add(LocalTime.of(15,30));
        localTimes.add(LocalTime.of(16,00));
        localTimes.add(LocalTime.of(16,30));
        localTimes.add(LocalTime.of(17,00));
        localTimes.add(LocalTime.of(17,30));
        localTimes.add(LocalTime.of(18,00));
        localTimes.add(LocalTime.of(18,30));
        localTimes.add(LocalTime.of(19,00));
        localTimes.add(LocalTime.of(19,30));
        localTimes.add(LocalTime.of(20,00));
        localTimes.add(LocalTime.of(20,30));
        localTimes.add(LocalTime.of(21,00));
        localTimes.add(LocalTime.of(21,30));
        localTimes.add(LocalTime.of(20,00));
        localTimes.add(LocalTime.of(20,30));
        localTimes.add(LocalTime.of(21,00));
        localTimes.add(LocalTime.of(21,30));
        localTimes.add(LocalTime.of(22,00));
        localTimes.add(LocalTime.of(22,30));
        localTimes.add(LocalTime.of(23,00));
        localTimes.add(LocalTime.of(23,30));
        localTimes.add(LocalTime.of(0,00));
        localTimes.add(LocalTime.of(0,30));
        localTimes.add(LocalTime.of(1,00));
        localTimes.add(LocalTime.of(1,30));
        localTimes.add(LocalTime.of(2,00));
        localTimes.add(LocalTime.of(2,30));
        localTimes.add(LocalTime.of(3,00));
        localTimes.add(LocalTime.of(3,30));
        localTimes.add(LocalTime.of(4,00));


        getModifiedApp=CustomerandAppointmentController.getModifyAppointment();
        modifyTitle.setText(getModifiedApp.getTitle());
        modifyDesc.setText(getModifiedApp.getDescription());
        modifyType.setText(getModifiedApp.getType());
        modifyLocation.setText(getModifiedApp.getLocation());

        Timestamp start= Timestamp.valueOf(getModifiedApp.getStart());
        Instant startTimeInstant= start.toInstant();

        LocalDate appStartDate= LocalDate.from(getModifiedApp.getStart());

        modifyStartDate.setValue(appStartDate);
        LocalTime appStartTime=LocalTime.from(getModifiedApp.getStart());
        modifyStartTime.setValue(appStartTime);
            modifyStartTime.setItems(localTimes);
        LocalDate appEndDate= LocalDate.from(getModifiedApp.getEnd());
        modifyEndDate.setValue(appEndDate);

        LocalTime appEndTime=LocalTime.from(getModifiedApp.getEnd());
        modifyEndTime.setValue(appEndTime);
        modifyEndTime.setItems(localTimes);



        UserDao userDao= new UserDao();
        ObservableList<Users> userList= userDao.getUsers();
        ObservableList<Integer> userIDS=FXCollections.observableArrayList();
        userList.forEach(user-> userIDS.add(user.getId()));
        modifyUserID.setValue(getModifiedApp.getUser_id());
        modifyUserID.setItems(userIDS);

        ContactDAO contactDAO= new ContactDAO();
        ObservableList<Contacts> contactsList= contactDAO.getAllContact();
        ObservableList<String> contactIDS=FXCollections.observableArrayList();
        contactsList.forEach(contact->contactIDS.add(contact.getContact_Name()));
        modifyContact.setValue(getModifiedApp.getContactName());
        modifyContact.setItems(contactIDS);

        CustomerDAO customerDAO= new CustomerDAO();
        ObservableList<Customers> customerList= customerDAO.getAllCustomers();
        ObservableList<Integer> custIDS=FXCollections.observableArrayList();
        for(Customers cust: customerList){
            custIDS.add(cust.getCustomer_ID());
        }
        modifyCustomerID.setValue(getModifiedApp.getCustomer_id());
        modifyCustomerID.setItems(custIDS);
        AppointmentsDAO appointmentsDAO=new AppointmentsDAO();
        ObservableList<Appointments> applist=appointmentsDAO.getAllAppointments();

        applist.forEach(app-> appTimes.addAll(app.getStart(),app.getEnd()));


    }

    /***
     * Checks if the users local time choice is within the business hours of 8am-10pm.
     * Returns true if the appointment is within the business hours and false otherwise.
     * @return
     */
    public  boolean isWithinBusinessHours(){
        LocalDate today=LocalDate.now();
        LocalTime opentime= LocalTime.of(8,0);
        LocalTime closetime= LocalTime.of(22,0);

        ZonedDateTime openingTime =ZonedDateTime.of(today,opentime,ZoneId.of("America/New_York"));
        ZonedDateTime closingTime =ZonedDateTime.of(today,closetime,ZoneId.of("America/New_York"));

        ZonedDateTime startTim =ZonedDateTime.of(today, (LocalTime) modifyStartTime.getValue(),ZoneId.of(String.valueOf(ZoneId.systemDefault())));
        ZonedDateTime endTim =ZonedDateTime.of(today, (LocalTime) modifyEndTime.getValue(),ZoneId.of(String.valueOf(ZoneId.systemDefault())));


        LocalTime openLocalTimes=LocalTime.from(openingTime);
        LocalTime closeLocalTimes=LocalTime.from(closingTime);


        Time now= new Time(8,0,0);
        //System.out.println(appStartTime.getValue());
        // LocalDateTime local= LocalDateTime.
        if(((LocalTime) modifyStartTime.getValue()).isAfter(openLocalTimes) && ((LocalTime) modifyEndTime.getValue()).isBefore(closeLocalTimes)){
            return true;
        }

        return false;  }
    /***
     * Checks to see if an appointment overlaps with another.
     * @param startTime1
     * @param endTime1
     * @param startTime2
     * @param endTime2
     * @return true or false depending on existing overlap
     */
    private boolean isOverlap(LocalDateTime startTime1, LocalDateTime endTime1, LocalDateTime startTime2, LocalDateTime endTime2) {
        return startTime1.isBefore(endTime2) && endTime1.isAfter(startTime2);
    }

    /***
     * Saves the modified appointment and updates it's information in the DB and the appointment table.
     * Checks all possible exceptions before the appointment is successfully modified.
     * @param actionEvent
     */
    public void saveModifiedAppointment(ActionEvent actionEvent){
        String title= modifyTitle.getText();
        String type= modifyType.getText();
        String location=modifyLocation.getText();
        String desc=modifyDesc.getText();

        LocalDateTime currentTime= LocalDateTime.now();

        if(title.isBlank()|| type.isBlank()|| location.isBlank() || desc.isBlank() || modifyUserID.getValue()==null || modifyCustomerID.getValue()==null|| modifyContact.getValue()==null ||
                modifyStartDate.getValue()==null|| modifyEndDate.getValue()==null|| modifyStartTime.getValue()==null || modifyEndTime.getValue()==null){
            Alert alert= new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Empty Fields");
            alert.setContentText("All Fields/Combo Boxes must be filled out");
            alert.showAndWait();
            return;
        }

        LocalDateTime appStart=LocalDateTime.of(modifyStartDate.getValue(),(LocalTime) modifyStartTime.getValue());
        LocalDateTime appEnd=LocalDateTime.of(modifyEndDate.getValue(), (LocalTime) modifyEndTime.getValue());



        if(appStart.isAfter(appEnd)|| appEnd.isBefore(appStart) || appStart.equals(appEnd)|| !modifyStartDate.getValue().isEqual(modifyEndDate.getValue()) ){

            Alert alert= new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Start/End Times are not in Range");
            alert.setContentText("The Start/End dates must be the same. The start time cannot be after the end time. The end time cannot be before the start time.");
            alert.showAndWait();
            return;
        }
        for(int i=0;i<appTimes.size()-1;i+=2) {
            System.out.println(appTimes.get(i));
            System.out.println(appTimes.get(i + 1));
            if (appStart.isAfter(appTimes.get(i)) && appStart.isBefore(appTimes.get(i + 1)) || appEnd.isAfter(appTimes.get(i)) &&
                    appEnd.isBefore(appTimes.get(i + 1)) || appStart.isEqual(appTimes.get(i)) || appEnd.equals(appTimes.get(i + 1)) || isOverlap(appStart, appEnd, appTimes.get(i), appTimes.get(i + 1))) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("Start Date Time and End Date Times cannot be during other Appointments");
                alert.setContentText("Make sure the appointment date time is not during other appointments");
                alert.showAndWait();
                return;
            }
            if (appStart.isBefore(currentTime) || appEnd.isBefore(currentTime)) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("Start or End is before the current Date/Time");
                alert.setContentText("You can't create an appointment for a time that has already passed.");
                alert.showAndWait();
                return;
            }
        }
        if(!isWithinBusinessHours()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Start or End is not during Business Hours");
            alert.setContentText("Business is in ET Time. Check your times in ET time to verify it's between 8:00am and 10:00pm");
            alert.showAndWait();
            return;
        }
        try{
            ContactDAO contactDAO= new ContactDAO();
            int id= contactDAO.getContactIDByName(modifyContact.getValue().toString());
            AppointmentsDAO appointmentsDAO= new AppointmentsDAO();

            appointmentsDAO.updateAppointment(title,desc,location,type,appStart,appEnd,currentTime,(int)modifyCustomerID.getValue(), (int)modifyUserID.getValue(),id,getModifiedApp.getId());

        } catch (Exception e) {
            throw new RuntimeException(e);
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
    }

    /***
     * Cancels the appointment creation by returning to the main page.
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
