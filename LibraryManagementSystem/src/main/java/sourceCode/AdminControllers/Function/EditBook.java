package sourceCode.AdminControllers.Function;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Optional;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sourceCode.AdminControllers.BookController;
import sourceCode.Models.Book;
import sourceCode.Services.DatabaseConnection;

public class EditBook {

    public TextField ISBN;
    public TextField author;
    public TextField genre;
    public TextField publisher;
    public TextField imageUrl;
    public TextField publicationDate;
    public TextArea description;
    public TextField pageNumber;
    public TextField language;
    public TextField quantity;
    public TextField title;
    private BookController bookController;

    public void setBookController(BookController bookController) {
        this.bookController = bookController;
    }

    public void setBook(Book book) {
        ISBN.setText(book.getISBN());
        title.setText(book.getTitle());
        author.setText(book.getAuthor());
        genre.setText(book.getGenre());
        publisher.setText(book.getPublisher());
        imageUrl.setText(book.getImageUrl());
        publicationDate.setText(book.getPublicationDate());
        description.setText(book.getDescription());
        pageNumber.setText(String.valueOf(book.getPageNumber()));
        language.setText(book.getLanguage());
        quantity.setText(String.valueOf(book.getQuantity()));
    }

    public void confirmButtonOnAction(ActionEvent event) {
        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Confirm Edit");
        confirmationAlert.setHeaderText("Are you sure you want to save the changes?");
        confirmationAlert.setContentText("Please confirm your action.");
        Optional<ButtonType> result = confirmationAlert.showAndWait();
        if (result.isEmpty() || result.get() != ButtonType.OK) {
            return;
        }
        String query =
                "UPDATE library.book SET title = ?, author = ?, genre = ?, publisher = ?, publicationDate = ?, "
                        +
                        "language = ?, pageNumber = ?, imageUrl = ?, description = ?, quantity = ? WHERE ISBN = ?";
        try (Connection connection = DatabaseConnection.getInstance().getConnection()) {
            assert connection != null;
            try (PreparedStatement stmt = connection.prepareStatement(query)) {
                stmt.setString(1, title.getText());
                stmt.setString(2, author.getText());
                stmt.setString(3, genre.getText());
                stmt.setString(4, publisher.getText());
                stmt.setString(5, publicationDate.getText());
                stmt.setString(6, language.getText());
                stmt.setInt(7, Integer.parseInt(pageNumber.getText()));
                stmt.setString(8, imageUrl.getText());
                stmt.setString(9, description.getText());
                stmt.setInt(10, Integer.parseInt(quantity.getText()));
                stmt.setString(11, ISBN.getText());
                stmt.executeUpdate();
                System.out.println("Book edited successfully");
            }
        } catch (SQLException e) {
            System.out.println("Can't edit this book");
            e.printStackTrace();
        }
        bookController.initialize(null, null);
        cancelButtonOnAction(event);
    }

    public void cancelButtonOnAction(ActionEvent event) {
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.close();
    }
}