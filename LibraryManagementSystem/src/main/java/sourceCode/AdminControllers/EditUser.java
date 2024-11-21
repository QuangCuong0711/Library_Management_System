package sourceCode.AdminControllers;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javafx.scene.control.Button;
import javafx.event.ActionEvent;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sourceCode.Services.Service;

public class EditUser {

    public TextField userID;
    public TextField name;
    public TextField identityNumber;
    public ChoiceBox<String> gender;
    public TextField mail;
    public TextField phoneNumber;
    public TextField address;
    public DatePicker birth;
    private User user;

    public void setUser(sourceCode.Models.User user) {
        name.setText(user.getName());
        userID.setText(user.getUserId());
        identityNumber.setText(user.getIdentityNumber());
        birth.setValue(user.getBirth());
        mail.setText(user.getEmail());
        address.setText(user.getAddress());
        phoneNumber.setText(user.getPhoneNumber());
        gender.setValue(user.getGender());
    }

    public void setUserController(User user) {
        this.user = user;
    }

    public void confirmButtonOnAction(ActionEvent event) {
        String query = "UPDATE library.user SET name = ?, identityNumber = ?, birth = ?, gender = ?, phoneNumber = ?, email = ?, address = ? WHERE userId = ?";
        try (Connection connection = Service.getConnection()) {
            assert connection != null;
            try (PreparedStatement stmt = connection.prepareStatement(query)) {
                stmt.setString(1, name.getText());
                stmt.setString(2, identityNumber.getText());
                stmt.setDate(3, Date.valueOf(birth.getValue()));
                stmt.setString(4, gender.getValue());
                stmt.setString(5, phoneNumber.getText());
                stmt.setString(6, mail.getText());
                stmt.setString(7, address.getText());
                stmt.setString(8, userID.getText());
                stmt.executeUpdate();
                System.out.println("User edited successfully");
            }
        } catch (SQLException e) {
            System.out.println("Can't edit this user");
            e.printStackTrace();
        }
        user.initialize(null, null);
        cancelButtonOnAction(event);
    }

    public void cancelButtonOnAction(ActionEvent event) {
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.close();
    }
}