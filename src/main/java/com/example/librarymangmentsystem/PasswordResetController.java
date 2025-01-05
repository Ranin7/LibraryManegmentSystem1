package com.example.librarymangmentsystem;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.Locale;
import java.util.regex.Pattern;
import com.example.librarymangmentsystem.models.services.UserDAOImpl;
import com.example.librarymangmentsystem.util.EmailService;

public class PasswordResetController {

    @FXML
    private TextField emailField;
    @FXML
    private PasswordField newPasswordField;
    @FXML
    private PasswordField confirmPasswordField;
    @FXML
    private TextField codeField;

    private static String verificationCode;
    private static String staticUserEmail;
    private static String staticNewPassword;

    @FXML
    private void handleResetPasswordClick() {
        String userEmail = emailField.getText().trim();
        String newPassword = newPasswordField.getText().trim();
        String confirmPassword = confirmPasswordField.getText().trim();

        if (userEmail.isEmpty() || newPassword.isEmpty() || confirmPassword.isEmpty()) {
            showAlert(AlertType.ERROR, "Error", "Please ensure all fields are filled out correctly before submitting.");
            return;
        }

        if (!newPassword.equals(confirmPassword)) {
            showAlert(AlertType.ERROR, "Error", "Passwords do not match.");
            return;
        }

        if (!isValidPassword(newPassword)) {
            showAlert(AlertType.ERROR, "Error", "Password must be at least 6 characters long, contain at least one digit, one letter, and one special character.");
            return;
        }

        if (!isValidEmail(userEmail)) {
            showAlert(AlertType.ERROR, "Error", "Invalid email address.");
            return;
        }

        UserDAOImpl userDAO = new UserDAOImpl();
        if (!userDAO.emailExists(userEmail)) {
            showAlert(AlertType.ERROR, "Error", "This email is not registered!");
            return;
        }

        verificationCode = generateVerificationCode();
        staticUserEmail = userEmail;
        staticNewPassword = newPassword;

        sendVerificationCodeToEmail(userEmail, verificationCode);
        showAlert(AlertType.INFORMATION, "Password Reset", "A verification code has been sent to your email.");
        navigateToVerificationPage();
    }

    private String generateVerificationCode() {
        return String.format(Locale.ENGLISH, "%06d", (int) (Math.random() * 1000000));
    }

    private void sendVerificationCodeToEmail(String email, String verificationCode) {
        EmailService.sendVerificationCode(email, verificationCode);
    }

    @FXML
    private void handleVerificationComplete() {
        String enteredCode = codeField.getText().trim();

        if (verificationCode != null && verificationCode.equals(enteredCode)) {
            UserDAOImpl userDAO = new UserDAOImpl();
            boolean isUpdated = userDAO.updatePassword(staticUserEmail, staticNewPassword);

            if (isUpdated) {
                showAlert(AlertType.INFORMATION, "Verification", "Verification completed successfully. Password updated.");
                navigateToLoginPage();
            } else {
                showAlert(AlertType.ERROR, "Error", "Failed to update password.");
            }
        } else {
            showAlert(AlertType.ERROR, "Error", "Invalid verification code.");
        }
    }

    private void navigateToVerificationPage() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Verification.fxml"));
            Scene verificationScene = new Scene(loader.load());
            Stage currentStage = (Stage) emailField.getScene().getWindow();
            currentStage.setScene(verificationScene);
        } catch (IOException e) {
            e.printStackTrace();
            showAlert(AlertType.ERROR, "Error", "Failed to load verification page.");
        }
    }

    private void navigateToLoginPage() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Login.fxml"));
            Scene loginScene = new Scene(loader.load());
            Stage currentStage = (Stage) codeField.getScene().getWindow();
            currentStage.setScene(loginScene);
        } catch (IOException e) {
            e.printStackTrace();
            showAlert(AlertType.ERROR, "Error", "Failed to load login page.");
        }
    }

    private void showAlert(AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private boolean isValidEmail(String email) {
        return email != null && email.contains("@") && email.contains(".");
    }

    private boolean isValidPassword(String password) {
        String regex = "^(?=.*[0-9])(?=.*[a-zA-Z])(?=.*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?]).{6,}$";
        return Pattern.matches(regex, password);
    }
}

