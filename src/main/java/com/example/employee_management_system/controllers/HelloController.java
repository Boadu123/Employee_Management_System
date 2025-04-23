package com.example.employee_management_system.controllers;

import com.example.employee_management_system.exceptions.InvalidDepartmentException;
import com.example.employee_management_system.models.Employee;
import com.example.employee_management_system.db.EmployeeDatabase;
import com.example.employee_management_system.models.comparators.EmployeePerformanceComparator;
import com.example.employee_management_system.models.comparators.EmployeeSalaryComparator;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.util.*;


public class HelloController {

    public StackPane employeeFormContainer;
    public StackPane searchFormsContainer;
    private EmployeeDatabase<String> database = new EmployeeDatabase<>();


    @FXML private ComboBox<String> searchOptionSelector;

    @FXML private VBox searchByDepartmentForm, searchByNameForm, searchBySalaryForm, searchByPerformanceForm;

    @FXML private TextField departmentSearchField, nameSearchField;
    @FXML private TextField minSalaryField, maxSalaryField;
    @FXML private TextField minPerformanceField;

    @FXML private TextArea searchResultsArea;


    @FXML private ComboBox<String> employeeActionSelector;

    @FXML private VBox addEmployeeForm, removeEmployeeForm, updateEmployeeForm, viewAllEmployeesForm;

    @FXML private TextField addIdField, addNameField, addDepartmentField, addSalaryField, addPerformanceField, addExperienceField;
    @FXML private CheckBox addIsActiveCheckbox;

    @FXML private TextField removeIdField;

    @FXML private TextField updateIdField, updateNameField, updateDepartmentField, updateSalaryField, updatePerformanceField, updateExperienceField;
    @FXML private CheckBox updateIsActiveCheckbox;

    @FXML private TextArea employeeDisplayArea;

    // Sort tab
    @FXML private ComboBox<String> sortOptionSelector;
    @FXML private TextArea sortResultsArea;

    @FXML
    private Label welcomeText;

    // Search Employees
    @FXML
    protected void onSearchByDepartment() {
        String department = departmentSearchField.getText().trim();
        if (department.isEmpty()) {
            searchResultsArea.setText("Please enter a department name");
            return;
        }

        try {
            List<Employee<String>> results = database.searchByDepartment(department);
            searchResultsArea.setText(formatEmployeeList(results));
        } catch (InvalidDepartmentException e) {
            searchResultsArea.setText("Error: " + e.getMessage());
        }
    }

    @FXML
    protected void onSearchByName() {
        String name = nameSearchField.getText().trim();
        if (name.isEmpty()) {
            searchResultsArea.setText("Please enter a name to search");
            return;
        }

        try {
            List<Employee<String>> results = database.searchByName(name);
            searchResultsArea.setText(formatEmployeeList(results));
        } catch (IllegalArgumentException e) {
            searchResultsArea.setText("Error: " + e.getMessage());
        }
    }

    @FXML
    protected void onSearchByPerformance() {
        try {
            double minRating = Double.parseDouble(minPerformanceField.getText().trim());
            if (minRating < 0 || minRating > 100) {
                searchResultsArea.setText("Performance rating must be between 0 and 100");
                return;
            }

            try {
                List<Employee<String>> results = database.filterByPerformance(minRating);
                searchResultsArea.setText(formatEmployeeList(results));
            } catch (IllegalArgumentException e) {
                searchResultsArea.setText("Error: " + e.getMessage());
            }
        } catch (NumberFormatException e) {
            searchResultsArea.setText("Please enter a valid performance rating (0-100)");
        }
    }

    @FXML
    protected void onSearchBySalaryRange() {
        try {
            double min = Double.parseDouble(minSalaryField.getText().trim());
            double max = Double.parseDouble(maxSalaryField.getText().trim());

            if (min < 0 || max < 0) {
                searchResultsArea.setText("Salary values cannot be negative");
                return;
            }

            if (min > max) {
                searchResultsArea.setText("Minimum salary cannot be greater than maximum salary");
                return;
            }

            try {
                List<Employee<String>> results = database.filterBySalaryRange(min, max);
                searchResultsArea.setText(formatEmployeeList(results));
            } catch (IllegalArgumentException e) {
                searchResultsArea.setText("Error: " + e.getMessage());
            }
        } catch (NumberFormatException e) {
            searchResultsArea.setText("Please enter valid salary numbers");
        }
    }

    // Manage Employees
    @FXML
    protected void onEmployeeActionSelect() {
        String action = employeeActionSelector.getValue();

        addEmployeeForm.setVisible(false);
        removeEmployeeForm.setVisible(false);
        updateEmployeeForm.setVisible(false);
        viewAllEmployeesForm.setVisible(false);

        switch (action) {
            case "Add Employee":
                addEmployeeForm.setVisible(true);
                break;
            case "Remove Employee":
                removeEmployeeForm.setVisible(true);
                break;
            case "Update Employee":
                updateEmployeeForm.setVisible(true);
                break;
            case "View All Employees":
                viewAllEmployeesForm.setVisible(true);
                onRefreshEmployeeList();
                break;
        }
    }

    @FXML
    protected void onSubmitAddEmployee() {
        String id = addIdField.getText();
        String name = addNameField.getText();
        String department = addDepartmentField.getText();
        double salary = Double.parseDouble(addSalaryField.getText());
        double performance = Double.parseDouble(addPerformanceField.getText());
        int experience = Integer.parseInt(addExperienceField.getText());
        boolean isActive = addIsActiveCheckbox.isSelected();

        Employee<String> employee = new Employee<>(id, name, department, salary, performance, experience, isActive);
        database.addEmployee(employee);
        System.out.println("Added: " + name);

        clearAddEmployeeForm(); // optional: create this method to reset fields
    }

    private void clearAddEmployeeForm() {
    }

    @FXML
    protected void onSubmitRemoveEmployee() {
        String id = removeIdField.getText();
        database.removeEmployee(id);
        System.out.println("Removed employee with ID: " + id);
    }

    @FXML
    protected void onSubmitUpdateEmployee() {
        String id = updateIdField.getText();
        if (id.isEmpty()) {
            employeeDisplayArea.setText("Error: ID field is required.");
            return;
        }

        Map<String, Object> updates = new HashMap<>();

        if (!updateNameField.getText().isEmpty())
            updates.put("name", updateNameField.getText());

        if (!updateDepartmentField.getText().isEmpty())
            updates.put("department", updateDepartmentField.getText());

        if (!updateSalaryField.getText().isEmpty()) {
            try {
                updates.put("salary", Double.parseDouble(updateSalaryField.getText()));
            } catch (NumberFormatException e) {
                employeeDisplayArea.setText("Invalid salary value.");
                return;
            }
        }

        if (!updatePerformanceField.getText().isEmpty()) {
            try {
                updates.put("performancerating", Double.parseDouble(updatePerformanceField.getText()));
            } catch (NumberFormatException e) {
                employeeDisplayArea.setText("Invalid performance rating value.");
                return;
            }
        }

        if (!updateExperienceField.getText().isEmpty()) {
            try {
                updates.put("yearsofexperience", Integer.parseInt(updateExperienceField.getText()));
            } catch (NumberFormatException e) {
                employeeDisplayArea.setText("Invalid experience value.");
                return;
            }
        }

        updates.put("isactive", updateIsActiveCheckbox.isSelected());

        boolean updated = database.updateEmployeeFields(id, updates);
        if (updated) {
            employeeDisplayArea.setText("Employee updated successfully.");
        } else {
            employeeDisplayArea.setText("Employee not found.");
        }

        System.out.println("Updated employee details with ID: " + id);

    }

    @FXML
    protected void onRefreshEmployeeList() {
        List<Employee<String>> allEmployees = database.getAllEmployees();
        StringBuilder builder = new StringBuilder();

        builder.append(String.format("%-10s %-15s %-15s %-10s %-20s %-20s %-10s%n",
                "ID", "Name", "Department", "Salary", "Performance", "Experience", "Active"));

        for (Employee<String> emp : allEmployees) {
            builder.append(String.format("%-10s %-15s %-15s %-10.2f %-20.1f %-20d %-10b%n",
                    emp.getEmployeeID(), emp.getName(), emp.getDepartment(), emp.getSalary(),
                    emp.getPerformanceRating(), emp.getYearsOfExperience(), emp.getIsActive()));
        }

        employeeDisplayArea.setText(builder.toString());
        searchResultsArea.setText(builder.toString());

    }

    private String formatEmployeeList(List<Employee<String>> employees) {
        StringBuilder builder = new StringBuilder();

        builder.append(String.format("%-10s %-15s %-15s %-10s %-20s %-20s %-10s%n",
                "ID", "Name", "Department", "Salary", "Performance", "Experience", "Active"));

        for (Employee<String> emp : employees) {
            builder.append(String.format("%-10s %-15s %-15s %-10.2f %-20.1f %-20d %-10b%n",
                    emp.getEmployeeID(), emp.getName(), emp.getDepartment(), emp.getSalary(),
                    emp.getPerformanceRating(), emp.getYearsOfExperience(), emp.getIsActive()));
        }

        return builder.toString();
    }

    @FXML
    private void initialize() {
        List<Employee<String>> allEmployees = database.getAllEmployees();
        String formattedList = formatEmployeeList(allEmployees);

        searchResultsArea.setText(formattedList);
        sortResultsArea.setText(formattedList);
    }


    @FXML
    protected void onSearchOptionSelect() {
        searchByDepartmentForm.setVisible(false);
        searchByNameForm.setVisible(false);
        searchBySalaryForm.setVisible(false);
        searchByPerformanceForm.setVisible(false);

        String selected = searchOptionSelector.getValue();
        switch (selected) {
            case "Department":
                searchByDepartmentForm.setVisible(true);
                break;
            case "Name":
                searchByNameForm.setVisible(true);
                break;
            case "Salary Range":
                searchBySalaryForm.setVisible(true);
                break;
            case "Minimum Performance":
                searchByPerformanceForm.setVisible(true);
                break;
        }
    }


    @FXML
    protected void onSortOptionSelect() {
        String selected = sortOptionSelector.getValue();
        List<Employee<String>> allEmployees = new ArrayList<>(database.getAllEmployees());

        switch (selected) {
            case "Years of Experience":
                Collections.sort(allEmployees); // Assuming Employee implements Comparable by experience
                break;
            case "Highest Salary":
                allEmployees.sort(new EmployeeSalaryComparator<>());
                break;
            case "Performance":
                allEmployees.sort(new EmployeePerformanceComparator<>());
                break;
        }

        sortResultsArea.setText(formatEmployeeList(allEmployees));
    }

}
