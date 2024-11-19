package sourceCode.Services;

import java.io.IOException;
import java.util.Objects;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class SwitchScene {

    public void switchTo(ActionEvent event, String fxml) {
        try {
            String path = "/sourceCode/" + fxml + ".fxml";
            Parent root = FXMLLoader.load(
                    Objects.requireNonNull(getClass().getResource(path)));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.centerOnScreen();
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void switchToHome(ActionEvent event) {
        switchTo(event, "Home");
    }

    public void switchToUser(ActionEvent event) {
        switchTo(event, "User");
    }

    public void switchToBook(ActionEvent event) {
        switchTo(event, "Book");
    }

    public void switchToTicket(ActionEvent event) {
        switchTo(event, "Ticket");
    }

    public void switchToFeedback(ActionEvent event) {
        switchTo(event, "Feedback");
    }

    public void switchToSetting(ActionEvent event) {
        switchTo(event, "Setting");
    }

    public void switchToLogin(ActionEvent event) {
        switchTo(event, "Login");
    }
}
