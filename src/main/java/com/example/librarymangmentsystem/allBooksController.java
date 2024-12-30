package com.example.librarymangmentsystem;
//commenttest
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
public class allBooksController {
    @FXML
    private Button allBook;
    @FXML
    public void viewDetails(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ViewDetails.fxml"));
        Parent root = loader.load();

        Stage stage = (Stage) allBook.getScene().getWindow();
        stage.setScene(new Scene(root));
    }
    @FXML
    public void goDashboard(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("dashboard.fxml"));
        Parent root = loader.load();

        Stage stage = (Stage) allBook.getScene().getWindow();
        stage.setScene(new Scene(root));
    }
}
