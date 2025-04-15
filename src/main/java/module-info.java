module com.example.employee_management_system {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.employee_management_system to javafx.fxml;
    exports com.example.employee_management_system;
    exports com.example.employee_management_system.utils;
    opens com.example.employee_management_system.utils to javafx.fxml;
    exports com.example.employee_management_system.models;
    opens com.example.employee_management_system.models to javafx.fxml;
}