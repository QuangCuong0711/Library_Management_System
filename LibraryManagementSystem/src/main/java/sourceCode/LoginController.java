package sourceCode;

import java.sql.*;
import java.util.Objects;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import java.io.IOException;

import javafx.scene.image.Image;
import javafx.stage.Stage;
import sourceCode.AdminControllers.UserController;
import sourceCode.Services.DatabaseConnection;
import sourceCode.Services.SwitchScene;

public class LoginController {

    public static String currentUserId = null;
    public static Image imagedefault = new Image(LoginController.class.getResource("/sourceCode/Image/templateCover.png").toExternalForm()
    );

    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private CheckBox checkBox;

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
            fxmlFile = "UserFXML/Library.fxml";
        }
        new Thread(() -> {
            try (Connection conn = DatabaseConnection.getInstance().getConnection()) {
                assert conn != null;
                PreparedStatement preparedStatement = conn.prepareStatement(query);
                preparedStatement.setString(1, username);
                preparedStatement.setString(2, password);
                ResultSet resultSet = preparedStatement.executeQuery();

                if (resultSet.next() && resultSet.getInt(1) == 1) {
                    currentUserId = username;
                    Platform.runLater(() -> {
                        try {
                            Parent root = FXMLLoader.load(
                                    Objects.requireNonNull(this.getClass().getResource(fxmlFile)));
                            Scene scene = new Scene(root);
                            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                            stage.setScene(scene);
                            stage.centerOnScreen();
                            stage.show();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
                    SwitchScene switchScene = new SwitchScene();
                    switchScene.preloadFXML(
                            "AdminFXML/Home",
                            "AdminFXML/User",
                            "AdminFXML/Book",
                            "AdminFXML/Ticket",
                            "AdminFXML/Feedback",
                            "UserFXML/Library",
                            "UserFXML/Bookcase",
                            "UserFXML/Ticket",
                            "UserFXML/Feedback"
                    );
                } else {
                    Platform.runLater(() -> {
                        usernameField.clear();
                        passwordField.clear();
                        System.out.println("Invalid username or password");
                    });
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }).start();
//        try (Connection conn = DatabaseConnection.getInstance().getConnection()) {
//            assert conn != null;
//            PreparedStatement preparedStatement = conn.prepareStatement(query);
//            preparedStatement.setString(1, username);
//            preparedStatement.setString(2, password);
//            ResultSet resultSet = preparedStatement.executeQuery();
//            if (resultSet.next() && resultSet.getInt(1) == 1) {
//                currentUserId = username;
//                Parent root = FXMLLoader.load(
//                        Objects.requireNonNull(this.getClass().getResource(fxmlFile)));
//                Scene scene = new Scene(root);
//                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
//                stage.setScene(scene);
//                stage.centerOnScreen();
//                stage.show();
//                SwitchScene switchScene = new SwitchScene();
//                switchScene.preloadFXML(
//                        "AdminFXML/Home",
//                        "AdminFXML/User",
//                        "AdminFXML/Book",
//                        "AdminFXML/Ticket",
//                        "AdminFXML/Feedback",
//                        "UserFXML/Library",
//                        "UserFXML/Bookcase",
//                        "UserFXML/Ticket",
//                        "UserFXML/Feedback"
//                );
//            } else {
//                usernameField.clear();
//                passwordField.clear();
//                System.out.println("Invalid username or password");
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
    }

    public void signUp() {
        UserController userController = new UserController();
        userController.addUser();
    }
}