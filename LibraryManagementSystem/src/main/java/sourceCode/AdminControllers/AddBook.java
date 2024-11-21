package sourceCode.AdminControllers;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.event.ActionEvent;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sourceCode.Services.Service;

public class AddBook implements Initializable {

    public TextField ISBN;
    public TextField titile;
    public TextField author;
    public TextField genre;
    public TextField publisher;
    public TextField imageUrl;
    public TextField publicationDate;
    public TextField description;
    public TextField pageNumber;
    public TextField language;
    private BookController bookController;

    public void setBookController(BookController bookController) {
        this.bookController = bookController;
    }

    @Override
    public void initialize(URL url, ResourceBundle resource) {

    }

    public void confirmButtonOnAction(ActionEvent event) {
        String query = "INSERT INTO library.book (ISBN, title, author, genre, publisher, publicationDate, language, pageNumber, imageUrl, description, quantity) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = Service.getConnection()) {
            assert connection != null;
            try (PreparedStatement stmt = connection.prepareStatement(query)) {
                stmt.setString(1, ISBN.getText());
                stmt.setString(2, titile.getText());
                stmt.setString(3, author.getText());
                stmt.setString(4, genre.getText());
                stmt.setString(5, publisher.getText());
                stmt.setString(6, publicationDate.getText());
                stmt.setString(7, language.getText());
                stmt.setInt(8, Integer.parseInt(pageNumber.getText()));
                stmt.setString(9, imageUrl.getText());
                stmt.setString(10, description.getText());
                stmt.executeUpdate();
                System.out.println("Book added successfully");
            }
        } catch (SQLException e) {
            System.out.println("Book adding failed");
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