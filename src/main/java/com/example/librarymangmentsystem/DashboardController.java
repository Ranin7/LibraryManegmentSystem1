package com.example.librarymangmentsystem;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Set;

public class DashboardController implements Initializable {

    @FXML
    private Button addBookB;
    @FXML
    private Button addReservationB;
    @FXML
    private Button viewDetailsB;
    @FXML
    private Button allBooksB;
    @FXML
    private Button historyB;
    @FXML
    private Button logoutB;
    @FXML
    private Button reservation;
    @FXML
    private Button updateBook;
    @FXML
    private Button createAccountB;
    @FXML
    private Button HistoryByBookB;


    private Set<Integer> userPermissionIds;
    private String userRole;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        UserSession userSession = UserSession.getInstance();
        this.userRole = userSession.getUserRole();
        this.userPermissionIds = userSession.getUserPermissionIds();

        System.out.println("User Role: " + userRole);
        System.out.println("User Permissions: " + userPermissionIds);

        checkPermissions();
    }




        private void checkPermissions() {
            if (addBookB != null) addBookB.setDisable(!userPermissionIds.contains(1));  // Add Book
            if (addReservationB != null) addReservationB.setDisable(!userPermissionIds.contains(10));  // Add Reservation
            if (viewDetailsB != null) viewDetailsB.setDisable(!userPermissionIds.contains(15));  // View Details
            if (allBooksB != null) allBooksB.setDisable(!userPermissionIds.contains(14));  // All Books
            if (historyB != null) historyB.setDisable(!userPermissionIds.contains(11));  // View History
            if (createAccountB != null) createAccountB.setDisable(!userPermissionIds.contains(16));  // Create Account (only for Admin)
            if (updateBook != null) updateBook.setDisable(!userPermissionIds.contains(9));  // UpdateBook
            if (HistoryByBookB != null) HistoryByBookB.setDisable(!userPermissionIds.contains(12));  // historyByBook
            if (reservation != null) reservation.setDisable(!userPermissionIds.contains(13));  // reservation
        }




    private boolean hasPermission(int permissionId) {
        if (UserSession.getInstance().hasPermission(permissionId)) {
            return true;
        } else {
            showPermissionError("You do not have permission to access this page.");
            return false;
        }
    }

    private void showPermissionError(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Permission Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    public void goToAddBook() throws IOException {
        if (hasPermission(1)) {
            navigateToPage("addBook.fxml");
        }
    }

    @FXML
    public void goToAddReservation() throws IOException {
        if (hasPermission(10)) {
            navigateToPage("AddReservation.fxml");
        }
    }

    @FXML
    public void goToViewDetails() throws IOException {
        if (hasPermission(15)) {
            navigateToPage("BookDetails.fxml");
        }
    }

    @FXML
    public void goToAllBooks() throws IOException {
        if (hasPermission(14)) {
            navigateToPage("AllBooks.fxml");
        }
    }

    @FXML
    public void goToHistory() throws IOException {
        if (hasPermission(11)) {
            navigateToPage("History.fxml");
        }
    }

    private void navigateToPage(String fxmlFile) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
        Parent root = loader.load();
        Stage stage = (Stage) addBookB.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    @FXML
    public void logout() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("WelcomeScreen.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) logoutB.getScene().getWindow();
        stage.setScene(new Scene(root));
    }



@FXML
    public void goToBooksView() throws IOException {
    if (hasPermission(9)) {
        navigateToPage("booksView.fxml");
    }
}

    @FXML
    public void goToReservation() throws IOException {
        if (hasPermission(13)) {
            navigateToPage("Reservations.fxml");
        }
    }

        @FXML
        public void goToCreateAccount () throws IOException {
            if (hasPermission(16)) {
                navigateToPage("createAccount.fxml");
            }
        }



}
