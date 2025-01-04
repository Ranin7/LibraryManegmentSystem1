package com.example.librarymangmentsystem;

import com.example.librarymangmentsystem.models.Books;
import com.example.librarymangmentsystem.models.Reservation;
import com.example.librarymangmentsystem.models.Role;
import com.example.librarymangmentsystem.models.services.ResDAOImp;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javax.swing.*;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Arrays;

public class AddReservationController {

    @FXML private TextField bookid;
    @FXML private TextField bookName;
    @FXML private TextField uname;
    @FXML private TextField uid;
    @FXML private DatePicker resdate;
    @FXML private DatePicker returndate;
    @FXML private Button back;

    @FXML
    public void addRes(ActionEvent ev) {
        try {
            int bookId = Integer.parseInt(bookid.getText());
            String userNameValue = uname.getText();
            LocalDate reservationDate = resdate.getValue();
            LocalDate returnDate = returndate.getValue();

            // Validate inputs
            if (userNameValue.isEmpty() || reservationDate == null || returnDate == null) {
                JOptionPane.showMessageDialog(null, "Please fill in all fields!");
                return;
            }

            if (returnDate.isBefore(reservationDate)) {
                JOptionPane.showMessageDialog(null, "Return date must be after the reservation date!");
                return;
            }

            // Fetch book and user from the database
            ResDAOImp resDAO = new ResDAOImp();
            Books book = resDAO.getBookById(bookId);
            Role.User user = resDAO.getUserById(Integer.parseInt(uid.getText()));

            // Check if book and user are valid
            if (book == null) {
                JOptionPane.showMessageDialog(null, "Book not found!");
                return;
            }
            if (user == null) {
                JOptionPane.showMessageDialog(null, "User not found!");
                return;
            }

            // Create a new Reservation object and populate it with the fetched data
            Reservation reservation = new Reservation();
            reservation.setBookName(book.getBookName()); // Get book name from the Book entity
            reservation.setUName(user.getUsername()); // Get username from the User entity
            reservation.setResDate(reservationDate.toString());
            reservation.setReturnDate(returnDate.toString());

            // Set book and user references in the reservation
            reservation.setBookid(book); // Set the Book reference
            reservation.setUseri(user);  // Set the User reference

            // Save the reservation using the DAO implementation
            boolean success = resDAO.saveReservation(reservation);

            if (success) {
                JOptionPane.showMessageDialog(null, "Reservation added successfully!");
                clearFields();
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
        bookName.clear();
        uname.clear();
        uid.clear();
        resdate.setValue(null);
        returndate.setValue(null);
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