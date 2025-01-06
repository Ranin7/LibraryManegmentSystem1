package com.example.librarymangmentsystem;
import com.example.librarymangmentsystem.models.Book;
import com.example.librarymangmentsystem.models.Reservation;
import com.example.librarymangmentsystem.models.User;
import com.example.librarymangmentsystem.models.services.BookDAOImp;
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
import javafx.stage.Stage;
import javax.swing.*;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

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
    private TextField searchField;

    @FXML
    private DatePicker resdate;

    @FXML
    private DatePicker returndate;
    @FXML
    private Button back;

    @FXML
    private TableView<Book> tableforbook;

    @FXML
    private TableColumn<Book, Integer> column1;
    @FXML
    private TableColumn<Book, String> column2;

    private ObservableList <Book> booksList;

    @FXML

    private void initialize() {
        column1.setCellValueFactory(new PropertyValueFactory<>("id"));
        column2.setCellValueFactory(new PropertyValueFactory<>("bookName"));

        tableforbook.setEditable(false);

        loadTableData();
        searchField.textProperty().addListener((observable, oldValue, newValue) -> filterBooks(newValue));


        tableforbook.setRowFactory(tv -> {
            TableRow<Book> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && !row.isEmpty()) {
                    Book selectedBook = row.getItem();
                    fillFieldsWithBookData(selectedBook);
                }
            });
            return row;
        });
    }

    private void fillFieldsWithBookData(Book book) {
        bookid.setText(String.valueOf(book.getId()));
        bookname.setText(book.getBookName());
    }

    private void loadTableData() {
        BookDAOImp bookDOAImp = new BookDAOImp();
        booksList = FXCollections.observableArrayList(bookDOAImp.getAll());
        tableforbook.setItems(booksList);
    }

    private void filterBooks(String searchText) {
        if (booksList == null) return;

        ObservableList<Book> filteredList = FXCollections.observableArrayList();

        for (Book book : booksList) {

            if (String.valueOf(book.getId()).contains(searchText) ||
                    book.getBookName().toLowerCase().contains(searchText.toLowerCase())) {
                filteredList.add(book);
            }
        }
        tableforbook.setItems(filteredList);

        if (searchText.isEmpty()) {
            tableforbook.setItems(booksList);
        }
    }



    public void addRes(ActionEvent ev) {
        try {
            int bookId = Integer.parseInt(bookid.getText().trim());
            int userId = Integer.parseInt(uid.getText().trim());
            String bookNameInput = bookname.getText().trim();
            LocalDate reservationDate = resdate.getValue();
            LocalDate returnDate = returndate.getValue();
            String userName = uname.getText().trim();

            ResDAOImp resDAO = new ResDAOImp();
            Book book = resDAO.getBookById(bookId);
            if (book == null || !book.getBookName().equalsIgnoreCase(bookNameInput)) {
                JOptionPane.showMessageDialog(null, "Error: Book ID and name do not match. Please check the entered information!");
                return;
            }

            List<Reservation> reservations = resDAO.getReservationsByBookId(book.getId());
            for (Reservation reservation : reservations) {
                if ("reserved".equalsIgnoreCase(reservation.getAvailable())) {
                    JOptionPane.showMessageDialog(null, "Error: This book is already reserved!");
                    clearFields();
                    return;
                }
            }

            if (returnDate != null && reservationDate != null && returnDate.isBefore(reservationDate)) {
                JOptionPane.showMessageDialog(null, "Return date must be after the reservation date!");
                return;
            }

            User user = resDAO.getUserById(userId);
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
            resDAO.updateBookStatus(book);

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