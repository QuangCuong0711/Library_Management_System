package sourceCode.Controllers;

import java.util.Objects;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.Optional;
import javax.swing.text.html.ImageView;

public class MenuController {

    @FXML
    private Button menu;
    @FXML
    private Button document;
    @FXML
    private Button user;
    @FXML
    private Button ticket;
    @FXML
    private Button logout;


    public void switchDocumentManagement(ActionEvent event) {
        try {
            Button button = (Button) event.getSource();
            Stage stage = (Stage) button.getScene().getWindow();
            Parent root = FXMLLoader.load(
                    Objects.requireNonNull(getClass().getResource("/sourceCode/Document.fxml")));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void switchUserManagement(ActionEvent event) {
        try {
            Button button = (Button) event.getSource();
            Stage stage = (Stage) button.getScene().getWindow();
            Parent root = FXMLLoader.load(
                    Objects.requireNonNull(getClass().getResource("/sourceCode/User.fxml")));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void switchTicketManagement(ActionEvent event) {
        try {
            Button button = (Button) event.getSource();
            Stage stage = (Stage) button.getScene().getWindow();
            Parent root = FXMLLoader.load(
                    Objects.requireNonNull(getClass().getResource("/sourceCode/Document.fxml")));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void logout(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Log out");
        alert.setHeaderText(null);
        alert.setContentText("Do you want to log out ?");
        Optional<ButtonType> a = alert.showAndWait();
        if (a.isPresent() && a.get() == ButtonType.OK) {
            try {
                Button button = (Button) event.getSource();
                Stage stage = (Stage) button.getScene().getWindow();
                Parent root = FXMLLoader.load(
                        Objects.requireNonNull(getClass().getResource("/sourceCode/Welcome.fxml")));
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.centerOnScreen();
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }


    public void exit() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Thoát");
        alert.setHeaderText(null);
        alert.setContentText("Bạn có chắc chắn muốn thoát ?");
        Optional<ButtonType> a = alert.showAndWait();
        if (a.isPresent() && a.get() == ButtonType.OK) {
            System.exit(0);
        }
    }
}
