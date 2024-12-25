package com.example.librarymangmentsystem;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class HomeController {
    @FXML
    private Button addBookB;

    @FXML
    public void AddBooks(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("addBook.fxml"));
        Parent root = loader.load();

        Stage stage = (Stage) addBookB.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    @FXML
    public void viewDetails(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ViewDetails.fxml"));
        Parent root = loader.load();

        Stage stage = (Stage) addBookB.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    @FXML
    public void goToBooksView(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("booksView.fxml"));
        Parent root = loader.load();

        // Transition to the book list view
        Stage stage = (Stage) addBookB.getScene().getWindow();
        stage.setScene(new Scene(root));
    }


@FXML
public void goToAddReservation(ActionEvent event) throws IOException {
    FXMLLoader loader = new FXMLLoader(getClass().getResource("AddReservation.fxml"));
    Parent root = loader.load();

    // Transition to the book list view
    Stage stage = (Stage) addBookB.getScene().getWindow();
    stage.setScene(new Scene(root));
}

    @FXML
    public void goToReservation(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Reservations.fxml"));
        Parent root = loader.load();

        // Transition to the book list view
        Stage stage = (Stage) addBookB.getScene().getWindow();
        stage.setScene(new Scene(root));
    }
    @FXML
    public void goToCreateAccount(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("createAccount.fxml"));
        Parent root = loader.load();

        // Transition to the book list view
        Stage stage = (Stage) addBookB.getScene().getWindow();
        stage.setScene(new Scene(root));
    }
}