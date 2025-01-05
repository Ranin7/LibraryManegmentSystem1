package com.example.librarymangmentsystem;

import com.example.librarymangmentsystem.models.Reservation;
import com.example.librarymangmentsystem.util.HibernateUtil;
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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.hibernate.Session;
import org.hibernate.query.Query;
import java.io.IOException;


public class HistoryForBookController {

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
    private Button back;

    private ObservableList<Reservation> reservationsList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        columnB1.setCellValueFactory(new PropertyValueFactory<>("BookName"));
        columnU.setCellValueFactory(new PropertyValueFactory<>("UName"));
        columnRD.setCellValueFactory(new PropertyValueFactory<>("ResDate"));
        columnRED.setCellValueFactory(new PropertyValueFactory<>("ReturnDate"));
    }
    private ObservableList<Reservation> getReservationsByBookName(String bookName) {
        ObservableList<Reservation> filteredReservations = FXCollections.observableArrayList();
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Reservation> query = session.createQuery("from Reservation where bookName = :bookName", Reservation.class);
            query.setParameter("bookName", bookName);
            filteredReservations.addAll(query.list());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return filteredReservations;
    }


    @FXML
    public void loadReservationsForBook(String bookName) {
        ObservableList<Reservation> reservations = getReservationsByBookName(bookName);
        resTable.setItems(reservations);
    }


    @FXML
    public void backToViewDetails(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("AllBooks.fxml"));
        Parent root = loader.load();

        Stage stage = (Stage) back.getScene().getWindow();
        stage.setScene(new Scene(root));
    }
}
