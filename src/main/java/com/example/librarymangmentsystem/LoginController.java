package com.example.librarymangmentsystem;

import com.example.librarymangmentsystem.models.Role;
import com.example.librarymangmentsystem.models.services.UserDAOImpl;
import com.example.librarymangmentsystem.models.interfaces.UserDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {

    @FXML
    private TextField userNameField;

    @FXML
    private PasswordField  passwordField; // Change to PasswordField

    private UserDAO userDAO = new UserDAOImpl();

    public void handleLogin(ActionEvent event) {
        String username = userNameField.getText().trim();
        String password = passwordField.getText().trim();
        if (username.isEmpty()) {
            showAlert("Login Failed", "Username cannot be empty.", Alert.AlertType.ERROR);
            return;
        }
        Role.User user = userDAO.getUserByUsername(username);
        if (user != null) {
            if (user.getRole().equals("User")) {
                goToPage(event, "BooksPage.fxml");
            }
            else if (user.getRole().equals("Admin") || user.getRole().equals("Librarian")) {
                if (password.isEmpty()) {
                    showAlert("Login Failed", "Password cannot be empty for Admin or Librarian.", Alert.AlertType.ERROR);
                    return;
                }
                if (user.getPassword().equals(password)) {
                    if (user.getRole().equals("Admin")) {
                        goToPage(event, "dashboard.fxml");
                    } else if (user.getRole().equals("Librarian")) {
                        goToPage(event, "dashboard.fxml");
                    }
                } else {
                    showAlert("Login Failed", "Invalid password.", Alert.AlertType.ERROR);
                }
            } else {
                showAlert("Login Failed", "User role is not recognized.", Alert.AlertType.ERROR);
            }
        } else {
            showAlert("Login Failed", "Invalid username.", Alert.AlertType.ERROR);
        }
    }


    private void goToPage(ActionEvent event, String fxmlFileName) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFileName));
            Scene scene = new Scene(loader.load());
            Stage stage = (Stage) userNameField.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            showAlert("Navigation Error", "Failed to load the page: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    private void showAlert(String title, String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}

