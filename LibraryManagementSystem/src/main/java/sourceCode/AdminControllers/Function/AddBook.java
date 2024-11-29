package sourceCode.AdminControllers.Function;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.event.ActionEvent;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sourceCode.AdminControllers.BookController;
import sourceCode.Services.DatabaseConnection;

public class AddBook implements Initializable {

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

    @Override
    public void initialize(URL url, ResourceBundle resource) {

    }

    public void confirmButtonOnAction(ActionEvent event) {
        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Confirm Add");
        confirmationAlert.setHeaderText("Are you sure you want to save the changes?");
        confirmationAlert.setContentText("Please confirm your action.");
        Optional<ButtonType> result = confirmationAlert.showAndWait();
        if (result.isEmpty() || result.get() != ButtonType.OK) {
            return;
        }
        String checkQuery = "SELECT quantity FROM library.book WHERE ISBN = ?";
        String updateQuery = "UPDATE library.book SET quantity = quantity + ?, title = ?, author = ?, genre = ?, publisher = ?, publicationDate = ?, language = ?, pageNumber = ?, imageUrl = ?, description = ?, quantity = ?, WHERE ISBN = ?";
        String insertQuery = "INSERT INTO library.book (ISBN, title, author, genre, publisher, publicationDate, language, pageNumber, imageUrl, description, quantity) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = DatabaseConnection.getInstance().getConnection()) {
            assert connection != null;
            try (PreparedStatement checkStmt = connection.prepareStatement(checkQuery)) {
                checkStmt.setString(1, ISBN.getText());
                ResultSet rs = checkStmt.executeQuery();
                if (rs.next()) {
                    try (PreparedStatement updateStmt = connection.prepareStatement(updateQuery)) {
                        updateStmt.setInt(1, 1);
                        updateStmt.setString(2, title.getText());
                        updateStmt.setString(3, author.getText());
                        updateStmt.setString(4, genre.getText());
                        updateStmt.setString(5, publisher.getText());
                        updateStmt.setString(6, publicationDate.getText());
                        updateStmt.setString(7, language.getText());
                        updateStmt.setInt(8, Math.max(Integer.parseInt(pageNumber.getText()), 0));
                        updateStmt.setString(9, imageUrl.getText());
                        updateStmt.setString(10, description.getText());
                        updateStmt.setString(11, ISBN.getText());
                        updateStmt.executeUpdate();
                        System.out.println("Book updated successfully");
                    }
                } else {
                    try (PreparedStatement insertStmt = connection.prepareStatement(insertQuery)) {
                        insertStmt.setString(1, ISBN.getText());
                        insertStmt.setString(2, title.getText());
                        insertStmt.setString(3, author.getText());
                        insertStmt.setString(4, genre.getText());
                        insertStmt.setString(5, publisher.getText());
                        insertStmt.setString(6, publicationDate.getText());
                        insertStmt.setString(7, language.getText());
                        insertStmt.setInt(8, Math.max(Integer.parseInt(pageNumber.getText()), 0));
                        insertStmt.setString(9, imageUrl.getText());
                        insertStmt.setString(10, description.getText());
                        insertStmt.setInt(11, Math.max(Integer.parseInt(quantity.getText()), 1));
                        insertStmt.executeUpdate();
                        System.out.println("Book added successfully");
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("Book operation failed");
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