package sourceCode.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;

import java.io.IOException;
import javafx.stage.Stage;


public class WelcomeController {

    @FXML
    private TextField username;

    @FXML
    private TextField password;


    // Màn hình đăng nhập
    public void Login (ActionEvent event) {
        // Check nhập đầy đủ thông tin hay chưa
        if(username.getText().isEmpty() || password.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Please enter all the fields");
            alert.showAndWait();
        }
        else {
            // Đăng nhập thành công
            // Cải tiến sẽ đọc dữ liệu tài khoản từ file ngoài xem có đúng tk ko
            try {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Đăng nhập");
                alert.setHeaderText(null);
                alert.setContentText("Đăng nhập thành công");
                alert.showAndWait();

                // Lấy Stage từ sự kiện hiện tại
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

                // Tải giao diện mới từ FXML (ví dụ: dashboard.fxml)
                Parent root = FXMLLoader.load(getClass().getResource("/sourceCode/Menu.fxml"));

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


