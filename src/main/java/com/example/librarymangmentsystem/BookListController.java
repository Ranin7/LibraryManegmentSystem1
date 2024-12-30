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
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.StringConverter;

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

    private ObservableList<Books> booksList;
    private ObservableList<Books> modifiedBooks = FXCollections.observableArrayList();
    private boolean isEditingAllowed = false;

    @FXML
    public void initialize() {
        // إعداد الأعمدة
        col1.setCellValueFactory(new PropertyValueFactory<>("bookName"));
        col2.setCellValueFactory(new PropertyValueFactory<>("id"));
        col3.setCellValueFactory(new PropertyValueFactory<>("author"));
        col4.setCellValueFactory(new PropertyValueFactory<>("genre"));
        col5.setCellValueFactory(new PropertyValueFactory<>("publicationYear"));

        col6.setCellValueFactory(cellData ->
                new SimpleBooleanProperty("Yes".equalsIgnoreCase(cellData.getValue().getAvailable())).asObject()
        );

        col6.setCellFactory(TextFieldTableCell.forTableColumn(new StringConverter<Boolean>() {
            @Override
            public String toString(Boolean object) {
                return object ? "Yes" : "No";
            }

            @Override
            public Boolean fromString(String string) {
                return "Yes".equalsIgnoreCase(string);
            }
        }));

        col7.setCellValueFactory(new PropertyValueFactory<>("image"));
        col7.setCellFactory(param -> new TableCell<Books, byte[]>() {
            @Override
            protected void updateItem(byte[] item, boolean empty) {
                super.updateItem(item, empty);
                if (item != null && !empty) {
                    try {
                        Image image = new Image(new ByteArrayInputStream(item));
                        ImageView imageView = new ImageView(image);
                        imageView.setFitHeight(50); // حجم الصورة
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

        loadBooks();
    }

    public void loadBooks() {
        BookDOAImp bookDAO = new BookDOAImp();
        booksList = FXCollections.observableArrayList(bookDAO.getAll());
        booksTable.setItems(booksList);
    }

    @FXML
    public void onUpdateClicked(ActionEvent event) {
        isEditingAllowed = true;
        booksTable.setEditable(true);
        System.out.println("Editing enabled.");
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
            bookDAO.update(book);
        }
        loadBooks();
        modifiedBooks.clear();
        isEditingAllowed = false;
        booksTable.setEditable(false);
        System.out.println("All changes have been saved.");
    }

    @FXML
    public void backToHome(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("dashboard.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) back.getScene().getWindow();
        stage.setScene(new Scene(root));
    }
}