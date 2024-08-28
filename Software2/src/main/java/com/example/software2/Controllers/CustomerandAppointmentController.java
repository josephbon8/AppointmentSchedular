package com.example.software2.Controllers;

import com.example.software2.DAO.AppointmentsDAO;
import com.example.software2.DAO.CustomerDAO;
import com.example.software2.Models.Appointments;
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
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.ResourceBundle;

/***
 * Main page of the application which display's customer and appointment data in TableViews. Contains all CRUD operations.
 * Initializes all data upon load.
 */
public class CustomerandAppointmentController implements Initializable {


    public TableView<Customers> customersTable;
    public TableColumn customersID;
    public TableColumn customersName;
    public TableColumn customersAddress;
    public TableColumn customersPhone;
    public TableColumn customersState;
    public TableColumn customersPostal;
    public TableView<Appointments> appointmentsTable;
    public TableColumn appointmentsID;
    public TableColumn appointmentsTitle;
    public TableColumn appointmentsType;
    public TableColumn appointmentsDesc;
    public TableColumn appointmentsStartTime;
    public TableColumn appointmentsEndTime;
    public TableColumn appointmentsContact;
    public TableColumn appointmentsUserID;
    public TableColumn appointmentsCustomerID;
    public RadioButton weekButton;
    public RadioButton monthButton;
    public RadioButton allButton;
    public TableColumn customersDivision;
    public TableColumn customersDivisionID;

    private static Customers modifyCustomer;

    private static Appointments modifyAppointment;

    private static Users getUserData;

    /***
     * Getter for the customer that's highlighted when the modify button is clicked. Used for getting the customer Object in other controllers.
     * @return modifyCustomer.
     */
    public static Customers getCustomer(){
        return modifyCustomer;
    }

    /***
     * Getter for the appointment that's highlighted when the modify button is clicked. Used for getting the appointment Object in other controllers.
     * @return modifyAppointment
     */
    public static Appointments getModifyAppointment() {
        return modifyAppointment;
    }

    /***
     * Setter for the customer that's highlighted when the modify button is clicked. Used for setting the customer Object in other controllers.
     * @return modifyCustomer.
     */
    public static void setCustomer(Customers customer){
        modifyCustomer=customer;
    }

    private static int appointmentID;

    private static LocalDateTime appTime;

    /***
     * Initializes the tableviews with the appropriate data.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        CustomerDAO customerDAO=new CustomerDAO();

        customersTable.setItems(customerDAO.getAllCustomers());

        customersID.setCellValueFactory(new PropertyValueFactory<>("Customer_ID"));
        customersName.setCellValueFactory(new PropertyValueFactory<>("Customer_Name"));
        customersAddress.setCellValueFactory(new PropertyValueFactory<>("Address"));
        customersPhone.setCellValueFactory(new PropertyValueFactory<>("Phone"));
        customersPostal.setCellValueFactory(new PropertyValueFactory<>("Postal_Code"));
        customersDivision.setCellValueFactory(new PropertyValueFactory<>("division"));
        customersDivisionID.setCellValueFactory(new PropertyValueFactory<>("division_id"));

        AppointmentsDAO appDao= new AppointmentsDAO();
        appointmentsTable.setItems(appDao.getAllAppointments());
        appointmentsID.setCellValueFactory(new PropertyValueFactory<>("id"));
        appointmentsTitle.setCellValueFactory(new PropertyValueFactory<>("Title"));
        appointmentsDesc.setCellValueFactory(new PropertyValueFactory<>("Description"));
        appointmentsContact.setCellValueFactory(new PropertyValueFactory<>("contactName"));
        appointmentsCustomerID.setCellValueFactory(new PropertyValueFactory<>("customer_id"));
        appointmentsUserID.setCellValueFactory(new PropertyValueFactory<>("user_id"));
        appointmentsType.setCellValueFactory(new PropertyValueFactory<>("Type"));
        appointmentsStartTime.setCellValueFactory(new PropertyValueFactory<>("Start"));
        appointmentsEndTime.setCellValueFactory(new PropertyValueFactory<>("End"));

    }

    /***
     * Sets the userData so that it can be used in other controllers.
     * @param user
     */
    public  void  setUserData(Users user){
        getUserData=user;

    }

    /***
     * Gets the current user so that it can be used in other controllers.
     * @return
     */
    public  Users getUserData(){
        return getUserData;
    }

    /***
     * Used in combination with the method userAppointmentsIn15minutes to check if an appointment is within 15 minutes of logIn.
     * Returns true if an appointment is coming up in 15, false otherwise.
     * @param bool
     */
    public void getBooleanResult(Boolean bool){
        if(bool)
            {
                Alert alert= new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText("Upcoming Appointment");
                alert.setContentText("AppointmentID"+appointmentID+ "at: "+appTime );
                alert.showAndWait();
            }

        else{
                Alert alert= new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText(" No Upcoming Appointments");
                alert.setContentText("UserID "+getUserData.getId()+ "Name "+getUserData.getUser_Name()+ "has no upcoming appointments" );
                alert.showAndWait();
            }
        }

    /***
     * Used to compare a list of appointments with the currentTime.
     * Returns true or false depending on whether an appointment is in 15 minutes.
     * @param appointmentsList
     * @param time
     * @return
     */
    public static boolean userAppointmentsIn15Minutes(ObservableList<Appointments> appointmentsList, LocalDateTime time) {
        ZoneId systemZone = ZoneId.systemDefault();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");

        for (Appointments app : appointmentsList) {
            LocalDateTime listTimeZone = app.getStart().atZone(systemZone).toLocalDateTime();
            LocalDateTime paramTimeZone = time.atZone(systemZone).toLocalDateTime();

            Duration duration = Duration.between(listTimeZone, paramTimeZone);

            if (duration.toMinutes() <= 15) {
                appointmentID = app.getId();
                appTime = app.getStart().atZone(ZoneId.systemDefault()).toLocalDateTime();
                return true;
            }
        }
        return false;
    }

    /***
     * Used to compare a list of appointments with the currentTime.
     * Returns true or false depending on whether an appointment is in 15 minutes.
     * @param appointmentsList
     * @param time
     * @return
     */

    public boolean userAppointmentsIn15Minute(ObservableList<Appointments> appointmentsList, LocalDateTime time) {
        ZoneId systemZone = ZoneId.systemDefault();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");

        for (Appointments app : appointmentsList) {
            LocalDateTime listTimeZone = app.getStart().atZone(systemZone).toLocalDateTime();
            LocalDateTime paramTimeZone = time.atZone(systemZone).toLocalDateTime();

            Duration duration = Duration.between(paramTimeZone, listTimeZone);

            if (listTimeZone.isAfter(time) && Duration.between(time, listTimeZone).toMinutes() <= 15) {
                appointmentID = app.getId();
                appTime = app.getStart().atZone(ZoneId.systemDefault()).toLocalDateTime();
                return true;
            }

        }
        return false; }

    /***
     * Navigates to the add appointment page.
     * @param actionEvent
     * @throws IOException
     */
    public void addAppointmentHandler(ActionEvent actionEvent) throws IOException {
            try{
                Parent root= FXMLLoader.load(getClass().getResource("/com/example/software2/AddAppointment.fxml"));

                Stage stage= (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
                Scene scene= new Scene(root);
                stage.setTitle("Add Appointment");
                stage.setScene(scene);
                stage.show();
            }
            catch (IOException e){
                e.printStackTrace();
            }
    }

    /***
     * Navigates to the add customer page.
     * @param actionEvent
     * @throws IOException
     */
    public void addCustomerHandler(ActionEvent actionEvent) throws IOException{
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/software2/AddCustomer.fxml"));
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

    /***
     * Navigates to the modify appointment page. Displays an alert box if an appointment isn't selected.
     * @param actionEvent
     */
    public void modifyAppointmentHandler(ActionEvent actionEvent){
        if(appointmentsTable.getSelectionModel().getSelectedItem()== null){
            Alert alert= new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Appointment Not Selected");
            alert.setContentText("Select an Appointment to Modify");
            alert.showAndWait();
        }

        else{
            modifyAppointment =  appointmentsTable.getSelectionModel().getSelectedItem();

                try{
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/software2/ModifyAppointment.fxml"));
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
        }

    /***
     * Navigates to the modify customer page. Displays an alert box if a customer isn't selected.
     * @param actionEvent
     */

    public void modifyCustomerHandler(ActionEvent actionEvent){
        if(customersTable.getSelectionModel().getSelectedItem()== null){
            Alert alert= new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Customer Not Selected");
            alert.setContentText("Select a Customer to Modify");
            alert.showAndWait();
        }
        else {
            modifyCustomer=customersTable.getSelectionModel().getSelectedItem();
            try{
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/software2/ModifyCustomer.fxml"));
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
        }

    /***
     * Deletes the selected appointment. Displays an alert box if an appointment isn't selected.
     * Displays a success message if an appointment is selected and deleted.
     * @param actionEvent
     */
    public void deleteAppointmentHandler(ActionEvent actionEvent) {
        AppointmentsDAO dao = new AppointmentsDAO();
        if (appointmentsTable.getSelectionModel().getSelectedItem() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("No Appointment Selected");
            alert.setContentText("You must select an appointment to delete");
            alert.showAndWait();
            return;
        }
        if (appointmentsTable.getSelectionModel().getSelectedItem() != null) {
            int id = appointmentsTable.getSelectionModel().getSelectedItem().getId();
            String type = appointmentsTable.getSelectionModel().getSelectedItem().getType();
            dao.deleteAppointment(appointmentsTable.getSelectionModel().getSelectedItem().getId());
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Appointment Deleted Successfully");
            alert.setContentText(" Appointment ID:" + id + "  of type" + "has been deleted");
            alert.showAndWait();
            appointmentsTable.setItems(dao.getAllAppointments());
        }
    }

    /***
     * Deletes the selected customer if that customer does not have any appointments. Otherwise it will display an error message.
     * @param actionEvent
     * @throws SQLException
     */
    public void deleteCustomerHandler(ActionEvent actionEvent) throws SQLException {
        AppointmentsDAO dao= new AppointmentsDAO();
        CustomerDAO customerDAO= new CustomerDAO();

        int apps;

        if(customersTable.getSelectionModel().getSelectedItem()==null){
            Alert alert= new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("No Customer Selected");
            alert.setContentText("You must select a customer to delete");
            alert.showAndWait();
            return;
        }
        try{
            Customers customer=  customersTable.getSelectionModel().getSelectedItem();
            int id= customer.getCustomer_ID();
            System.out.println(id);
             apps= dao.getAllCustomerAppointmentsByID(id);
            if(apps>0){
                Alert alert= new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("This customer has associated Appointments");
                alert.setContentText("You must delete the associated Appointments with this customer to delete.");
                alert.showAndWait();
                return;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        if(apps==0){
            Alert alert= new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Are you sure you want to delete this Customer?");
            alert.setContentText("This customer currently has 0 remaining appointments");
            alert.showAndWait();
            ButtonType okButton = new ButtonType("OK");
            ButtonType cancelButton = new ButtonType("Cancel", ButtonType.CANCEL.getButtonData());
            alert.getButtonTypes().setAll(okButton, cancelButton);
            ButtonType result = alert.showAndWait().orElse(ButtonType.CANCEL);
            if(result==okButton){
                Customers customer=  customersTable.getSelectionModel().getSelectedItem();
                int id= customer.getCustomer_ID();
                 customerDAO.deleteCustomer(id);
                Alert alert2= new Alert(Alert.AlertType.CONFIRMATION);
                alert2.setHeaderText("Delete Successful");
                alert2.setContentText("Customer with ID:"+ id+ "has been successfully deleted");
                alert2.showAndWait();
                customersTable.setItems(customerDAO.getAllCustomers());
            }
        }




    }

    /***
     * Filters the appointments in the appointments table by the current week.
     * Uses a radiobutton.
     * @param actionEvent
     */
    public void filterAppointmentsByWeek(ActionEvent actionEvent){
        ObservableList<Appointments> currentMonthApps= FXCollections.observableArrayList();
        LocalDateTime currentWeek= LocalDateTime.now(ZoneId.systemDefault());
        LocalDateTime startOfCurrentWeek= currentWeek.with(TemporalAdjusters.previousOrSame(DayOfWeek.SUNDAY));
        LocalDateTime endOfCurrentWeek = currentWeek.with(TemporalAdjusters.nextOrSame(DayOfWeek.SATURDAY));

        AppointmentsDAO appointmentsDAO= new AppointmentsDAO();
        for(Appointments app: appointmentsDAO.getAllAppointments()){
            if(  !app.getStart().isBefore(startOfCurrentWeek) && !app.getStart().isAfter(endOfCurrentWeek)){
              //  app.getStart().isEqual(startOfCurrentWeek)
                currentMonthApps.add(app);
            }
        }
        appointmentsTable.setItems(currentMonthApps);
    }

    /***
     * Filters the appointments in the appointments table by the current month.
     * Uses a radiobutton.
     * @param actionEvent
     */
    public void filterAppointmentsByMonth(ActionEvent actionEvent){
        ObservableList<Appointments> currentMonthApps= FXCollections.observableArrayList();
        LocalDate currentMonth= LocalDate.now(ZoneId.systemDefault());

        AppointmentsDAO appointmentsDAO= new AppointmentsDAO();
        for(Appointments app: appointmentsDAO.getAllAppointments()){
            if(app.getStart().getMonth().equals(currentMonth.getMonth())){
                currentMonthApps.add(app);
            }
        }
        appointmentsTable.setItems(currentMonthApps);
    }

    /***
     * Returns all the appointments in the appointments table.
     * Uses a radiobutton.
     * @param actionEvent
     */
    public void viewAllAppointments(ActionEvent actionEvent){
        AppointmentsDAO dao=new AppointmentsDAO();
    appointmentsTable.setItems(dao.getAllAppointments());
    }

    /***
     * Navigates to the reports page.
     * @param actionEvent
     */
    public void viewReports(ActionEvent actionEvent){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/software2/Reports.fxml"));
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

    /***
     * Returns to the logIn page.
     * @param actionEvent
     */
    public void logoutHandler(ActionEvent actionEvent){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/software2/Login.fxml"));
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
}
