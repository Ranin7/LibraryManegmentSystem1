package com.example.librarymangmentsystem;

import com.example.librarymangmentsystem.models.Books;
import com.example.librarymangmentsystem.models.services.BookDOAImp;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class allBooksController {

    @FXML
    private Button allBook;

    @FXML
    private TextField searchField;

    @FXML
    private VBox booksContainer;

    @FXML
    private ListView<String> searchSuggestions; // New ListView for predictive suggestions

    private ObservableList<Books> booksList;

    private BookDOAImp bookDAO;

    public allBooksController() {
        bookDAO = new BookDOAImp();
        booksList = FXCollections.observableArrayList(bookDAO.getAll());
    }

    @FXML
    public void initialize() {
        // Initial population of books
        displayBooks(booksList);

        // Add listener for real-time search with predictive suggestions
        searchField.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            searchBook(newValue);
            updateSearchSuggestions(newValue);
        });

        // Handle suggestion click
        searchSuggestions.setOnMouseClicked(event -> {
            String selectedSuggestion = searchSuggestions.getSelectionModel().getSelectedItem();
            if (selectedSuggestion != null) {
                searchField.setText(selectedSuggestion);
                searchBook(selectedSuggestion);
                searchSuggestions.setVisible(false); // Hide suggestions after selection
            }
        });
    }

    /**
     * Display books in VBox dynamically.
     */
    private void displayBooks(List<Books> books) {
        booksContainer.getChildren().clear();

        for (Books book : books) {
            AnchorPane bookPane = createBookPanel(book);
            booksContainer.getChildren().add(bookPane);
        }
    }

    private AnchorPane createBookPanel(Books book) {
        AnchorPane bookPanel = new AnchorPane();
        bookPanel.setPrefSize(192, 110);
        bookPanel.setStyle("-fx-background-color: #FDE8D3; -fx-border-radius: 5; -fx-padding: 5; -fx-spacing: 5;");

        // Book Image with Top Margin
        ImageView imageView = new ImageView();
        imageView.setFitHeight(110);
        imageView.setFitWidth(75);
        imageView.setLayoutX(5);
        imageView.setLayoutY(25); // Added top margin here
        imageView.setPreserveRatio(true);

        try {
            if (book.getImage() != null && book.getImage().length > 0) {
                ByteArrayInputStream bis = new ByteArrayInputStream(book.getImage());
                imageView.setImage(new Image(bis));
            } else if (book.getImagePath() != null && !book.getImagePath().isEmpty()) {
                imageView.setImage(new Image(book.getImagePath()));
            }
        } catch (Exception e) {
            System.out.println("Error loading image: " + e.getMessage());
        }

        // Book Title
        Text title = new Text(book.getBookName() != null ? book.getBookName() : "Unknown Title");
        title.setLayoutX(97);
        title.setLayoutY(33);
        title.setWrappingWidth(74);
        title.setStyle("-fx-font-size: 10; -fx-font-family: 'System Bold Italic';");

        // View Details Button
        Button viewDetailsButton = new Button("View Details");
        viewDetailsButton.setLayoutX(96);
        viewDetailsButton.setLayoutY(67);
        viewDetailsButton.setPrefSize(80, 20);
        viewDetailsButton.setStyle("-fx-background-color: #CFD6C4; -fx-text-fill: WHITE;");
        viewDetailsButton.setOnAction(event -> {
            try {
                viewDetails(book);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        // Add components to the panel
        bookPanel.getChildren().addAll(imageView, title, viewDetailsButton);

        return bookPanel;
    }

    /**
     * Real-time search filtering
     */
    private void searchBook(String searchText) {
        searchText = searchText.trim().toLowerCase();

        if (searchText.isEmpty()) {
            displayBooks(booksList);
            return;
        }

        String finalSearchText = searchText;
        List<Books> filteredBooks = booksList.stream()
                .filter(book ->
                        (book.getBookName() != null && book.getBookName().toLowerCase().contains(finalSearchText)) ||
                                (book.getAuthor() != null && book.getAuthor().toLowerCase().contains(finalSearchText)) ||
                                (book.getGenre() != null && book.getGenre().toLowerCase().contains(finalSearchText))
                )
                .collect(Collectors.toList());

        displayBooks(filteredBooks);
    }

    /**
     * Update search suggestions dynamically.
     */
    private void updateSearchSuggestions(String searchText) {
        if (searchText.isEmpty()) {
            searchSuggestions.setVisible(false);
            return;
        }

        List<String> suggestions = booksList.stream()
                .map(book -> book.getBookName() != null ? book.getBookName() : "")
                .filter(title -> title.toLowerCase().contains(searchText.toLowerCase()))
                .distinct()
                .collect(Collectors.toList());

        searchSuggestions.setItems(FXCollections.observableArrayList(suggestions));
        searchSuggestions.setVisible(!suggestions.isEmpty());
    }

    /**
     * Navigate to book details
     */
    private void viewDetails(Books book) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ViewDetails.fxml"));
        Parent root = loader.load();

        Stage stage = (Stage) allBook.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    /**
     * Navigate to dashboard
     */
    @FXML
    public void goDashboard(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("dashboard.fxml"));
        Parent root = loader.load();

        Stage stage = (Stage) allBook.getScene().getWindow();
        stage.setScene(new Scene(root));
    }
}
