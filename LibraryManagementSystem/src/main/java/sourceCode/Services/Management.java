package sourceCode.Services;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import java.io.IOException;
import java.sql.*;
import java.util.Optional;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.cell.PropertyValueFactory;
import sourceCode.Models.Document;
import sourceCode.Models.User;

public class Management {

    private static final Service service = new Service();

    public ObservableList<Document> getAllDocument() {
        ObservableList<Document> documentList = FXCollections.observableArrayList();
        String query = "SELECT * FROM library.books";
        try (Connection conn = Service.getConnection()) {
            assert conn != null;
            try (Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(query)) {
                while (rs.next()) {
                    Document document = new Document(
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
                    documentList.add(document);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return documentList;
    }

    public ObservableList<Document> sreachDocument(String value, String text) {
        ObservableList<Document> documentList = FXCollections.observableArrayList();
        String query = "SELECT * FROM library.books WHERE " + value + " LIKE '%" + text + "%'";
        try (Connection conn = Service.getConnection()) {
            assert conn != null;
            try (Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(query)) {
                while (rs.next()) {
                    Document document = new Document(
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
                    documentList.add(document);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return documentList;
    }

    public void removeDocument(ObservableList<Document> documentList, User user) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Remove Document");
        alert.setHeaderText("Can't restore this user after removing");
        alert.setContentText("Do you want to remove this Document ?");
        Optional<ButtonType> a = alert.showAndWait();
        if (a.isEmpty() || a.get() != ButtonType.OK) {
            return;
        }
        String query = "DELETE FROM library.books WHERE ISBN = ?";
        try (Connection connection = Service.getConnection()) {
            assert connection != null;
            try (PreparedStatement stmt = connection.prepareStatement(query)) {
                stmt.setString(1, user.getUserId());
                stmt.executeUpdate();
                documentList.remove(user);
                System.out.println("Document removed successfully");
            }
        } catch (SQLException e) {
            System.out.println("Document removing failed");
            e.printStackTrace();
        }
    }

    public void editDocument(String title, String author, String genre, String publisher, String publicationDate, String language,
                             int pageNumber, String imageUrl, String description, int quantity) {
        String query = "UPDATE library.books SET title = ?, author = ?, genre = ?, publisher = ?, publicationDate = ?, " +
                "language = ?, pageNumber = ?, imageUrl = ?, description = ?, quantity = ? WHERE ISBN = ?";
        try (Connection connection = Service.getConnection()) {
            assert connection != null;
            try (PreparedStatement stmt = connection.prepareStatement(query)) {
                stmt.setString(1, title);
                stmt.setString(2, author);
                stmt.setString(3, genre);
                stmt.setString(4, publisher);
                stmt.setString(5, publicationDate);
                stmt.setString(6, language);
                stmt.setInt(7, pageNumber);
                stmt.setString(8, imageUrl);
                stmt.setString(9, description);
                stmt.setInt(10, quantity);
                stmt.executeUpdate();
                System.out.println("Document edited successfully");
            }
        } catch (SQLException e) {
            System.out.println("Can't edit this document");
            e.printStackTrace();
        }
    }

    public void addDocument(String title, String author, String genre, String publisher, String publicationDate, String language,
                            int pageNumber, String imageUrl, String description, int quantity) {
        String query = "INSERT INTO library.books (title, author, genre, publisher, publicationDate, language, pageNumber, imageUrl, description, quantity) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = Service.getConnection()) {
            assert connection != null;
            try (PreparedStatement stmt = connection.prepareStatement(query)) {
                stmt.setString(1, title);
                stmt.setString(2, author);
                stmt.setString(3, genre);
                stmt.setString(4, publisher);
                stmt.setString(5, publicationDate);
                stmt.setString(6, language);
                stmt.setInt(7, pageNumber);
                stmt.setString(8, imageUrl);
                stmt.setString(9, description);
                stmt.setInt(10, quantity);
                stmt.executeUpdate();
                System.out.println("Document added successfully");
            }
        } catch (SQLException e) {
            System.out.println("Document adding failed");
            e.printStackTrace();
        }
    }
    public void addBookAPI(Document newBook) {
        String query = "INSERT INTO library.books (ISBN, title, author, genre, publisher, publicationDate, language, pageNumber, imageUrl, description) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = Service.getConnection()) {
            assert connection != null;
            try (PreparedStatement stmt = connection.prepareStatement(query)) {
                stmt.setString(1, newBook.getISBN());
                stmt.setString(2, newBook.getTitle());
                stmt.setString(3, newBook.getAuthor());
                stmt.setString(4, newBook.getGenre());
                stmt.setString(5, newBook.getPublisher());
                stmt.setString(6, newBook.getPublicationDate());
                stmt.setString(7, newBook.getLanguage());
                stmt.setInt(8, newBook.getPageNumber());
                stmt.setString(9, newBook.getImageUrl());
                stmt.setString(10, newBook.getDescription());
                stmt.executeUpdate();
                System.out.println("Book added successfully");
            }
        } catch (SQLException e) {
            System.out.println("Book adding failed");
            e.printStackTrace();
        }
    }

//    public void searchBookAPI(String query) {
//        try {
//            JsonArray books = service.getBook(query);
//            for (int i = 0; i < books.size(); i++) {
//                JsonObject book = books.get(i).getAsJsonObject();
//                JsonObject volumeInfo = book.getAsJsonObject("volumeInfo");
//                Document newBook = new Document();
//                newBook.setISBN(book.get("id") != null ? book.get("id").getAsString() : null);
//                newBook.setTitle(
//                        volumeInfo.get("title") != null ? volumeInfo.get("title").getAsString()
//                                : null);
//                newBook.setAuthor(volumeInfo.get("authors") != null ? volumeInfo.get("authors")
//                        .getAsJsonArray().get(0).getAsString() : null);
//                newBook.setGenre(volumeInfo.get("categories") != null ? volumeInfo.get("categories")
//                        .getAsJsonArray().get(0).getAsString() : null);
//                newBook.setPublisher(
//                        volumeInfo.get("publisher") != null ? volumeInfo.get("publisher")
//                                .getAsString() : null);
//                newBook.setPublicationDate(
//                        volumeInfo.get("publishedDate") != null ? volumeInfo.get("publishedDate")
//                                .getAsString() : null);
//                newBook.setLanguage(volumeInfo.get("language") != null ? volumeInfo.get("language")
//                        .getAsString() : null);
//                newBook.setPageNumber(
//                        volumeInfo.get("pageCount") != null ? volumeInfo.get("pageCount").getAsInt()
//                                : 0);
//                newBook.setImageUrl(volumeInfo.getAsJsonObject("imageLinks") != null
//                        && volumeInfo.getAsJsonObject("imageLinks").get("thumbnail") != null
//                        ? volumeInfo.getAsJsonObject("imageLinks").get("thumbnail").getAsString()
//                        : null);
//                newBook.setDescription(
//                        volumeInfo.get("description") != null ? volumeInfo.get("description")
//                                .getAsString() : null);
//                addBook(newBook);
//            }
//        } catch (IOException | InterruptedException e) {
//            e.printStackTrace();
//        }
//    }
}
