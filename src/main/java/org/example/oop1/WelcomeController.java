package org.example.oop1;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.stage.Stage;


public class WelcomeController {

    @FXML
    private TextField username;

    @FXML
    private TextField password;



    public void Login (ActionEvent event) {
        if(username.getText().isEmpty() || password.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Please enter all the fields");
            alert.showAndWait();
        }
        else {
            try {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Đăng nhập");
                alert.setHeaderText(null);
                alert.setContentText("Đăng nhập thành công");
                alert.showAndWait();

                // Lấy Stage từ sự kiện hiện tại
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

                // Tải giao diện mới từ FXML (ví dụ: dashboard.fxml)
                Parent root = FXMLLoader.load(getClass().getResource("Menu.fxml"));

                // Tạo scene mới và set scene cho Stage
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();

            } catch (IOException e) {
                e.printStackTrace();
            }
           }
    }

}


