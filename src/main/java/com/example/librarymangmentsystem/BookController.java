package com.example.librarymangmentsystem;

import com.example.librarymangmentsystem.models.Books;
import com.example.librarymangmentsystem.models.Role;
import com.example.librarymangmentsystem.models.services.BookDOAImp;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;

public class BookController {
    @FXML
    private Button groupbook;
    @FXML
    private Button back1;
    @FXML
    private TextField txtname,txtauther,txtgenre;
    @FXML
    private DatePicker txtpublication;
    @FXML
    private CheckBox avalibl;




    @FXML
    public void showAllBooks(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("booksView.fxml"));
        Parent root = loader.load();

        Stage stage = (Stage) groupbook.getScene().getWindow();
        stage.setScene(new Scene(root));
    }
    public void showHomePage(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("HomePage.fxml"));
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

            Books book = new Books();
            book.setBookName(name);
            book.setAuthor(author);
            book.setGenre(genre);
            book.setPublicationYear(publication);
            book.setAvailable(avalibl.isSelected() ? "Yes" : "No");

            BookDOAImp bookDOA = new BookDOAImp();
            bookDOA.save(book);

            txtname.clear();
            txtauther.clear();
            txtgenre.clear();
            txtpublication.setValue(null);
        }

}