package sourceCode.AdminControllers;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import sourceCode.Models.Book;
import sourceCode.Services.Service;
import sourceCode.Services.SwitchScene;

public class BookController extends SwitchScene implements Initializable {

    private static final Service service = new Service();
    private static final String selectAllQuery = "SELECT * FROM library.book";
    private static final ObservableList<Book> bookList = FXCollections.observableArrayList();
    private static final String[] searchBy = {"Use GGAPI", "ISBN", "Title", "Author", "Publisher"};
    @FXML
    private TableView<Book> bookTableView;
    @FXML
    private TableColumn<Book, String> isbnColumn;
    @FXML
    private TableColumn<Book, String> authorColumn;
    @FXML
    private TableColumn<Book, String> titleColumn;
    @FXML
    private TableColumn<Book, String> publisherColumn;
    @FXML
    private ChoiceBox<String> choiceBox;
    @FXML
    private TextField searchBar;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        choiceBox.getItems().addAll(searchBy);
        choiceBox.setValue("Search by");
        bookTableView.setItems(bookList);
        isbnColumn.setCellValueFactory(new PropertyValueFactory<>("ISBN"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        authorColumn.setCellValueFactory(new PropertyValueFactory<>("author"));
        publisherColumn.setCellValueFactory(new PropertyValueFactory<>("publisher"));
        selectBook(selectAllQuery);
    }

    public void selectBook(String query) {
        bookList.clear();
        try (Connection conn = Service.getConnection()) {
            assert conn != null;
            try (Statement stmt = conn.createStatement();
                    ResultSet rs = stmt.executeQuery(query)) {
                while (rs.next()) {
                    Book book = new Book(
                            rs.getString("ISBN"),
                            rs.getString("title"),
                            rs.getString("author"),
                            rs.getString("genre"),
                            rs.getString("publisher"),
                            rs.getString("publicationDate"),
                            rs.getString("language"),
                            rs.getInt("pageNumber"),
                            rs.getString("imageUrl"),
                            rs.getString("description"),
                            rs.getInt("quantity")
                    );
                    bookList.add(book);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void searchAPIBook(String keyword) {
        bookList.clear();
        try {
            JsonArray books = service.getBook(keyword);
            for (int i = 0; i < books.size(); i++) {
                JsonObject book = books.get(i).getAsJsonObject();
                bookList.add(createBookFromJson(book));
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private Book createBookFromJson(JsonObject book) {
        JsonObject volumeInfo = book.getAsJsonObject("volumeInfo");
        Book newBook = new Book();
        newBook.setISBN(book.get("id") != null ? book.get("id").getAsString() : null);
        newBook.setTitle(
                volumeInfo.get("title") != null ? volumeInfo.get("title").getAsString() : null);
        newBook.setAuthor(
                volumeInfo.get("authors") != null ? volumeInfo.get("authors").getAsJsonArray()
                        .get(0).getAsString() : null);
        newBook.setGenre(
                volumeInfo.get("categories") != null ? volumeInfo.get("categories").getAsJsonArray()
                        .get(0).getAsString() : null);
        newBook.setPublisher(
                volumeInfo.get("publisher") != null ? volumeInfo.get("publisher").getAsString()
                        : null);
        newBook.setPublicationDate(
                volumeInfo.get("publishedDate") != null ? volumeInfo.get("publishedDate")
                        .getAsString() : null);
        newBook.setLanguage(
                volumeInfo.get("language") != null ? volumeInfo.get("language").getAsString()
                        : null);
        newBook.setPageNumber(
                volumeInfo.get("pageCount") != null ? volumeInfo.get("pageCount").getAsInt() : 0);
        newBook.setImageUrl(volumeInfo.getAsJsonObject("imageLinks") != null
                && volumeInfo.getAsJsonObject("imageLinks").get("thumbnail") != null
                ? volumeInfo.getAsJsonObject("imageLinks").get("thumbnail").getAsString() : null);
        newBook.setDescription(
                volumeInfo.get("description") != null ? volumeInfo.get("description").getAsString()
                        : null);
        return newBook;
    }

    public void addAPIBook() {
        Book book = bookTableView.getSelectionModel().getSelectedItem();
        String query = "INSERT INTO library.book (ISBN, title, author, genre, publisher, publicationDate, language, pageNumber, imageUrl, description) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = Service.getConnection()) {
            assert connection != null;
            try (PreparedStatement stmt = connection.prepareStatement(query)) {
                stmt.setString(1, book.getISBN());
                stmt.setString(2, book.getTitle());
                stmt.setString(3, book.getAuthor());
                stmt.setString(4, book.getGenre());
                stmt.setString(5, book.getPublisher());
                stmt.setString(6, book.getPublicationDate());
                stmt.setString(7, book.getLanguage());
                stmt.setInt(8, book.getPageNumber());
                stmt.setString(9, book.getImageUrl());
                stmt.setString(10, book.getDescription());
                stmt.executeUpdate();
                System.out.println("Book added successfully");
            }
        } catch (SQLException e) {
            System.out.println("Book adding failed");
            e.printStackTrace();
        }
    }


    /**
     * This method is used to search for a Document.
     */
    public void searchBook() {
        if (choiceBox.getValue().equals("Use GGAPI")) {
            searchAPIBook(searchBar.getText());
        } else {
            String query = "SELECT * FROM library.book WHERE " + choiceBox.getValue() + " LIKE '%"
                    + searchBar.getText() + "%'";
            selectBook(query);
        }
    }

    /**
     * This method is used to show the information of a Document.
     */
    public void showBook() {
        Book book = bookTableView.getSelectionModel().getSelectedItem();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/sourceCode/ShowBook.fxml"));
            Parent root = loader.load();
            ShowBook showBook = loader.getController();
            showBook.setBook(book);
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Book Information");
            stage.initStyle(StageStyle.UNDECORATED);
            stage.centerOnScreen();
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method is used to remove a Document from the database.
     */
    public void removeBook() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Remove Document");
        alert.setHeaderText("Can't restore this user after removing");
        alert.setContentText("Do you want to remove this Document ?");
        Optional<ButtonType> a = alert.showAndWait();
        if (a.isEmpty() || a.get() != ButtonType.OK) {
            return;
        }
        Book book = bookTableView.getSelectionModel().getSelectedItem();
        if (book == null) {
            System.out.println("No book selected");
            return;
        }
        String query = "DELETE FROM library.book WHERE ISBN = ?";
        try (Connection connection = Service.getConnection()) {
            assert connection != null;
            try (PreparedStatement stmt = connection.prepareStatement(query)) {
                stmt.setString(1, book.getISBN());
                stmt.executeUpdate();
                bookList.remove(book);
                System.out.println("Book removed successfully");
            }
        } catch (SQLException e) {
            System.out.println("Book removing failed");
            e.printStackTrace();
        }
    }

    //    public void editDocument(String title, String author, String genre, String publisher,
//            String publicationDate, String language,
//            int pageNumber, String imageUrl, String description, int quantity) {
//        String query =
//                "UPDATE library.book SET title = ?, author = ?, genre = ?, publisher = ?, publicationDate = ?, "
//                        +
//                        "language = ?, pageNumber = ?, imageUrl = ?, description = ?, quantity = ? WHERE ISBN = ?";
//        try (Connection connection = Service.getConnection()) {
//            assert connection != null;
//            try (PreparedStatement stmt = connection.prepareStatement(query)) {
//                stmt.setString(1, title);
//                stmt.setString(2, author);
//                stmt.setString(3, genre);
//                stmt.setString(4, publisher);
//                stmt.setString(5, publicationDate);
//                stmt.setString(6, language);
//                stmt.setInt(7, pageNumber);
//                stmt.setString(8, imageUrl);
//                stmt.setString(9, description);
//                stmt.setInt(10, quantity);
//                stmt.executeUpdate();
//                System.out.println("Document edited successfully");
//            }
//        } catch (SQLException e) {
//            System.out.println("Can't edit this document");
//            e.printStackTrace();
//        }
//    }
//    public void addDocument(String title, String author, String genre, String publisher,
//            String publicationDate, String language,
//            int pageNumber, String imageUrl, String description, int quantity) {
//        String query = "INSERT INTO library.book (title, author, genre, publisher, publicationDate, language, pageNumber, imageUrl, description, quantity) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
//        try (Connection connection = Service.getConnection()) {
//            assert connection != null;
//            try (PreparedStatement stmt = connection.prepareStatement(query)) {
//                stmt.setString(1, title);
//                stmt.setString(2, author);
//                stmt.setString(3, genre);
//                stmt.setString(4, publisher);
//                stmt.setString(5, publicationDate);
//                stmt.setString(6, language);
//                stmt.setInt(7, pageNumber);
//                stmt.setString(8, imageUrl);
//                stmt.setString(9, description);
//                stmt.setInt(10, quantity);
//                stmt.executeUpdate();
//                System.out.println("Document added successfully");
//            }
//        } catch (SQLException e) {
//            System.out.println("Document adding failed");
//            e.printStackTrace();
//        }
//    }
//    public void addBook() {
//        try {
//            FXMLLoader loader = new FXMLLoader(getClass().getResource("/sourceCode/AddBook.fxml"));
//            Parent root = loader.load();
//            AddBook addBook = loader.getController();
//            addBook.setBookController(this);
//            Stage stage = new Stage();
//            stage.setScene(new Scene(root));
//            stage.setTitle("Add Book");
//            stage.initStyle(StageStyle.UNDECORATED);
//            stage.centerOnScreen();
//            stage.show();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
}
