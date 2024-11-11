package sourceCode.Models;

import java.sql.*;

public class DatabaseService {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/library_db";
    private static final String USER = "root";
    private static final String PASSWORD = "10072005";

    /**
     * Connect to database.
     */
    public static Connection getConnection() throws SQLException {
        try {
            return DriverManager.getConnection(DB_URL, USER, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Add a new book to the database.
     */
    public void addBook(Document newBook) {
        String query = "INSERT INTO books (ISBN, title, author, genre, publisher, publicationDate, language, pageNumber, imageUrl, description) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = getConnection()) {
            assert connection != null;
            try (PreparedStatement stmt = connection.prepareStatement(query)) {
                stmt.setString(1, newBook.getISBN());
                stmt.setString(2, newBook.getTitle());
                stmt.setString(3, newBook.getAuthor());
                stmt.setString(4, newBook.getGenre());
                stmt.setString(5, newBook.getPublisher());
                stmt.setDate(6, Date.valueOf(newBook.getPublicationDate()));
                stmt.setString(7, newBook.getLanguage());
                stmt.setInt(8, newBook.getPageNumber());
                stmt.setString(9, newBook.getImageUrl());
                stmt.setString(10, newBook.getDescription());
                stmt.executeUpdate();
                System.out.println("Done");
            }
        } catch (SQLException e) {
            System.out.println("Failed");
            e.printStackTrace();
        }
    }
//    public void addUser(User newUser) {
//        String query = "INSERT INTO users (username, password, email, role) VALUES (?, ?, ?, ?)";
//        try (Connection connection = getConnection()) {
//            assert connection != null;
//            try (PreparedStatement stmt = connection.prepareStatement(query)) {
//                stmt.setString(1, newUser.getUsername());
//                stmt.setString(2, newUser.getPassword());
//                stmt.setString(3, newUser.getEmail());
//                stmt.setString(4, newUser.getRole());
//                stmt.executeUpdate();
//                System.out.println("Done");
//            }
//        } catch (SQLException e) {
//            System.out.println("Failed");
//            e.printStackTrace();
//        }
//    }
}
