package com.example.librarymangmentsystem;

import com.example.librarymangmentsystem.models.User;
import com.example.librarymangmentsystem.models.services.UserDAOImpl;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class CreateAccountController {

    @FXML
    private Button back;
    @FXML
    private TextField userNameField;
    @FXML
    private TextField emailField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private PasswordField confirmPasswordField;

    @FXML
    public void goToHome(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("dashboard.fxml"));
        Parent root = loader.load();

        Stage stage = (Stage) back.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    @FXML
    public void createAccount(ActionEvent event) {
        String username = userNameField.getText().trim();
        String email = emailField.getText().trim();
        String password = passwordField.getText();
        String confirmPassword = confirmPasswordField.getText();

        if (username.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            showAlert("Error", "All fields are required!", Alert.AlertType.ERROR);
            return;
        }

        if (!password.equals(confirmPassword)) {
            showAlert("Error", "Passwords do not match!", Alert.AlertType.ERROR);
            return;
        }

        if (!email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            showAlert("Error", "Invalid email format!", Alert.AlertType.ERROR);
            return;
        }

        if (!isValidPassword(password)) {
            showAlert("Error", "Password must be at least 6 characters long, and contain a mix of letters, numbers, and symbols.", Alert.AlertType.ERROR);
            return;
        }

        UserDAOImpl userDAO = new UserDAOImpl();

        if (userDAO.usernameExists(username)) {
            showAlert("Error", "Username already exists! Please choose a different username.", Alert.AlertType.ERROR);
            return;
        }

        if (userDAO.emailExists(email)) {
            showAlert("Error", "This email is already registered!", Alert.AlertType.ERROR);
            return;
        }

        User newUser = new User(username, email, password, "Librarian");
        userDAO.addUser(newUser);

        showAlert("Account Created", "Your account has been successfully created!", Alert.AlertType.INFORMATION);
    }

    private void showAlert(String title, String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private boolean isValidPassword(String password) {
        String regex = "^(?=.*[0-9])(?=.*[a-zA-Z])(?=.*[!@#$%^&*()_+\\-\\[\\]{};':\"|,.<>/?]).{6,}$";
        return password.matches(regex);
    }
}