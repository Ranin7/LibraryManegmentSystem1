package com.example.librarymangmentsystem;

import com.example.librarymangmentsystem.models.Books;
import com.example.librarymangmentsystem.util.HibernateUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.io.IOException;

public class ViewDetailsController {

    @FXML
    private Button backButton;

    @FXML
    public void backToHome(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("AllBooks.fxml"));
        Parent root = loader.load();

        Stage stage = (Stage) backButton.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    @FXML
    private Label titleLabel;

    @FXML
    private Label authorLabel;

    @FXML
    private Label genreLabel;

    @FXML
    private Label publicationYearLabel;

    @FXML
    private Label CheckAvailability;

    @FXML
    private ImageView BookImageView;

    private int bookID;

    @FXML
    public void initialize(int bookID) {

        this.bookID = bookID;
        loadBookDetails();

    }
    private void loadBookDetails() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Books> query = session.createQuery("from Books where id = :bookID" , Books.class);
            query.setParameter("bookID",bookID);
            Books books = query.uniqueResult();

            if (books != null) {
                titleLabel.setText(books.getBookName());
                authorLabel.setText(books.getAuthor());
                genreLabel.setText(books.getGenre());
                publicationYearLabel.setText(books.getPublicationYear());
                CheckAvailability.setText(books.getAvailable());

                if (books.getImage() != null) {
                    Image image = new Image(new java.io.ByteArrayInputStream(books.getImage()));
                    BookImageView.setImage(image);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
