package com.example.librarymangmentsystem;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class WelcomeScreenController {

    @FXML
    private Button librarianButton;
    @FXML
    private Button userButton;

    @FXML
    private void handleLibrarianButton() throws IOException {
        UserSession.getInstance().setUserRole("Librarian");
        loadLoginScreen();
    }


    @FXML
    private void handleUserButton() throws IOException {
        UserSession.getInstance().setUserRole("User");
        loadDashboard();
    }

    private void loadLoginScreen() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Login.fxml"));
        Scene scene = new Scene(loader.load());
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
