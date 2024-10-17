package org.example.oop1;

import User.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class UserController extends MenuController implements Initializable {

    @FXML
    private TableView<User> table ;

    @FXML
    private TableColumn<User, String> id;

    @FXML
    private TableColumn<User, String> name ;

    @FXML
    private TableColumn<User, String> address ;

    @FXML
    private TableColumn<User, String> phone ;

    @FXML
    private TableColumn<User, String> date ;

    @FXML
    private TableColumn<User, String> gender ;

    @FXML
    private TableColumn<User, String> CCCD ;

    @FXML
    private TextField idTextField ;

    @FXML
    private TextField nameTextField ;

    @FXML
    private TextField addressTextField ;

    @FXML
    private TextField phoneTextField ;

    @FXML
    private TextField dateTextField ;

    @FXML
    private TextField genderTextField ;

    @FXML
    private TextField CCCDTextField ;

    private ObservableList<User> userList;

    // Cài đặt thuộc tính vào TableView
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        userList = FXCollections.observableArrayList(
                new User("Cuong", "23021484", "07/11/2005", "0357989017", "026205007140", "Tân Phong", "Nam"),
                new User("Hung","23021567","","","","","")
        );
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        address.setCellValueFactory(new PropertyValueFactory<>("address"));
        phone.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        date.setCellValueFactory(new PropertyValueFactory<>("date"));
        gender.setCellValueFactory(new PropertyValueFactory<>("gender"));
        CCCD.setCellValueFactory(new PropertyValueFactory<>("CCCD"));
        table.setItems(userList);
    }

    //Thêm thông tin
    public void add (ActionEvent event) {
        if (idTextField.getText().isEmpty() || nameTextField.getText().isEmpty() || addressTextField.getText().isEmpty()|| phoneTextField.getText().isEmpty()|| dateTextField.getText().isEmpty()|| genderTextField.getText().isEmpty()|| phoneTextField.getText().isEmpty() ){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Please fill all the fields");
            alert.showAndWait();
            return;
        } else {
            User user = new User();
            user.setId(idTextField.getText());
            user.setName(nameTextField.getText());
            user.setAddress(addressTextField.getText());
            user.setPhoneNumber(phoneTextField.getText());
            user.setDate(dateTextField.getText());
            user.setGender(genderTextField.getText());
            user.setCCCD(CCCDTextField.getText());
            userList.add(user);
        }

    }


    public void delete(ActionEvent event) {
        User user = table.getSelectionModel().getSelectedItem();
        if (user != null) {
            userList.remove(user);
        }
    }

    public void edit(ActionEvent event) {
        User user = table.getSelectionModel().getSelectedItem();
        if (user != null) {
            user.setId(idTextField.getText());
            user.setName(nameTextField.getText());
            user.setAddress(addressTextField.getText());
            user.setPhoneNumber(phoneTextField.getText());
            user.setDate(dateTextField.getText());
            user.setGender(genderTextField.getText());
            user.setCCCD(CCCDTextField.getText());
            table.refresh();
        }
    }

    public void display(ActionEvent event) {
        User user = table.getSelectionModel().getSelectedItem();
        if (user != null) {
            idTextField.setText(user.getId());
            nameTextField.setText(user.getName());
            addressTextField.setText(user.getAddress());
            phoneTextField.setText(user.getPhoneNumber());
            dateTextField.setText(user.getDate());
            genderTextField.setText(user.getGender());
            CCCDTextField.setText(user.getCCCD());
        }
    }
}
