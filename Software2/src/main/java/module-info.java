module com.example.software2 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires javafx.graphics;


    opens com.example.software2 to javafx.fxml, javafx.graphics;
    exports com.example.software2;
    opens com.example.software2.Controllers to javafx.fxml;
    exports com.example.software2.Controllers;
    opens com.example.software2.DAO to javafx.fxml;
    exports com.example.software2.DAO;
    opens com.example.software2.Models to javafx.fxml;
    exports com.example.software2.Models;
    opens com.example.software2.helper to javafx.fxml;
    exports com.example.software2.helper;
}