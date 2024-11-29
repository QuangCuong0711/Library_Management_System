package sourceCode.AdminControllers.Function;

import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.event.ActionEvent;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sourceCode.AdminControllers.UserController;
import sourceCode.Services.DatabaseConnection;

public class AddUser implements Initializable {

    public TextField address;
    public TextField phoneNumber;
    public ChoiceBox<String> gender;
    public TextField identityNumber;
    public TextField name;
    public TextField userID;
    public TextField mail;
    public DatePicker birth;
    public PasswordField password;
    private UserController userController;

    public void setUserController(UserController userController) {
        this.userController = userController;
    }

    @Override
    public void initialize(URL url, ResourceBundle resource) {
        gender.getItems().addAll("Nam", "Nữ");
    }

    public void confirmButtonOnAction(ActionEvent event) {
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
        userController.initialize(null, null);
        cancelButtonOnAction(event);
    }

    public void cancelButtonOnAction(ActionEvent event) {
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.close();
    }
}