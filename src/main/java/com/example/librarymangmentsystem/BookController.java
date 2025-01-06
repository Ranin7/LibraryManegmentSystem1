package com.example.librarymangmentsystem;

import com.example.librarymangmentsystem.models.Book;
import com.example.librarymangmentsystem.models.services.BookDAOImp;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDate;

public class BookController {

    @FXML
    private Button groupbook;
    @FXML
    private Button back1;
    @FXML
    private TextField txtname, txtauther, txtgenre;
    @FXML
    private DatePicker txtpublication;
    @FXML
    private CheckBox avalibl;
    @FXML
    private ImageView imageView;

    private File selectedImage;

    @FXML
    public void showAllBooks(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("booksView.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) groupbook.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    @FXML
    public void showHomePage(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("dashboard.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) back1.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    @FXML
    public void addBook(ActionEvent event) {
        String name = txtname.getText();
        String author = txtauther.getText();
        String genre = txtgenre.getText();
        LocalDate publicationDate = txtpublication.getValue();
        String publication = (publicationDate != null) ? publicationDate.toString() : "";
        String availableStatus = avalibl.isSelected() ? "Yes" : "No";

        byte[] imageData = null;
        if (selectedImage != null) {
            try {
                imageData = Files.readAllBytes(selectedImage.toPath());
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
        }

        Book books = new Book();
        books.setBookName(name);
        books.setAuthor(author);
        books.setGenre(genre);
        books.setPublicationYear(publication);
        books.setAvailable(availableStatus);
        books.setImage(imageData);
        BookDAOImp bookDAO = new BookDAOImp();
        bookDAO.save(books);

        clearFields();
    }

    private void clearFields() {
        txtname.clear();
        txtauther.clear();
        txtgenre.clear();
        txtpublication.setValue(null);
        avalibl.setSelected(false);
        imageView.setImage(null);
        selectedImage = null;
    }

    @FXML
    public void handleImageUpload(javafx.scene.input.MouseEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.gif", "*.bmp"));
        selectedImage = fileChooser.showOpenDialog(imageView.getScene().getWindow());

        if (selectedImage != null && selectedImage.exists()) {
            imageView.setImage(new Image(selectedImage.toURI().toString()));
        } else {
            showAlert("Image Upload Error", "Invalid Image", "Please select a valid image file.");
        }
    }

    private void showAlert(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
}


