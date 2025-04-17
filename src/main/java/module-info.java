module com.example.employee_management_system {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.employee_management_system to javafx.fxml;
    exports com.example.employee_management_system;
    exports com.example.employee_management_system.services;
    opens com.example.employee_management_system.services to javafx.fxml;
    exports com.example.employee_management_system.models;
    opens com.example.employee_management_system.models to javafx.fxml;
    exports com.example.employee_management_system.controllers;
    opens com.example.employee_management_system.controllers to javafx.fxml;
    exports com.example.employee_management_system.models.comparators;
    opens com.example.employee_management_system.models.comparators to javafx.fxml;
    exports com.example.employee_management_system.db;
    opens com.example.employee_management_system.db to javafx.fxml;
}