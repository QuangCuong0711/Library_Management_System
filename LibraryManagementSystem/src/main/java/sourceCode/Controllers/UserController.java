package sourceCode.Controllers;

import java.io.IOException;
import java.util.Objects;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class UserController {

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

    public void switchMenu(ActionEvent event) {
        try {
            Button button = (Button) event.getSource();
            Stage stage = (Stage) button.getScene().getWindow();
            Parent root = FXMLLoader.load(
                    Objects.requireNonNull(getClass().getResource("/sourceCode/Menu.fxml")));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
