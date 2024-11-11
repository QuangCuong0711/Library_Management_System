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

public class WelcomeController {

    @FXML
    private TextField username;
    @FXML
    private PasswordField password;
    @FXML
    private Label alert;
    @FXML
    private Button loginButton;

    public void Login(ActionEvent event) {
        if (!username.getText().equals("admin") || !password.getText().equals("admin")) {
            // Checking if the username and password are correct
            alert.setText("Password or username is incorrect");
            alert.setStyle("-fx-text-fill: red");
            password.setText("");
        } else {
            // Login successful
            alert.setText("Login successful");
            alert.setStyle("-fx-text-fill: green");
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
            pause.play();
        }
    }

}


