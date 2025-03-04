package sourceCode.AdminControllers.Function;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.event.ActionEvent;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sourceCode.AdminControllers.BookController;
import sourceCode.Services.DatabaseConnection;

public class AddBook {

    @FXML
    private TextField ISBN;
    @FXML
    private TextField author;
    @FXML
    private TextField genre;
    @FXML
    private TextField publisher;
    @FXML
    private TextField imageUrl;
    @FXML
    private TextField publicationDate;
    @FXML
    private TextArea description;
    @FXML
    private TextField pageNumber;
    @FXML
    private TextField language;
    @FXML
    private TextField quantity;
    @FXML
    private TextField title;
    private BookController bookController;

    public void setBookController(BookController bookController) {
        this.bookController = bookController;
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
        String errorMessage = validateInput();
        if (!errorMessage.isEmpty()) {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setTitle("Invalid Input");
            errorAlert.setHeaderText("Invalid Insertion");
            errorAlert.setContentText(errorMessage);
            errorAlert.showAndWait();
            return;
        }

        String checkQuery = "SELECT quantity FROM library.book WHERE ISBN = ?";
        String updateQuery = "UPDATE library.book SET quantity = quantity + ?, title = ?, author = ?, genre = ?, publisher = ?, publicationDate = ?, language = ?, pageNumber = ?, imageUrl = ?, description = ? WHERE ISBN = ?";
        String insertQuery = "INSERT INTO library.book (ISBN, title, author, genre, publisher, publicationDate, language, pageNumber, imageUrl, description, quantity) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = DatabaseConnection.getInstance().getConnection()) {
            assert connection != null;
            try (PreparedStatement checkStmt = connection.prepareStatement(checkQuery)) {
                checkStmt.setString(1, ISBN.getText());
                ResultSet rs = checkStmt.executeQuery();
                if (rs.next()) {
                    try (PreparedStatement updateStmt = connection.prepareStatement(updateQuery)) {
                        updateStmt.setInt(1, Integer.parseInt(quantity.getText()));
                        updateStmt.setString(2, title.getText());
                        updateStmt.setString(3, author.getText());
                        updateStmt.setString(4, genre.getText());
                        updateStmt.setString(5, publisher.getText());
                        updateStmt.setString(6, publicationDate.getText());
                        updateStmt.setString(7, language.getText());
                        updateStmt.setInt(8, Integer.parseInt(pageNumber.getText()));
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
                        insertStmt.setInt(8, Integer.parseInt(pageNumber.getText()));
                        insertStmt.setString(9, imageUrl.getText());
                        insertStmt.setString(10, description.getText());
                        insertStmt.setInt(11, Integer.parseInt(quantity.getText()));
                        insertStmt.executeUpdate();
                        System.out.println("Book added successfully");
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("Book operation failed");
            e.printStackTrace();
        }

        bookController.refreshList();
        cancelButtonOnAction(event);
    }

    private String validateInput() {
        StringBuilder errorMessage = new StringBuilder();

        if (ISBN.getText() == null || ISBN.getText().trim().isEmpty()) {
            errorMessage.append("ISBN must not be empty.\n");
        }
        if (title.getText() == null || title.getText().trim().isEmpty()) {
            errorMessage.append("Title must not be empty.\n");
        }
        try {
            int pages = Integer.parseInt(pageNumber.getText());
            if (pages <= 0) {
                errorMessage.append("Page number must be a positive integer.\n");
            }
        } catch (NumberFormatException e) {
            errorMessage.append("Page number must be a valid integer.\n");
        }
        try {
            int qty = Integer.parseInt(quantity.getText());
            if (qty < 0) {
                errorMessage.append("Quantity must be a positive integer.\n");
            }
        } catch (NumberFormatException e) {
            errorMessage.append("Quantity must be a valid integer.\n");
        }

        return errorMessage.toString();
    }

    public void cancelButtonOnAction(ActionEvent event) {
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.close();
    }
}