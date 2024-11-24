package sourceCode.UserControllers;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sourceCode.Services.Service;

public class AddFeedback {

    @FXML
    public TextField commentText;
    @FXML
    public Slider ratingSlider;
    private String isbn;

    public String getISBN() {
        return this.isbn;
    }

    public void setISBN(String ISBN) {
        this.isbn = ISBN.substring(6);
    }

    public void confirmButtonOnAction(ActionEvent event) {
        String query = "INSERT INTO library.Feedback (ISBN, userID, comment, rating, date) VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = Service.getConnection()) {
            assert connection != null;
            try (PreparedStatement stmt = connection.prepareStatement(query)) {
                stmt.setString(1, getISBN());
                stmt.setString(2, "U001");
                stmt.setString(3, commentText.getText());
                stmt.setInt(4, ratingSlider.valueProperty().intValue());
                stmt.setDate(5, new Date(System.currentTimeMillis()));
                stmt.executeUpdate();
                System.out.println("Feedback added successfully");
            }
        } catch (SQLException e) {
            System.out.println("Feedback adding failed");
            e.printStackTrace();
        }
        cancelButtonOnAction(event);
    }

    public void cancelButtonOnAction(ActionEvent event) {
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.close();
    }
}
