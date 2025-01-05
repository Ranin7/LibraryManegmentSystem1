package com.example.librarymangmentsystem;
//commenttest
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
import javafx.scene.control.Button;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import javafx.scene.layout.VBox;
import javafx.scene.image.ImageView;
import javafx.scene.control.TextField;
import java.awt.print.Book;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import java.io.ByteArrayInputStream;
import javafx.scene.text.Text;
import org.hibernate.query.Query;


public class allBooksController {
    @FXML
    private Button allBook;

    @FXML
    public void viewDetails(ActionEvent event) throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ViewDetails.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) allBook.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void goDashboard(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("dashboard.fxml"));
        Parent root = loader.load();

        Stage stage = (Stage) allBook.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    @FXML
    private TextField searchField;

    @FXML
    private VBox booksContainer;

    @FXML
    private VBox filterVBox;
    @FXML
    private ComboBox<String> genreComboBox;

    @FXML
    private ComboBox<String> authorComboBox;

    @FXML
    private ComboBox<String> publisherYearComboBox;

    @FXML
    private ComboBox<String> availabilityStatusComboBox;

    @FXML
    private Button filterButton;

    @FXML
    private TableView<String> bookTableView;

    @FXML
    private TableColumn<Book, String> authorColumn;

    @FXML
    private TableColumn<Book, String> genreColumn;

    @FXML
    private TableColumn<Book, String> publisherYearColumn;

    @FXML
    private TableColumn<Book, String> availabilityStatusColumn;

    private String SelectedGenre = "All";
    private String SelectedAuthor = "All";
    private String SelectedPublisherYear = "All";
    private String SelectedAvailabilityStatus = "All";


    private final ObservableList<Books> bookList = FXCollections.observableArrayList();
    private SessionFactory sessionFactory;

    @FXML
    private ListView<String> searchSuggestions;// New ListView for predictive suggestions

    @FXML
    private ListView<String> filteredSuggestions;

    private final ObservableList<Books> booksList;
    private ObservableList<Books> filteredBooks;


    private BookDOAImp bookDAO;

    public allBooksController() {
        bookDAO = new BookDOAImp();
        booksList = FXCollections.observableArrayList(bookDAO.getAll());
    }


    @FXML
    public void initialize() {
        String userRole = UserSession.getInstance().getUserRole();

        allBook.setOnAction(event -> {
            try {
                if ("Librarian".equals(userRole)) {
                    // Load Dashboard for Librarian
                    loadScene("Dashboard.fxml");
                } else if ("User".equals(userRole)) {
                    // Load Welcome Screen for User
                    loadScene("WelcomeScreen.fxml");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        setupTableView();
        loadFilterOptions();
        setupHibernate();
        loadAllBooks();
        displayBooks(booksList);
        displayFilterBooks(bookList);

        searchField.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            searchBook(newValue);
            updateSearchSuggestions(newValue);
        });


        searchSuggestions.setOnMouseClicked(event -> {
            String selectedSuggestion = searchSuggestions.getSelectionModel().getSelectedItem();
            if (selectedSuggestion != null) {
                searchField.setText(selectedSuggestion);
                searchBook(selectedSuggestion);
                searchSuggestions.setVisible(false); // Hide suggestions after selection
            }
        });






        genreComboBox.setOnAction(e -> applyFilters());
        authorComboBox.setOnAction(e -> applyFilters());
        publisherYearComboBox.setOnAction(e -> applyFilters());
        availabilityStatusComboBox.setOnAction(e -> applyFilters());

        filterButton.setOnAction(e -> toggleFilterOptions());


        if (filterVBox != null) {
            filterVBox.setVisible(false);
            filterVBox.setManaged(false);
        }
    }
    private void loadScene(String fxmlFile) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
        AnchorPane root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = (Stage) allBook.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void toggleFilterOptions() {
        System.out.println("Filter button clicked!");
        if (filterVBox != null) {
            boolean isVisible = filterVBox.isVisible();
            filterVBox.setVisible(!isVisible);
            filterVBox.setManaged(!isVisible);

            filterButton.setText(isVisible ? "Filter ▼" : "Filter ▲");

            if (!isVisible){
                applyFilters();
            }
        }
    }

    private void setupTableView() {
        if (authorColumn != null) {
            authorColumn.setCellValueFactory(new PropertyValueFactory<>("author"));
        }
        if (genreColumn != null) {
            genreColumn.setCellValueFactory(new PropertyValueFactory<>("genre"));
        }
        if (publisherYearColumn != null) {
            publisherYearColumn.setCellValueFactory(new PropertyValueFactory<>("publisherYear"));
        }
        if (availabilityStatusColumn != null) {
            availabilityStatusColumn.setCellValueFactory(new PropertyValueFactory<>("availabilityStatus"));
        }
        if (bookTableView != null) {
            bookTableView.setItems(FXCollections.observableArrayList(""));
        }
    }

    private void loadFilterOptions() {
        if (genreComboBox != null) {
            genreComboBox.setItems(FXCollections.observableArrayList("All", "Fiction", "Non-Fiction", "Sci-Fi", "Biography", "Animals", "history", "Political", "Children's Stories"));
        }
        if (authorComboBox != null) {
            authorComboBox.setItems(FXCollections.observableArrayList("All", "Tom", "Alis", "Mohammed", "hassan", "Amjad", "Dostoyfiski", "ahlam","Haneen"));
        }
        if (publisherYearComboBox != null) {
            publisherYearComboBox.setItems(FXCollections.observableArrayList("All", "2009", "2010"
                    , "2011", "2012", "2013", "2014", "2015",
                    "2016", "2017", "2018", "2019", "2020",
                    "2021", "2022", "2023"));
        }
        if (availabilityStatusComboBox != null) {
            availabilityStatusComboBox.setItems(FXCollections.observableArrayList("All", "Yes", "No", "Reserved"));
        }
    }

    private void setupHibernate() {
        try {
            StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure("hibernate.cfg.xml").build();
            sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
        } catch (Exception e) {
            e.printStackTrace();
            sessionFactory = null;
        }
    }

    private void loadAllBooks() {
        if (sessionFactory != null) {
            bookList.clear();
            ObservableList<Books> filtered = filterBooks(SelectedGenre, SelectedAuthor, SelectedPublisherYear, SelectedAvailabilityStatus);
            if (filtered.isEmpty()) {
                bookList.addAll(booksList);  // Revert to all books if no match
            } else {
                bookList.addAll(filtered);
            }
            displayFilterBooks(bookList);

            //bookList.addAll(fetchAllBooks());


        }
    }

    private void displayFilterBooks(ObservableList<Books> books) {
        booksContainer.getChildren().clear();

        for (Books book : books) {
            AnchorPane bookPane = createBookPanel(book);
            booksContainer.getChildren().add(bookPane);

        }
    }


    @FXML
    private void applyFilters() {
        String selectedGenre = genreComboBox != null && genreComboBox.getValue() != null ? genreComboBox.getValue() : "All";
        String selectedAuthor = authorComboBox != null && authorComboBox.getValue() != null ? authorComboBox.getValue() : "All";
        String selectedPublisherYear = publisherYearComboBox != null && publisherYearComboBox.getValue() != null ? publisherYearComboBox.getValue() : "All";
        String selectedAvailabilityStatus = availabilityStatusComboBox != null && availabilityStatusComboBox.getValue() != null ? availabilityStatusComboBox.getValue() : "All";

        ObservableList<Books> filtered = filterBooks(selectedGenre, selectedAuthor, selectedPublisherYear, selectedAvailabilityStatus);
        bookList.clear();
        bookList.addAll(filtered);
        displayBooks(bookList);
    }



    private ObservableList<Books> filterBooks(String genre, String author, String year, String availability) {
        return booksList.stream()
                .filter(book -> (genre.equals("All") || book.getGenre().equalsIgnoreCase(genre)))
                .filter(book -> (author.equals("All") || book.getAuthor().equalsIgnoreCase(author)))
                .filter(book -> (year.equals("All") || book.getPublicationYear().equals(year)))
                .filter(book -> (availability.equals("All") || book.getAvailable().equalsIgnoreCase(availability)))
                .collect(Collectors.toCollection(FXCollections::observableArrayList));
    }



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
    private void viewDetails(Books book) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ViewDetails.fxml"));
        Parent root = loader.load();

        ViewDetailsController detailsController = loader.getController();
        detailsController.initialize(book.getId());

        Stage stage = (Stage) allBook.getScene().getWindow();
        stage.setScene(new Scene(root));
    }




    public void close(){
        if (sessionFactory != null) {
            sessionFactory.close();
        }
    }
}

