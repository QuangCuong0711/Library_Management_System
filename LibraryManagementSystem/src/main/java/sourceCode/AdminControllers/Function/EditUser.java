package sourceCode.AdminControllers.Function;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Optional;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.event.ActionEvent;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sourceCode.AdminControllers.UserController;
import sourceCode.Services.DatabaseConnection;
import sourceCode.UserControllers.ProfileController;

public class EditUser {

    @FXML
    private TextField userID;
    @FXML
    private TextField name;
    @FXML
    private TextField identityNumber;
    @FXML
    private ChoiceBox<String> gender;
    @FXML
    private TextField mail;
    @FXML
    private TextField phoneNumber;
    @FXML
    private TextField address;
    @FXML
    private DatePicker birth;
    @FXML
    private PasswordField password;
    private UserController userController;
    private ProfileController profileController;

    public void setUser(sourceCode.Models.User user) {
        name.setText(user.getName());
        userID.setText(user.getUserId());
        identityNumber.setText(user.getIdentityNumber());
        birth.setValue(user.getBirth());
        mail.setText(user.getEmail());
        address.setText(user.getAddress());
        phoneNumber.setText(user.getPhoneNumber());
        gender.setValue(user.getGender());
        password.setText(user.getPassword());
    }

    public void setUserController(UserController userController) {
        this.userController = userController;
    }

    public void setProfileController(ProfileController profileController) {
        this.profileController = profileController;
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
        String query = "UPDATE library.user SET name = ?, identityNumber = ?, birth = ?, gender = ?, phoneNumber = ?, email = ?, address = ?, password = ? WHERE userId = ?";
        try (Connection connection = DatabaseConnection.getInstance().getConnection()) {
            assert connection != null;
            try (PreparedStatement stmt = connection.prepareStatement(query)) {
                stmt.setString(1, name.getText());
                stmt.setString(2, identityNumber.getText());
                stmt.setDate(3, Date.valueOf(birth.getValue()));
                stmt.setString(4, gender.getValue());
                stmt.setString(5, phoneNumber.getText());
                stmt.setString(6, mail.getText());
                stmt.setString(7, address.getText());
                stmt.setString(8, password.getText());
                stmt.setString(9, userID.getText());
                stmt.executeUpdate();
                Alert alrt = new Alert(Alert.AlertType.INFORMATION);
                alrt.setTitle("User Edited");
                alrt.setHeaderText(null);
                alrt.setContentText("User edited successfully");
                alrt.showAndWait();
                System.out.println("User edited successfully");
            }
        } catch (SQLException e) {
            System.out.println("Can't edit this user");
            e.printStackTrace();
        }
        if (userController != null) {
            userController.refreshList();
        } else {
            profileController.initProfilePane();
        }
        cancelButtonOnAction(event);
    }

    public void cancelButtonOnAction(ActionEvent event) {
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.close();
    }
}