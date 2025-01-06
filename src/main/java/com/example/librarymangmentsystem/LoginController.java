package com.example.librarymangmentsystem;

import com.example.librarymangmentsystem.models.User;
import com.example.librarymangmentsystem.models.services.UserDAOImpl;
import com.example.librarymangmentsystem.models.interfaces.UserDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.prefs.Preferences;

public class LoginController {

    @FXML
    private TextField userNameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private CheckBox rememberMeCheckBox;

    @FXML
    private Button backButton;


    private UserDAO userDAO = new UserDAOImpl();

    private Preferences preferences = Preferences.userNodeForPackage(LoginController.class);
    private String expectedRole;

    public void setRole(String role) {
        this.expectedRole = role;  // Store the role (Admin or Librarian) passed from WelcomeScreenController
    }


    @FXML
    public void initialize() {
        String savedUsername = preferences.get("username", "");
        String savedPassword = preferences.get("password", "");
        boolean isRememberMeSelected = preferences.getBoolean("rememberMe", false);

        userNameField.setText(savedUsername);
        passwordField.setText(savedPassword);
        rememberMeCheckBox.setSelected(isRememberMeSelected);
    }

    public void handleLogin(ActionEvent event) {
        String username = userNameField.getText().trim();
        String password = passwordField.getText().trim();

        if (username.isEmpty()) {
            showAlert("Login Failed", "Username cannot be empty.", Alert.AlertType.ERROR);
            return;
        }

        User user = userDAO.getUserByUsername(username);
        if (user != null) {
            String roleName = user.getRole().getRoleName();
            // Check if the user role matches the expected role
            if (roleName.equals(expectedRole)) {
                if (roleName.equals("User")) {
                    goToPage(event, "BooksPage.fxml");
                    saveUserPreferences(username, password);
                } else if (roleName.equals("Admin") || roleName.equals("Librarian")) {
                    if (password.isEmpty()) {
                        showAlert("Login Failed", "Password cannot be empty for Admin or Librarian.", Alert.AlertType.ERROR);
                        return;
                    }
                    if (user.getPassword().equals(password)) {
                        saveUserPreferences(username, password);
                        goToPage(event, "dashboard.fxml");
                    } else {
                        showAlert("Login Failed", "Invalid password.", Alert.AlertType.ERROR);
                    }
                } else {
                    showAlert("Login Failed", "User role is not recognized.", Alert.AlertType.ERROR);
                }
            } else {
                showAlert("Login Failed", "You are attempting to log in with the wrong role. Please try again.", Alert.AlertType.ERROR);
            }
        } else {
            showAlert("Login Failed", "Invalid username.", Alert.AlertType.ERROR);
        }
    }


    private void saveUserPreferences(String username, String password) {
        if (rememberMeCheckBox.isSelected()) {
            preferences.put("username", username);
            preferences.put("password", password);
            preferences.putBoolean("rememberMe", true);
        } else {
            preferences.remove("username");
            preferences.remove("password");
            preferences.putBoolean("rememberMe", false);
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

    @FXML
    private void handleForgetPassword(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ResetPassword.fxml"));
            Scene scene = new Scene(loader.load());
            Stage stage = new Stage();
            stage.setTitle("Reset Password");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            showAlert("Navigation Error", "Failed to load the Reset Password page: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }
    @FXML
    public void backToWelcomeScreen(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("WelcomeScreen.fxml"));
        Parent root = loader.load();

        Stage stage = (Stage) backButton.getScene().getWindow();
        stage.setScene(new Scene(root));
    }
}
