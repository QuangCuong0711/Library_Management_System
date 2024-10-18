package sourceCode.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;

public class MenuController {

    public void exit() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Thoát");
        alert.setHeaderText(null);
        alert.setContentText("Bạn có chắc chắn muốn thoát ?");
        Optional<ButtonType> a = alert.showAndWait();
        if (a.get() == ButtonType.OK) {
            System.exit(0);
        }
    }

    public void Readermanagement(ActionEvent event) {
        try {
            MenuItem menuItem = (MenuItem) event.getSource();
            Stage stage = (Stage) menuItem.getParentPopup().getOwnerWindow();
            Parent root = FXMLLoader.load(getClass().getResource("/sourceCode/User.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void Documentmanagement(ActionEvent event) {
        try {
            MenuItem menuItem = (MenuItem) event.getSource();
            Stage stage = (Stage) menuItem.getParentPopup().getOwnerWindow();
            Parent root = FXMLLoader.load(getClass().getResource("/sourceCode/Document.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void Ticketmanagement(ActionEvent event) {
        try {
            MenuItem menuItem = (MenuItem) event.getSource();
            Stage stage = (Stage) menuItem.getParentPopup().getOwnerWindow();
            Parent root = FXMLLoader.load(getClass().getResource("/sourceCode/Ticket.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
