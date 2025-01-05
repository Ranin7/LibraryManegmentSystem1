package com.example.librarymangmentsystem;

import com.example.librarymangmentsystem.models.Books;
import com.example.librarymangmentsystem.models.services.BookDOAImp;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import javafx.scene.control.TextField;

import java.io.ByteArrayInputStream;
import java.io.IOException;

public class BookListController {

    @FXML
    private TableView<Books> booksTable;

    @FXML
    private TableColumn<Books, String> col1;
    @FXML
    private TableColumn<Books, Integer> col2;
    @FXML
    private TableColumn<Books, String> col3;
    @FXML
    private TableColumn<Books, String> col4;
    @FXML
    private TableColumn<Books, String> col5;
    @FXML
    private TableColumn<Books, Boolean> col6;
    @FXML
    private TableColumn<Books, byte[]> col7;

    @FXML
    private Button back;
    @FXML
    private Button update;
    @FXML
    private Button done;

    @FXML
    private TextField searchField;

    private ObservableList<Books> booksList;
    private boolean isEditingAllowed = false;
    private ObservableList<Books> modifiedBooks = FXCollections.observableArrayList();

    // Back to home page
    @FXML
    public void backToHome(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("dashboard.fxml"));
        Parent root = loader.load();

        Stage stage = (Stage) back.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    public void loadBooks() {
        BookDOAImp bookDAO = new BookDOAImp();
        booksList = FXCollections.observableArrayList(bookDAO.getAll()); // Assuming getAll() returns List<Books>
        booksTable.setItems(booksList);  // Set the data to TableView
    }

    @FXML
    public void initialize() {
        // Initialize columns
        col1.setCellValueFactory(new PropertyValueFactory<>("bookName"));
        col2.setCellValueFactory(new PropertyValueFactory<>("id"));
        col3.setCellValueFactory(new PropertyValueFactory<>("author"));
        col4.setCellValueFactory(new PropertyValueFactory<>("genre"));
        col5.setCellValueFactory(new PropertyValueFactory<>("publicationYear"));

        // Initialize availability column to display "Yes" or "No"
        col6.setCellValueFactory(cellData -> new SimpleBooleanProperty(cellData.getValue().getAvailable().equalsIgnoreCase("Yes")).asObject());

        col6.setCellFactory(TextFieldTableCell.forTableColumn(new StringConverter<Boolean>() {
            @Override
            public String toString(Boolean object) {
                return object ? "Yes" : "No";
            }

            @Override
            public Boolean fromString(String string) {
                return string.equalsIgnoreCase("Yes");
            }
        }));

        col1.setCellFactory(TextFieldTableCell.forTableColumn());
        col3.setCellFactory(TextFieldTableCell.forTableColumn());
        col4.setCellFactory(TextFieldTableCell.forTableColumn());
        col5.setCellFactory(TextFieldTableCell.forTableColumn());
        col1.setOnEditCommit(event -> onEditCommit(event, "bookName"));
        col3.setOnEditCommit(event -> onEditCommit(event, "author"));
        col4.setOnEditCommit(event -> onEditCommit(event, "genre"));
        col5.setOnEditCommit(event -> onEditCommit(event, "publicationYear"));
        col6.setOnEditCommit(event -> {
            Books book = event.getRowValue();
            String newValue = event.getNewValue() ? "Yes" : "No";
            book.setAvailable(newValue); // Update availability

            if (!modifiedBooks.contains(book)) {
                modifiedBooks.add(book); // Add book to modified list
            }

            System.out.println("Availability updated to: " + newValue);
        });

        col7.setCellValueFactory(new PropertyValueFactory<>("image"));
        col7.setCellFactory(param -> new TableCell<Books, byte[]>() {
            @Override
            protected void updateItem(byte[] item, boolean empty) {
                super.updateItem(item, empty);
                if (item != null && !empty) {
                    try {
                        Image image = new Image(new ByteArrayInputStream(item));
                        ImageView imageView = new ImageView(image);
                        imageView.setFitHeight(50); // Image size
                        imageView.setFitWidth(50);
                        setGraphic(imageView);
                    } catch (Exception e) {
                        setText("Invalid Image");
                        setGraphic(null);
                    }
                } else {
                    setGraphic(null);
                    setText(null);
                }
            }
        });

        // Add listener to search field to filter the TableView
        searchField.textProperty().addListener((observable, oldValue, newValue) -> filterBooks(newValue));

        loadBooks();
    }

    private void filterBooks(String searchText) {
        ObservableList<Books> filteredList = FXCollections.observableArrayList();
        for (Books book : booksList) {
            if (book.getBookName().toLowerCase().contains(searchText.toLowerCase()) ||
                    book.getAuthor().toLowerCase().contains(searchText.toLowerCase()) ||
                    book.getGenre().toLowerCase().contains(searchText.toLowerCase()) ||
                    book.getPublicationYear().toLowerCase().contains(searchText.toLowerCase())) {
                filteredList.add(book);
            }
        }
        booksTable.setItems(filteredList);
    }

    public void onUpdateClicked(ActionEvent event) {
        isEditingAllowed = true;
        booksTable.setEditable(true);
        System.out.println("enableEdit");
    }

    private void onEditCommit(TableColumn.CellEditEvent<Books, ?> event, String property) {
        Books book = event.getRowValue();
        Object newValue = event.getNewValue();
        switch (property) {
            case "bookName":
                book.setBookName((String) newValue);
                break;
            case "author":
                book.setAuthor((String) newValue);
                break;
            case "genre":
                book.setGenre((String) newValue);
                break;
            case "publicationYear":
                book.setPublicationYear((String) newValue);
                break;
            default:
                break;
        }
        if (!modifiedBooks.contains(book)) {
            modifiedBooks.add(book);
        }

        System.out.println("Updated " + property + ": " + newValue);
    }

    @FXML
    public void onDoneClicked(ActionEvent event) {
        if (!isEditingAllowed) {
            System.out.println("Editing is not allowed. Please click 'Update' first.");
            return;
        }
        if (modifiedBooks.isEmpty()) {
            System.out.println("No changes to save.");
            return;
        }
        BookDOAImp bookDAO = new BookDOAImp();
        for (Books book : modifiedBooks) {
            System.out.println("Saving changes for book: " + book);
            bookDAO.update(book);
        }
        loadBooks();
        booksTable.refresh();
        modifiedBooks.clear();
        isEditingAllowed = false;
        booksTable.setEditable(false);
        System.out.println("All changes have been saved.");
    }
}