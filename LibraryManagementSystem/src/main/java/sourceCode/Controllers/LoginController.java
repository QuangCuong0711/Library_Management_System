package sourceCode.Controllers;

import java.sql.*;
import java.util.Objects;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;

import java.io.IOException;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.jetbrains.annotations.NotNull;
import sourceCode.Services.Service;

public class LoginController {

    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Label alertLabel;

    public void login(ActionEvent event) {
        String username = usernameField.getText();
        String password = passwordField.getText();
        try (Connection connection = Service.getConnection()) {
            String query = "SELECT * FROM ADMINACCOUNT WHERE nameAccount = ? AND passwordAccount = ?";
            assert connection != null;
            try (PreparedStatement stmt = connection.prepareStatement(query)) {
                stmt.setString(1, username);
                stmt.setString(2, password);
                ResultSet resultSet = stmt.executeQuery();
                if(resultSet.next()) {
                    alertLabel.setText("Login successful");
                    alertLabel.setStyle("-fx-text-fill: #00ff19");
                    PauseTransition pause = getPauseTransition(event);
                    pause.play();
                } else {
                    query = "SELECT * FROM USERACCOUNT WHERE nameAccount = ? AND passwordAccount = ?";;
                    assert connection != null;
                    try (PreparedStatement stmt2 = connection.prepareStatement(query)) {
                        stmt2.setString(1, username);
                        stmt2.setString(2, password);
                        ResultSet resultSet2 = stmt2.executeQuery();
                        if(resultSet2.next()) {
                            alertLabel.setText("Login successful");
                            alertLabel.setStyle("-fx-text-fill: #00ff19");
                            PauseTransition pause = getPauseTransition(event);
                            pause.play();
                        } else {
                            alertLabel.setText("Password or usernameField is incorrect");
                            alertLabel.setStyle("-fx-text-fill: #ff0000");
                            passwordField.setText("");
                        }
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("Login in failed");
            e.printStackTrace();
        }
    }

    @NotNull
    private PauseTransition getPauseTransition(ActionEvent event) {
        PauseTransition pause = new PauseTransition(Duration.millis(500));
        pause.setOnFinished(e -> {
            try {
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                Parent root = FXMLLoader.load(Objects.requireNonNull(
                        getClass().getResource("/sourceCode/Menu.fxml")));
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.setTitle("Library Management System");
                stage.setResizable(false);
                stage.centerOnScreen();
                stage.show();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        return pause;
    }

}


