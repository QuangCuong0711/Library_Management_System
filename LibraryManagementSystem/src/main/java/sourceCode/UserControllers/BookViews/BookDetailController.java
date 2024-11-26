package sourceCode.UserControllers.BookViews;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import sourceCode.Models.Book;
import sourceCode.Services.DatabaseConnection;

public class BookDetailController {

    @FXML
    private ImageView image;
    @FXML
    private Label title;
    @FXML
    private Label ISBN;
    @FXML
    private Label author;
    @FXML
    private Label publisher;
    @FXML
    private Label date;
    @FXML
    private Label genre;
    @FXML
    private Label language;
    @FXML
    private Label pageNumber;
    @FXML
    private TextArea description;
    private Book book;

    public void setBook(Book book) {
        this.book = book;
        title.setText(book.getTitle());
        ISBN.setText(book.getISBN());
        author.setText(book.getAuthor());
        genre.setText(book.getGenre());
        publisher.setText(book.getPublisher());
        date.setText(book.getPublicationDate());
        language.setText(book.getLanguage());
        pageNumber.setText(String.valueOf(book.getPageNumber()));
        if (book.getImageUrl() != null && !book.getImageUrl().isEmpty()) {
            try {
                Image img = new Image(book.getImageUrl(), true);
                if (img.isError()) {
                    System.out.println("Image URL is invalid or image cannot be loaded: "
                            + book.getImageUrl());
                    image.setImage(null);
                } else {
                    image.setImage(img);
                }
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid image URL: " + book.getImageUrl());
                image.setImage(null);
            }
        } else {
            image.setImage(null);
        }
        description.setText(book.getDescription());
    }

    public void borrowBook() {
        String checkQuery = "SELECT quantity FROM library.book WHERE ISBN = ?";
        String insertQuery = "INSERT INTO library.ticket (ISBN, userID, borrowedDate, returnedDate, quantity) VALUES (?, ?, CURDATE(), null, 1)";
        String updateTicketQuery = "UPDATE library.ticket SET quantity = quantity + 1 WHERE ISBN = ? AND userID = ?";
        String updateBookQuery = "UPDATE library.book SET quantity = quantity - 1 WHERE ISBN = ?";
        try (Connection conn = DatabaseConnection.getInstance().getConnection()) {
            assert conn != null;
            try (PreparedStatement checkStmt = conn.prepareStatement(checkQuery)) {
                checkStmt.setString(1, book.getISBN());
                if (checkStmt.executeQuery().next()) {
                    try (PreparedStatement updateTicketStmt = conn.prepareStatement(
                            updateTicketQuery)) {
                        updateTicketStmt.setString(1, book.getISBN());
                        updateTicketStmt.setString(2, "1");
                        updateTicketStmt.executeUpdate();
                        System.out.println("Book borrowing updated successfully");
                    }
                } else {
                    try (PreparedStatement insertStmt = conn.prepareStatement(insertQuery)) {
                        insertStmt.setString(1, book.getISBN());
                        insertStmt.setString(2, "1");
                        insertStmt.executeUpdate();
                        System.out.println("Book borrowing added successfully");
                    }
                }
                try (PreparedStatement updateBookStmt = conn.prepareStatement(updateBookQuery)) {
                    updateBookStmt.setString(1, book.getISBN());
                    updateBookStmt.executeUpdate();
                    System.out.println("Book quantity updated successfully");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Book borrowing failed");
        }
    }

    public void confirmButtonOnAction(ActionEvent event) {
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.close();
    }

}
