package com.example.software2.Controllers;

import com.example.software2.AppointmentApplication;
import com.example.software2.DAO.AppointmentsDAO;
import com.example.software2.DAO.UserDao;
import com.example.software2.Models.Users;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

/****
 * Controller that handles logIn functionality.
 */
public class LoginController implements Initializable {

    @FXML
    public Button loginButton;
    @FXML
    public TextField passwordField;
    @FXML
    public TextField usernameField;
    @FXML
    public Label timezoneLabel;
    @FXML
    public Label userNameLabel;
    @FXML
    public Label passwordLabel;
    @FXML
    public Label zoneIDLabel;
    @FXML
    public Label headerLabel;

    private static Users userData;


    /****
     * Initializes the LoginController which is reponsible for the functionality of the logIn Page.
     * Changes Labels and the alert box between English and French based on the users language settings.
     * @Param url, resourcebundle
     */

    @Override
    public void initialize(URL url,ResourceBundle resourceBundle){
           timezoneLabel.setText(String.valueOf(ZoneId.systemDefault()));



        zoneIDLabel.setText(AppointmentApplication.rb.getString("zoneIDLabel"));
        headerLabel.setText(AppointmentApplication.rb.getString("headerLabel"));
        userNameLabel.setText(AppointmentApplication.rb.getString("userNameLabel"));
        passwordLabel.setText(AppointmentApplication.rb.getString("passwordLabel"));
        loginButton.setText(AppointmentApplication.rb.getString("loginButton"));
           System.out.println(AppointmentApplication.rb.getString("title"));


        System.out.println(AppointmentApplication.rb.getString("title"));


    }
/** Returns a User object
 *  @return userData  */
    public static Users getUser(){
        return userData;
    }

    /****
     * Appends the param to the login_activity.txt file
     * @param log
     */
    private static void addLogToFile(String log) {
        Path logFilePath = Paths.get("login_activity.txt");

        try  {
            PrintWriter writer = new PrintWriter(new FileWriter(logFilePath.toFile(), true));

            writer.println(log);
            writer.close();
            System.out.println("Log entry appended successfully.");
        } catch (IOException e) {
            System.err.println("Error appending log entry to file: " + e.getMessage());
        }

    }

    /****
     * Create's a log string and calls addToLog method to append the log. Appends the username, date and if the log was successful or not.
     * @param username
     * @param success
     */
    public static void getLoginAttempt(String username, boolean success) {

        Date now = new Date();

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String timestamp = dateFormat.format(now);

        String logEntry =  " Username: " + username + ", Successful LogIn?: " + success + " "+timestamp;

        addLogToFile(logEntry);
    }

    /****
     * Determines if the username and password is valid from the Users table. Uses alert box's to display error messages.
     * @param actionEvent
     * @throws IOException
     */
    public void checkUserNameandPasswordHandler(ActionEvent actionEvent) throws IOException {
        UserDao userDao= new UserDao();
        List<Users> userList= userDao.getUsers();
        String username= usernameField.getText();
        String password= passwordField.getText();
        if(username.isEmpty()||password.isEmpty()){
            Alert alert= new Alert(Alert.AlertType.ERROR);
            alert.initStyle(StageStyle.UTILITY);
            alert.initModality(Modality.APPLICATION_MODAL);
            alert.setTitle(AppointmentApplication.rb.getString("missinguserorpass"));
            alert.setHeaderText(AppointmentApplication.rb.getString("missinguserorpass"));
           alert.setContentText(AppointmentApplication.rb.getString("missingMessage"));
            ButtonType okButton= new ButtonType("OK");
            alert.showAndWait();
        }
        if(username.isEmpty()&&!password.isEmpty()){
            getLoginAttempt("No Username Entered",false);

        }

        else if(!username.isEmpty()&& password.isEmpty()){
            getLoginAttempt(username,false);

        }
        boolean match=false;
        for (Users user: userList){
            System.out.println(username);
            System.out.println(user.getUser_Name());


            if (user.getPassword().equals(password) && user.getUser_Name().equals(username)) {
                match = true;
                userData=user;
                break;
            }

            }
        if(match){
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/software2/Appointments.fxml"));
                Parent root1 = loader.load();
                CustomerandAppointmentController custAppController = loader.getController();
                custAppController.setUserData(userData);
                getLoginAttempt(username,true);



                LocalDateTime now = LocalDateTime.now();
                AppointmentsDAO appointmentsDAO = new AppointmentsDAO();

                userDao.getAllUserAppointmentsByID(userData.getId());

                boolean result = custAppController.userAppointmentsIn15Minute(  userDao.getAllUserAppointmentsByID(userData.getId()), now);
                custAppController.getBooleanResult(result);

                Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                Scene scene = new Scene(root1);
                stage.setTitle("Customer/Appointments");
                stage.setScene(scene);
                stage.show();

            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        if(!match){
            Alert alert= new Alert(Alert.AlertType.ERROR);
            alert.initStyle(StageStyle.UTILITY);
            alert.initModality(Modality.APPLICATION_MODAL);
            alert.setTitle(AppointmentApplication.rb.getString("title"));
            alert.setHeaderText(AppointmentApplication.rb.getString("title"));
            alert.setContentText(AppointmentApplication.rb.getString("message"));
            ButtonType okButton= new ButtonType("OK");
            alert.showAndWait();
            if(username.isEmpty()){
                getLoginAttempt("No Username Entered",false);

            }
        }
    }


}