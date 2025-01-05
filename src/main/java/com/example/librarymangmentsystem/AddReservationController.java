package com.example.librarymangmentsystem;
import com.example.librarymangmentsystem.models.Books;
import com.example.librarymangmentsystem.models.Reservation;
import com.example.librarymangmentsystem.models.User;
import com.example.librarymangmentsystem.models.services.BookDOAImp;
import com.example.librarymangmentsystem.models.services.ResDAOImp;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.IOException;
import java.time.LocalDate;

public class AddReservationController {

    @FXML
    private TextField bookid;
    @FXML
    private TextField bookname;

    @FXML
    private TextField uid;
    @FXML
    private TextField uname;


    @FXML
    private DatePicker resdate;

    @FXML
    private DatePicker returndate;
    @FXML
    private Button back;

    @FXML
    private TableView<Books> tableforbook;

    @FXML
    private TableColumn<Books, Integer> column1;
    @FXML
    private TableColumn<Books, String> column2;


    @FXML
    private void initialize() {

        column1.setCellValueFactory(new PropertyValueFactory<>("id"));
        column2.setCellValueFactory(new PropertyValueFactory<>("bookName"));

        tableforbook.setEditable(true);

        column1.setCellFactory(TextFieldTableCell.forTableColumn(new javafx.util.converter.IntegerStringConverter()));
        column1.setOnEditCommit(event -> {
            Books books = event.getRowValue();
            books.setId(event.getNewValue());

        });

        column2.setCellFactory(TextFieldTableCell.forTableColumn());
        column2.setOnEditCommit(event -> {
            Books books = event.getRowValue();
            books.setBookName(event.getNewValue());

        });



        loadTableData();
    }

    private void loadTableData() {
        BookDOAImp bookDOAImp = new BookDOAImp();
        ObservableList<Books> books = FXCollections.observableArrayList(bookDOAImp.getAll());
        tableforbook.setItems(books);
    }

    public void addRes(ActionEvent ev) {
        try {
            int bookId = Integer.parseInt(bookid.getText().trim());
            int userId = Integer.parseInt(uid.getText().trim());
            String bookNameInput = bookname.getText().trim();
            LocalDate reservationDate = resdate.getValue();
            LocalDate returnDate = returndate.getValue();
            String userName = uname.getText().trim();


            if (returnDate != null && reservationDate != null && returnDate.isBefore(reservationDate)) {
                JOptionPane.showMessageDialog(null, "Return date must be after the reservation date!");
                return;
            }

            ResDAOImp resDAO = new ResDAOImp();
            Books book = resDAO.getBookById(bookId);
            User user=resDAO.getUserById(userId);

            if (book == null || !book.getBookName().equalsIgnoreCase(bookNameInput)) {
                JOptionPane.showMessageDialog(null, "Error: Book ID and name do not match. Please check the entered information!");
                return;
            }

            if (user == null || !user.getUsername().equalsIgnoreCase(userName)) {
                JOptionPane.showMessageDialog(null, "Error: User ID and name do not match. Please check the entered information!");
                return;
            }

            Reservation reservation = new Reservation();
            reservation.setBookName(book.getBookName().trim());
            reservation.setResDate(reservationDate.toString().trim());
            reservation.setReturnDate(returnDate.toString().trim());
            reservation.setBookid(book);
            reservation.setUser(user);
            reservation.setUName(userName);
            reservation.setAvailable("reserved");

            boolean success = resDAO.saveReservation(reservation);

            if (success) {
                JOptionPane.showMessageDialog(null, "Reservation added successfully!");
                //   clearFields();
            } else {
                JOptionPane.showMessageDialog(null, "Error saving reservation.");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Invalid ID format!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "An error occurred: " + e.getMessage());
        }
    }

    private void clearFields() {
        bookid.clear();
        bookname.clear();
        resdate.setValue(null);
        returndate.setValue(null);
        uname.clear();
        uid.clear();
    }


    @FXML
    public void backToHome(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("dashboard.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) back.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    @FXML
    public void GoReservations(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Reservations.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) back.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    @FXML
    public void GoHistory(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("History.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) back.getScene().getWindow();
        stage.setScene(new Scene(root));
    }
}