package com.example.software2;

import com.example.software2.DAO.AppointmentsDAO;
import com.example.software2.DAO.FirstLevelDivisionDAO;
import com.example.software2.Models.Appointments;
import com.example.software2.helper.JDBC;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.*;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Class that starts the application
 */
public class AppointmentApplication extends Application {

    public static ResourceBundle rb;

    /**
     * Start method that loads the logInPage.
     * @param stage
     * @throws IOException
     */
    @Override
    public void start(Stage stage) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(AppointmentApplication.class.getResource("Login.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle(rb.getString("heading"));
        stage.setScene(scene);
        stage.show();


    }

    /**
     * Main method that connects and disconnects to the DB.
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        JDBC.openConnection();

         rb = ResourceBundle.getBundle("/com/example/software2/Nat", Locale.getDefault());
        AppointmentsDAO dao= new AppointmentsDAO();
        System.out.println(dao.getAllCustomerAppointmentsByID(1));
        FirstLevelDivisionDAO fdao= new FirstLevelDivisionDAO();
        System.out.println(fdao.getIDByState("Alaska"));


        AppointmentsDAO appointmentsDAO=new AppointmentsDAO();
        ObservableList<Appointments> applist=appointmentsDAO.getAllAppointments();
        ObservableList<LocalDateTime> appTimes= FXCollections.observableArrayList();
        for(Appointments app: applist){
            appTimes.add(app.getStart());
            appTimes.add(app.getEnd());
        }
        System.out.println(appTimes);
        launch();
        LocalDate today=LocalDate.now();
        LocalTime todaytime= LocalTime.of(8,0);
        ZonedDateTime time =ZonedDateTime.of(today,todaytime, ZoneId.of("America/New_York"));

        LocalTime times=LocalTime.from(time);
        System.out.println(times);
        JDBC.closeConnection();

    }
}