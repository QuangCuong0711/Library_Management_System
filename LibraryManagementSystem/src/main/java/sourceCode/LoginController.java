package sourceCode;

import java.sql.*;
import java.util.Objects;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import java.io.IOException;
import javafx.stage.Stage;
import sourceCode.AdminControllers.User;
import sourceCode.Services.Service;

public class LoginController {

    public TextField usernameField;
    public PasswordField passwordField;
    public CheckBox checkBox;

    public void logIn(ActionEvent event) {
        String username = usernameField.getText();
        String password = passwordField.getText();
        String query;
        String fxmlFile;

        if (checkBox.isSelected()) {
            query = "SELECT COUNT(*) FROM library.admin WHERE adminId = ? AND password = ?";
            fxmlFile = "AdminFXML/Home.fxml";
        } else {
            query = "SELECT COUNT(*) FROM library.user WHERE userId = ? AND password = ?";
            fxmlFile = "UserFXML/Home.fxml";
        }

        try (Connection conn = Service.getConnection()) {
            assert conn != null;
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            if (resultSet.getInt(1) == 1) {
                Parent root = FXMLLoader.load(
                        Objects.requireNonNull(this.getClass().getResource(fxmlFile)));
                Scene scene = new Scene(root);
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(scene);
                stage.centerOnScreen();
                stage.show();
            } else {
                usernameField.clear();
                passwordField.clear();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void signUp() {
        User user = new User();
        user.addUser();
    }
}