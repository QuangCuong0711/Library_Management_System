package sourceCode.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import sourceCode.Models.User;

public class ShowUserController {

    @FXML
    private Label name;
    @FXML
    private Label userID;
    @FXML
    private Label identityNumber;
    @FXML
    private Label birth;
    @FXML
    private Label gender;
    @FXML
    private Label phoneNumber;
    @FXML
    private Label email;
    @FXML
    private Label address;

    public void setUser(User user) {
        name.setText(user.getName());
        userID.setText(user.getUserId());
        identityNumber.setText(user.getIdentityNumber());
        birth.setText(user.getBirth().toString());
        gender.setText(user.getGender());
        phoneNumber.setText(user.getPhoneNumber());
        email.setText(user.getEmail());
        address.setText(user.getAddress());
    }

    public void confirmButtonOnAction(ActionEvent event) {
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.close();
    }
}
