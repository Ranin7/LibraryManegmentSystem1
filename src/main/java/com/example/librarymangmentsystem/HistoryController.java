package com.example.librarymangmentsystem;

import com.example.librarymangmentsystem.models.Reservation;
import com.example.librarymangmentsystem.models.services.ResDAOImp;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;

import java.io.IOException;

public class HistoryController {

    @FXML
    private Button back;

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
    private TextField searchField;

    private ObservableList<Reservation> reservationsList;

    @FXML
    private void initialize() {

        columnB1.setCellValueFactory(new PropertyValueFactory<>("BookName"));
        columnU.setCellValueFactory(new PropertyValueFactory<>("UName"));
        columnRD.setCellValueFactory(new PropertyValueFactory<>("ResDate"));
        columnRED.setCellValueFactory(new PropertyValueFactory<>("ReturnDate"));

        columnB1.setCellFactory(TextFieldTableCell.forTableColumn());
        columnB1.setOnEditCommit(event -> {
            Reservation reservation = event.getRowValue();
            reservation.setBookName(event.getNewValue());
        });

        columnU.setCellFactory(TextFieldTableCell.forTableColumn());
        columnU.setOnEditCommit(event -> {
            Reservation reservation = event.getRowValue();
            reservation.setUName(event.getNewValue());
        });

        columnRD.setCellFactory(TextFieldTableCell.forTableColumn());
        columnRD.setOnEditCommit(event -> {
            Reservation reservation = event.getRowValue();
            reservation.setResDate(event.getNewValue());
        });

        columnRED.setCellFactory(TextFieldTableCell.forTableColumn());
        columnRED.setOnEditCommit(event -> {
            Reservation reservation = event.getRowValue();
            reservation.setReturnDate(event.getNewValue());
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
                    reservation.getResDate().contains(searchText) ||
                    reservation.getReturnDate().contains(searchText)) {
                filteredList.add(reservation);
            }
        }
        resTable.setItems(filteredList);

        if (searchText.isEmpty()) {
            resTable.setItems(reservationsList);
        }
    }

    @FXML
    public void backToReservation(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("dashboard.fxml"));
        Parent root = loader.load();

        Stage stage = (Stage) back.getScene().getWindow();
        stage.setScene(new Scene(root));
    }
}