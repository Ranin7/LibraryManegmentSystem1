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
    private void handleLibrarianButton() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Login.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = (Stage) librarianButton.getScene().getWindow();
        stage.setTitle("Login!");
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    private void GoHome() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("allBooks.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = (Stage) librarianButton.getScene().getWindow();
        stage.setTitle("Login!");
        stage.setScene(scene);
        stage.show();
    }
}

