package sourceCode.Services;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import sourceCode.Models.Document;

public class Management {

    private static final Service service = new Service();

    public void addBook(Document newBook) {
        String query = "INSERT INTO library.book (ISBN, title, author, genre, publisher, publicationDate, language, pageNumber, imageUrl, description) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
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

    public void searchBook(String query) {
        try {
            JsonArray books = service.getBook(query);
            for (int i = 0; i < books.size(); i++) {
                JsonObject book = books.get(i).getAsJsonObject();
                JsonObject volumeInfo = book.getAsJsonObject("volumeInfo");
                Document newBook = new Document();
                newBook.setISBN(book.get("id") != null ? book.get("id").getAsString() : null);
                newBook.setTitle(
                        volumeInfo.get("title") != null ? volumeInfo.get("title").getAsString()
                                : null);
                newBook.setAuthor(volumeInfo.get("authors") != null ? volumeInfo.get("authors")
                        .getAsJsonArray().get(0).getAsString() : null);
                newBook.setGenre(volumeInfo.get("categories") != null ? volumeInfo.get("categories")
                        .getAsJsonArray().get(0).getAsString() : null);
                newBook.setPublisher(
                        volumeInfo.get("publisher") != null ? volumeInfo.get("publisher")
                                .getAsString() : null);
                newBook.setPublicationDate(
                        volumeInfo.get("publishedDate") != null ? volumeInfo.get("publishedDate")
                                .getAsString() : null);
                newBook.setLanguage(volumeInfo.get("language") != null ? volumeInfo.get("language")
                        .getAsString() : null);
                newBook.setPageNumber(
                        volumeInfo.get("pageCount") != null ? volumeInfo.get("pageCount").getAsInt()
                                : 0);
                newBook.setImageUrl(volumeInfo.getAsJsonObject("imageLinks") != null
                        && volumeInfo.getAsJsonObject("imageLinks").get("thumbnail") != null
                        ? volumeInfo.getAsJsonObject("imageLinks").get("thumbnail").getAsString()
                        : null);
                newBook.setDescription(
                        volumeInfo.get("description") != null ? volumeInfo.get("description")
                                .getAsString() : null);
                addBook(newBook);
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
