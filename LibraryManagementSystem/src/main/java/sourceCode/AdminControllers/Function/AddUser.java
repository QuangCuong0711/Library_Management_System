package sourceCode.AdminControllers.Function;

import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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

public class AddUser implements Initializable {

    @FXML
    private TextField address;
    @FXML
    private TextField phoneNumber;
    @FXML
    private ChoiceBox<String> gender;
    @FXML
    private TextField identityNumber;
    @FXML
    private TextField name;
    @FXML
    private TextField userID;
    @FXML
    private TextField mail;
    @FXML
    private DatePicker birth;
    @FXML
    private PasswordField password;
    private UserController userController;

    public void setUserController(UserController userController) {
        this.userController = userController;
    }

    @Override
    public void initialize(URL url, ResourceBundle resource) {
        gender.getItems().addAll("Nam", "Nữ");
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
        String query = "INSERT INTO library.user (name, userId, identityNumber, birth, gender, phoneNumber, email, address, password) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = DatabaseConnection.getInstance().getConnection()) {
            assert connection != null;
            try (PreparedStatement stmt = connection.prepareStatement(query)) {
                stmt.setString(1, name.getText());
                stmt.setString(2, userID.getText());
                stmt.setString(3, identityNumber.getText());
                stmt.setDate(4, Date.valueOf(birth.getValue()));
                stmt.setString(5, gender.getValue());
                stmt.setString(6, phoneNumber.getText());
                stmt.setString(7, mail.getText());
                stmt.setString(8, address.getText());
                stmt.setString(9, password.getText());
                stmt.executeUpdate();
                System.out.println("User added successfully");
            }
        } catch (SQLException e) {
            System.out.println("User adding failed");
            e.printStackTrace();
        }
        userController.refreshList();
        cancelButtonOnAction(event);
    }

    public void cancelButtonOnAction(ActionEvent event) {
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.close();
    }
}