package com.example.librarymangmentsystem;

import com.example.librarymangmentsystem.models.Book;
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
    private Button history;

    @FXML
    public void backToHome(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("AllBooks.fxml"));
        Parent root = loader.load();

        Stage stage = (Stage) backButton.getScene().getWindow();
        stage.setScene(new Scene(root));
    }


    @FXML
    public void goTohistory(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("historyForBook.fxml"));
        Parent root = loader.load();

        String bookName = titleLabel.getText();

        HistoryForBookController controller = loader.getController();
        controller.loadReservationsForBook(bookName);

        Stage stage = (Stage) history.getScene().getWindow();
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
        String userRole = UserSession.getInstance().getUserRole();

        if ("Librarian".equals(userRole)) {
            history.setVisible(true);
        } else if ("User".equals(userRole)) {
            history.setVisible(false);
        }


        this.bookID = bookID;
        loadBookDetails();

    }
    private void loadBookDetails() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Book> query = session.createQuery("from Book where id = :bookID" , Book.class);
            query.setParameter("bookID",bookID);
            Book book = query.uniqueResult();

            if (book != null) {
                titleLabel.setText(book.getBookName());
                authorLabel.setText(book.getAuthor());
                genreLabel.setText(book.getGenre());
                publicationYearLabel.setText(book.getPublicationYear());
                CheckAvailability.setText(book.getAvailable());

                if (book.getImage() != null) {
                    Image image = new Image(new java.io.ByteArrayInputStream(book.getImage()));
                    BookImageView.setImage(image);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
