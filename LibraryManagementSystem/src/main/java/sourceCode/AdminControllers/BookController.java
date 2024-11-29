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
import javafx.concurrent.Task;
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
import sourceCode.AdminControllers.Function.AddBook;
import sourceCode.AdminControllers.Function.EditBook;
import sourceCode.AdminControllers.Function.ShowBook;
import sourceCode.Models.Book;
import sourceCode.Services.DatabaseConnection;
import sourceCode.Services.GoogleBooksAPI;
import sourceCode.Services.SwitchScene;

public class BookController extends SwitchScene implements Initializable {

    private static final String selectAllQuery = "SELECT * FROM library.book";
    private static final ObservableList<Book> bookList = FXCollections.observableArrayList();
    private static final String[] searchBy = {"Tất cả", "GoogleAPI", "ISBN", "Tiêu đề", "Tác giả",
            "Thể loại"};
    @FXML
    private TableView<Book> bookTableView;
    @FXML
    private TableColumn<Book, String> isbnColumn;
    @FXML
    private TableColumn<Book, String> authorColumn;
    @FXML
    private TableColumn<Book, String> titleColumn;
    @FXML
    private TableColumn<Book, String> genreColumn;
    @FXML
    private TableColumn<Book, String> quantityColumn;
    @FXML
    private ChoiceBox<String> choiceBox;
    @FXML
    private TextField searchBar;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        searchBar.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode().toString().equals("ENTER")) {
                if (choiceBox.getValue().equals("GoogleAPI")) {
                    searchAPIBook();
                } else {
                    searchBook();
                }
            }
        });
        choiceBox.getItems().addAll(searchBy);
        choiceBox.setValue("Tìm kiếm theo");
        bookTableView.setItems(bookList);
        isbnColumn.setCellValueFactory(new PropertyValueFactory<>("ISBN"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        authorColumn.setCellValueFactory(new PropertyValueFactory<>("author"));
        genreColumn.setCellValueFactory(new PropertyValueFactory<>("genre"));
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        selectBook(selectAllQuery);
    }

    public void selectBook(String query) {
        bookList.clear();
        Task<ObservableList<Book>> task = new Task<>() {
            @Override
            protected ObservableList<Book> call() throws Exception {
                ObservableList<Book> books = FXCollections.observableArrayList();
                try (Connection conn = DatabaseConnection.getInstance().getConnection()) {
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
                            books.add(book);
                        }
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                return books;
            }
        };

        task.setOnSucceeded(e -> {
            bookList.addAll(task.getValue());
        });

        new Thread(task).start();
    }

    public void searchAPIBook() {
        String keyword = searchBar.getText();
        bookList.clear();
        try {
            JsonArray books = GoogleBooksAPI.getBook(keyword);
            for (int i = 0; i < books.size(); i++) {
                JsonObject book = books.get(i).getAsJsonObject();
                bookList.add(GoogleBooksAPI.createBookFromJson(book));
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }


    public void addAPIBook() {
        sourceCode.Models.Book book = bookTableView.getSelectionModel().getSelectedItem();
        String checkQuery = "SELECT quantity FROM library.book WHERE ISBN = ?";
        String updateQuery = "UPDATE library.book SET quantity = quantity + 1, title = ?, author = ?, genre = ?, publisher = ?, publicationDate = ?, language = ?, pageNumber = ?, imageUrl = ?, description = ? WHERE ISBN = ?";
        String insertQuery = "INSERT INTO library.book (ISBN, title, author, genre, publisher, publicationDate, language, pageNumber, imageUrl, description, quantity) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = DatabaseConnection.getInstance().getConnection()) {
            assert connection != null;
            try (PreparedStatement checkStmt = connection.prepareStatement(checkQuery)) {
                checkStmt.setString(1, book.getISBN());
                ResultSet rs = checkStmt.executeQuery();
                if (rs.next()) {
                    try (PreparedStatement updateStmt = connection.prepareStatement(updateQuery)) {
                        updateStmt.setString(1, book.getTitle());
                        if (book.getAuthor() == null) {
                            updateStmt.setString(2, "");
                        } else {
                            updateStmt.setString(2, book.getAuthor());
                        }
                        updateStmt.setString(3, book.getGenre());
                        updateStmt.setString(4, book.getPublisher());
                        updateStmt.setString(5, book.getPublicationDate());
                        updateStmt.setString(6, book.getLanguage());
                        updateStmt.setInt(7, book.getPageNumber());
                        updateStmt.setString(8, book.getImageUrl());
                        updateStmt.setString(9, book.getDescription());
                        updateStmt.setString(10, book.getISBN());
                        updateStmt.executeUpdate();
                        System.out.println("Book updated successfully");
                    }
                } else {
                    try (PreparedStatement insertStmt = connection.prepareStatement(insertQuery)) {
                        insertStmt.setString(1, book.getISBN());
                        insertStmt.setString(2, book.getTitle());
                        if (book.getAuthor() == null) {
                            insertStmt.setString(3, "");
                        } else {
                            insertStmt.setString(3, book.getAuthor());
                        }
                        insertStmt.setString(4, book.getGenre());
                        insertStmt.setString(5, book.getPublisher());
                        insertStmt.setString(6, book.getPublicationDate());
                        insertStmt.setString(7, book.getLanguage());
                        insertStmt.setInt(8, book.getPageNumber());
                        insertStmt.setString(9, book.getImageUrl());
                        insertStmt.setString(10, book.getDescription());
                        insertStmt.setInt(11, 1); // Thêm số lượng ban đầu là 1
                        insertStmt.executeUpdate();
                        System.out.println("Book added successfully");
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("Book operation failed");
            e.printStackTrace();
        }
    }


    /**
     * This method is used to search for a Document.
     */
    public void searchBook() {
        bookList.clear();
        if (choiceBox.getValue().equals("Tất cả")) {
            selectBook(selectAllQuery);
        } else if (choiceBox.getValue().equals("ISBN")) {
            selectBook(selectAllQuery + " WHERE ISBN LIKE '%" + searchBar.getText() + "%'");
        } else if (choiceBox.getValue().equals("Tiêu đề")) {
            selectBook(selectAllQuery + " WHERE title LIKE '%" + searchBar.getText() + "%'");
        } else if (choiceBox.getValue().equals("Tác giả")) {
            selectBook(selectAllQuery + " WHERE author LIKE '%" + searchBar.getText() + "%'");
        } else if (choiceBox.getValue().equals("Thể loại")) {
            selectBook(selectAllQuery + " WHERE genre LIKE '%" + searchBar.getText() + "%'");
        } else if (choiceBox.getValue().equals("GoogleAPI")) {
            searchAPIBook();
        }
    }

    /**
     * This method is used to show the information of a Document.
     */
    public void showBook() {
        sourceCode.Models.Book book = bookTableView.getSelectionModel().getSelectedItem();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(
                    "/sourceCode/AdminFXML/ShowBook.fxml"));
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
        alert.setTitle("Remove Book");
        alert.setHeaderText("Can't restore this book after removing");
        alert.setContentText("Do you want to remove this Book ?");
        Optional<ButtonType> a = alert.showAndWait();
        if (a.isEmpty() || a.get() != ButtonType.OK) {
            return;
        }
        sourceCode.Models.Book book = bookTableView.getSelectionModel().getSelectedItem();
        if (book == null) {
            System.out.println("No book selected");
            return;
        }
        String query = "DELETE FROM library.book WHERE ISBN = ?";
        try (Connection connection = DatabaseConnection.getInstance().getConnection()) {
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

    public void addBook() {
        if (choiceBox.getValue().equals("GoogleAPI")) {
            addAPIBook();
            return;
        }
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/sourceCode/AdminFXML/AddBook.fxml"));
            Parent root = loader.load();
            AddBook addBook = loader.getController();
            addBook.setBookController(this);
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Add Book");
            stage.initStyle(StageStyle.UNDECORATED);
            stage.centerOnScreen();
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void editBook() {
        sourceCode.Models.Book book = bookTableView.getSelectionModel().getSelectedItem();
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/sourceCode/AdminFXML/EditBook.fxml"));
            Parent root = loader.load();
            EditBook editBook = loader.getController();
            editBook.setBook(book);
            editBook.setBookController(this);
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Edit Book");
            stage.initStyle(StageStyle.UNDECORATED);
            stage.centerOnScreen();
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
