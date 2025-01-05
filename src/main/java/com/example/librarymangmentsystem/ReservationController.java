package com.example.librarymangmentsystem;
import com.example.librarymangmentsystem.models.Reservation;
import com.example.librarymangmentsystem.models.services.ResDAOImp;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;

import java.io.IOException;

public class ReservationController {

    @FXML
    private TableView<Reservation> resTable;

    @FXML
    private TableColumn<Reservation, String> columnB1;
    @FXML
    private TableColumn<Reservation, String> columnU;
    @FXML
    private TableColumn<Reservation, String> columnRD;
    @FXML
    private TableColumn<Reservation, String> columnRED;
    @FXML
    private TableColumn<Reservation, String> columnAV;
    @FXML
    private TableColumn<Reservation, String> column3;

    @FXML
    private TextField searchField;

    private ObservableList<Reservation> reservationsList;

    @FXML
    private void initialize() {
        columnB1.setCellValueFactory(new PropertyValueFactory<>("BookName"));
        columnU.setCellValueFactory(new PropertyValueFactory<>("UName"));
        columnRD.setCellValueFactory(new PropertyValueFactory<>("ResDate"));
        columnRED.setCellValueFactory(new PropertyValueFactory<>("ReturnDate"));
        columnAV.setCellValueFactory(new PropertyValueFactory<>("available"));
        column3.setCellValueFactory(new PropertyValueFactory<>("AnotherB"));

        resTable.setEditable(true);
        searchField.textProperty().addListener((observable, oldValue, newValue) -> filterReservations(newValue));
        columnB1.setCellFactory(TextFieldTableCell.forTableColumn());
        columnB1.setOnEditCommit(event -> {
            Reservation reservation = event.getRowValue();
            reservation.setBookName(event.getNewValue());
            updateReservation(reservation);
        });

        columnU.setCellFactory(TextFieldTableCell.forTableColumn());
        columnU.setOnEditCommit(event -> {
            Reservation reservation = event.getRowValue();
            reservation.setUName(event.getNewValue());
            updateReservation(reservation);
        });

        columnRD.setCellFactory(TextFieldTableCell.forTableColumn());
        columnRD.setOnEditCommit(event -> {
            Reservation reservation = event.getRowValue();
            reservation.setResDate(event.getNewValue());
            updateReservation(reservation);
        });

        columnRED.setCellFactory(TextFieldTableCell.forTableColumn());
        columnRED.setOnEditCommit(event -> {
            Reservation reservation = event.getRowValue();
            reservation.setReturnDate(event.getNewValue());
            updateReservation(reservation);
        });

        columnAV.setCellFactory(TextFieldTableCell.forTableColumn());
        columnAV.setOnEditCommit(event -> {
            Reservation reservation = event.getRowValue();
            reservation.setAvailable(event.getNewValue());
            updateReservation(reservation);
        });

        column3.setCellFactory(TextFieldTableCell.forTableColumn());
        column3.setOnEditCommit(event -> {
            Reservation reservation = event.getRowValue();
            reservation.setAnotherB(event.getNewValue());
            updateReservation(reservation);
        });

        loadTableData();

        searchField.textProperty().addListener((observable, oldValue, newValue) -> filterReservations(newValue));
    }

    private void loadTableData() {
        ResDAOImp resDAO = new ResDAOImp();
        reservationsList = FXCollections.observableArrayList(resDAO.getAll());
        resTable.setItems(reservationsList);
    }

    private void filterReservations(String searchText) {
        if (reservationsList == null) return;

        ObservableList<Reservation> filteredList = FXCollections.observableArrayList();

        for (Reservation reservation : reservationsList) {
            if (reservation.getBookName().toLowerCase().contains(searchText.toLowerCase()) ||
                    reservation.getUName().toLowerCase().contains(searchText.toLowerCase()) ||
                    reservation.getResDate().toLowerCase().contains(searchText.toLowerCase()) ||
                    reservation.getReturnDate().toLowerCase().contains(searchText.toLowerCase())) {
                filteredList.add(reservation);
            }
        }

        resTable.setItems(filteredList);

        if (searchText.isEmpty()) {
            resTable.setItems(reservationsList);
        }
    }

    private void updateReservation(Reservation reservation) {
        ResDAOImp resDAO = new ResDAOImp();
        resDAO.update(reservation);
    }

    @FXML
    private void GoBack(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("dashboard.fxml"));
            Scene goback = new Scene(fxmlLoader.load());
            Stage currentStage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            currentStage.setScene(goback);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
