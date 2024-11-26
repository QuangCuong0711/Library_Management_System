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
import sourceCode.Services.DatabaseConnection;

public class UpdateFeedback {

    @FXML
    public TextField commentText;
    @FXML
    public Slider ratingSlider;
    private FeedbackController feedbackController;
    private int feedbackId;

    public void setFeedbackController(FeedbackController feedbackController) {
        this.feedbackController = feedbackController;
    }

    public int getFeedbackId() {
        return this.feedbackId;
    }

    public void setFeedbackID(int feedbackId) {
        this.feedbackId = feedbackId;
    }

    public String getComment() {
        return commentText.getText();
    }

    public void setComment(String comment) {
        commentText.setText(comment);
    }

    public int getRating() {
        return ratingSlider.valueProperty().intValue();
    }

    public void setRating(int rating) {
        ratingSlider.setValue(rating);
    }

    public void confirmButtonOnAction(ActionEvent event) {
        String query = "UPDATE library.Feedback SET comment = ?, rating = ?, date = ? WHERE feedbackId = ?";
        try (Connection connection = DatabaseConnection.getInstance().getConnection()) {
            assert connection != null;
            try (PreparedStatement stmt = connection.prepareStatement(query)) {
                stmt.setString(1, getComment());
                stmt.setInt(2, getRating());
                stmt.setDate(3, new Date(System.currentTimeMillis()));
                stmt.setInt(4, getFeedbackId());
                int rowsUpdated = stmt.executeUpdate();
                if (rowsUpdated > 0) {
                    System.out.println("Feedback updated successfully");
                } else {
                    System.out.println("No feedback found with the given ID");
                }
            }
        } catch (SQLException e) {
            System.out.println("Feedback updating failed");
            e.printStackTrace();
            cancelButtonOnAction(event);
        }
        feedbackController.initialize(null, null);
        cancelButtonOnAction(event);
    }

    public void cancelButtonOnAction(ActionEvent event) {
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.close();
    }
}
