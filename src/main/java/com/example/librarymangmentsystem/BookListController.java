package com.example.librarymangmentsystem;

import com.example.librarymangmentsystem.models.Book;
import com.example.librarymangmentsystem.models.services.BookDAOImp;
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
    private TableView<Book> booksTable;

    @FXML
    private TableColumn<Book, String> col1;
    @FXML
    private TableColumn<Book, Integer> col2;
    @FXML
    private TableColumn<Book, String> col3;
    @FXML
    private TableColumn<Book, String> col4;
    @FXML
    private TableColumn<Book, String> col5;
    @FXML
    private TableColumn<Book, Boolean> col6;
    @FXML
    private TableColumn<Book, byte[]> col7;

    @FXML
    private Button back;


    @FXML
    private TextField searchField;

    private ObservableList<Book> booksList;
    private boolean isEditingAllowed = false;
    private ObservableList<Book> modifiedBooks = FXCollections.observableArrayList();

    // Back to home page
    @FXML
    public void backToHome(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("dashboard.fxml"));
        Parent root = loader.load();

        Stage stage = (Stage) back.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    public void loadBooks() {
        BookDAOImp bookDAO = new BookDAOImp();
        booksList = FXCollections.observableArrayList(bookDAO.getAll());
        booksTable.setItems(booksList);
    }

    @FXML
    public void initialize() {
        // Initialize columns
        col1.setCellValueFactory(new PropertyValueFactory<>("bookName"));
        col2.setCellValueFactory(new PropertyValueFactory<>("id"));
        col3.setCellValueFactory(new PropertyValueFactory<>("author"));
        col4.setCellValueFactory(new PropertyValueFactory<>("genre"));
        col5.setCellValueFactory(new PropertyValueFactory<>("publicationYear"));
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
            Book books = event.getRowValue();
            String newValue = event.getNewValue() ? "Yes" : "No";
            books.setAvailable(newValue);

            if (!modifiedBooks.contains(books)) {
                modifiedBooks.add(books);
            }

            System.out.println("Availability updated to: " + newValue);
        });

        col7.setCellValueFactory(new PropertyValueFactory<>("image"));
        col7.setCellFactory(param -> new TableCell<Book, byte[]>() {
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

        searchField.textProperty().addListener((observable, oldValue, newValue) -> filterBooks(newValue));

        loadBooks();
    }

    private void filterBooks(String searchText) {
        ObservableList<Book> filteredList = FXCollections.observableArrayList();
        for (Book books : booksList) {
            if (books.getBookName().toLowerCase().contains(searchText.toLowerCase()) ||
                    books.getAuthor().toLowerCase().contains(searchText.toLowerCase()) ||
                    books.getGenre().toLowerCase().contains(searchText.toLowerCase()) ||
                    books.getPublicationYear().toLowerCase().contains(searchText.toLowerCase())) {
                filteredList.add(books);
            }
        }
        booksTable.setItems(filteredList);
    }

    public void onUpdateClicked(ActionEvent event) {
        isEditingAllowed = true;
        booksTable.setEditable(true);
        System.out.println("enableEdit");
    }

    private void onEditCommit(TableColumn.CellEditEvent<Book, ?> event, String property) {
        Book books = event.getRowValue();
        Object newValue = event.getNewValue();
        switch (property) {
            case "bookName":
                books.setBookName((String) newValue);
                break;
            case "author":
                books.setAuthor((String) newValue);
                break;
            case "genre":
                books.setGenre((String) newValue);
                break;
            case "publicationYear":
                books.setPublicationYear((String) newValue);
                break;
            default:
                break;
        }
        if (!modifiedBooks.contains(books)) {
            modifiedBooks.add(books);
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
        BookDAOImp bookDAO = new BookDAOImp();
        for (Book books : modifiedBooks) {
            System.out.println("Saving changes for book: " + books);
            bookDAO.update(books);
        }
        loadBooks();
        booksTable.refresh();
        modifiedBooks.clear();
        isEditingAllowed = false;
        booksTable.setEditable(false);
        System.out.println("All changes have been saved.");
    }
}
