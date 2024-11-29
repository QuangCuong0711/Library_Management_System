package sourceCode.Services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.ObservableList;
import sourceCode.Models.Book;

public class DatabaseOperation {
    public static void loadBookfromDatabase(String query, ObservableList<Book> databaseBookList) {
        databaseBookList.clear();
        try (Connection conn = DatabaseConnection.getInstance().getConnection()) {
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                try (ResultSet rs = stmt.executeQuery()) {
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
                        databaseBookList.add(book);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
