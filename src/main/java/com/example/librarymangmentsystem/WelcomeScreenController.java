package com.example.librarymangmentsystem;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class WelcomeScreenController {

    @FXML
    private Button librarianButton;
    @FXML
    private Button AdminButton;
    @FXML
    private Button userButton;

    @FXML
    private void handleLibrarianButton() throws IOException {
        int roleId = getRoleIdFromDatabase("Librarian"); // Replace with actual database check
        if (roleId == 2) {  // Check if the role ID corresponds to Librarian (2)
            UserSession.getInstance().setUserRole(2);  // Set the role as Librarian
            loadLoginScreen("Librarian");  // Pass the role as parameter
        } else {
            showRoleError("You do not have the correct permissions.");
        }
    }

    @FXML
    private void handleAdminButton() throws IOException {
        int roleId = getRoleIdFromDatabase("Admin"); // Replace with actual database check
        if (roleId == 1) {  // Check if the role ID corresponds to Admin (1)
            UserSession.getInstance().setUserRole(1);  // Set the role as Admin
            loadLoginScreen("Admin");  // Pass the role as parameter
        } else {
            showRoleError("You do not have the correct permissions.");
        }
    }


    @FXML
    private void handleUserButton() throws IOException {
        int roleId = getRoleIdFromDatabase("User"); // Replace with actual database check
        if (roleId == 3) {
            UserSession.getInstance().setUserRole(3);
            loadDashboard();
        } else {
            showRoleError("You do not have the correct permissions.");
        }
    }

    // Method to simulate fetching role ID from database based on username (for demo purposes)
    private int getRoleIdFromDatabase(String roleName) {

        if(roleName.equals("Librarian")) return 2;
        else if (roleName.equals("User")) return 3;
        else if (roleName.equals("Admin")) return 1; {

        }

        return 0;  // Simulating no match (replace with real database check)
    }

    // Show error message when role mismatch occurs
    private void showRoleError(String message) {
        // Display an error alert
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Role Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void loadLoginScreen(String role) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Login.fxml"));
        Scene scene = new Scene(loader.load());
        LoginController loginController = loader.getController();
        loginController.setRole(role);  // Set the role in the login controller
        Stage stage = (Stage) librarianButton.getScene().getWindow();
        stage.setTitle("Login");
        stage.setScene(scene);
    }


    private void loadDashboard() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("AllBooks.fxml"));
        Scene scene = new Scene(loader.load());
        Stage stage = (Stage) userButton.getScene().getWindow();
        stage.setTitle("Dashboard");
        stage.setScene(scene);
    }
}
