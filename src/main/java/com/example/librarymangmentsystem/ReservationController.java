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


import javax.swing.*;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;


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

    private ObservableList<Reservation> reservationList;

    @FXML
    private void initialize() {
        columnB1.setCellValueFactory(new PropertyValueFactory<>("BookName"));
        columnU.setCellValueFactory(new PropertyValueFactory<>("UName"));
        columnRD.setCellValueFactory(new PropertyValueFactory<>("ResDate"));
        columnRED.setCellValueFactory(new PropertyValueFactory<>("ReturnDate"));
        columnAV.setCellValueFactory(new PropertyValueFactory<>("available"));
        column3.setCellValueFactory(new PropertyValueFactory<>("AnotherB"));

        resTable.setEditable(true);

        columnB1.setCellFactory(TextFieldTableCell.forTableColumn());
        columnB1.setOnEditCommit(event -> {
            Reservation reservation = event.getRowValue();
            reservation.setBookName(event.getNewValue());
            updateReservations(reservationList);
        });

        columnU.setCellFactory(TextFieldTableCell.forTableColumn());
        columnU.setOnEditCommit(event -> {
            Reservation reservation = event.getRowValue();
            reservation.setUName(event.getNewValue());
            updateReservations(reservationList);
        });

        columnRD.setCellFactory(TextFieldTableCell.forTableColumn());
        columnRD.setOnEditCommit(event -> {
            Reservation reservation = event.getRowValue();
            reservation.setResDate(event.getNewValue());
            updateReservations(reservationList);
        });

        columnRED.setCellFactory(TextFieldTableCell.forTableColumn());
        columnRED.setOnEditCommit(event -> {
            Reservation reservation = event.getRowValue();
            if ("yes".equalsIgnoreCase(reservation.getAnotherB())) {
                JOptionPane.showMessageDialog(null, "Cannot modify Return Date for this reservation.");
                resTable.refresh();
            } else {
                reservation.setReturnDate(event.getNewValue());
                updateReservations(reservationList);
            }
        });

        columnAV.setCellFactory(TextFieldTableCell.forTableColumn());
        columnAV.setOnEditCommit(event -> {
            Reservation reservation = event.getRowValue();
            String newValue = event.getNewValue();

            if (!newValue.equalsIgnoreCase("reserved") && !newValue.equalsIgnoreCase("no")) {
                JOptionPane.showMessageDialog(null, "Invalid value! Use 'reserved' or 'no' only.");
                resTable.refresh();
                return;
            }

            reservation.setAvailable(newValue);
            updateReservations(reservationList);
        });

        column3.setCellFactory(TextFieldTableCell.forTableColumn());
        column3.setOnEditCommit(event -> {
            Reservation reservation = event.getRowValue();
            String newAnotherB = event.getNewValue();

            if ("no".equalsIgnoreCase(reservation.getAvailable())) {
                if ("yes".equalsIgnoreCase(newAnotherB)) {
                    JOptionPane.showMessageDialog(null, "Cannot set AnotherB to 'yes' when available is 'no'.");
                    resTable.refresh();
                    return;
                }
            }

            reservation.setAnotherB(newAnotherB);
            updateReservations(reservationList);
        });

        loadTableData();
        searchField.textProperty().addListener((observable, oldValue, newValue) -> filterReservations(newValue));
    }


    private void loadTableData() {
        ResDAOImp resDAO = new ResDAOImp();
        reservationList = FXCollections.observableArrayList(resDAO.getAll().stream().collect(Collectors.toList()));
        resTable.setItems(reservationList);
    }

    private void filterReservations(String searchText) {
        if (reservationList == null) return;

        ObservableList<Reservation> filteredList = FXCollections.observableArrayList();
        filteredList.setAll(
                reservationList.stream()
                        .filter(reservation -> reservation.getBookName().toLowerCase().contains(searchText.toLowerCase()) ||
                                reservation.getUName().toLowerCase().contains(searchText.toLowerCase()) ||
                                reservation.getResDate().toLowerCase().contains(searchText.toLowerCase()) ||
                                reservation.getReturnDate().toLowerCase().contains(searchText.toLowerCase()) ||
                                reservation.getAvailable().toLowerCase().contains(searchText.toLowerCase()))
                        .collect(Collectors.toList())
        );

        resTable.setItems(filteredList);

        if (searchText.isEmpty()) {
            resTable.setItems(reservationList);
        }
    }
    private void updateReservations(List<Reservation> reservations) {
        ResDAOImp resDAO = new ResDAOImp();
        reservations.stream().forEach(reservation -> resDAO.update(reservation));
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