package sourceCode.AdminControllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import sourceCode.Models.User;

public class ShowFeedBack {

    @FXML
    private Label feedbackID;
    @FXML
    private Label userID;
    @FXML
    private Label bookID;
    @FXML
    private Label comment;
    @FXML
    private Label rating;
    @FXML
    private Label date;


    public void setFeedback(sourceCode.Models.Feedback feedback) {
        feedbackID.setText(String.valueOf(feedback.getFeedbackID()));
        userID.setText(feedback.getUserID());
        bookID.setText(feedback.getISBN());
        comment.setText(feedback.getComment());
        rating.setText(String.valueOf(feedback.getRating()));
        date.setText(String.valueOf(feedback.getDate()));
    }

    public void confirmButtonOnAction(ActionEvent event) {
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.close();
    }

}
