package sourceCode.Controllers;

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

public class LoginController {

    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Label alertLabel;

    public void login(ActionEvent event) {
        if (!usernameField.getText().equals("admin") || !passwordField.getText().equals("admin")) {
            // Checking if the usernameField and passwordField are correct
            alertLabel.setText("Password or usernameField is incorrect");
            alertLabel.setStyle("-fx-text-fill: #ff0000");
            passwordField.setText("");
        } else {
            // login successful
            alertLabel.setText("Login successful");
            alertLabel.setStyle("-fx-text-fill: #00ff19");
            PauseTransition pause = getPauseTransition(event);
            pause.play();
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


