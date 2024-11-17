package sourceCode.Controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import sourceCode.Services.Service;
import sourceCode.Models.User;

public class UserController implements Initializable {

    ObservableList<User> userList = FXCollections.observableArrayList();
    String[] choices = {"UserID", "Name", "IdentityNumber"};
    @FXML
    private TableView<User> userTableView;
    @FXML
    private TableColumn<User, String> useridColumn;
    @FXML
    private TableColumn<User, String> fullnameColumn;
    @FXML
    private TableColumn<User, String> identitynumberColumn;
    @FXML
    private TableColumn<User, String> birthColumn;
    @FXML
    private ChoiceBox<String> choiceBox;
    @FXML
    private TextField searchBar;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        choiceBox.getItems().addAll(choices);
        choiceBox.setValue("UserID");
        userList.clear();
        String query = "SELECT userId, name, identityNumber, birth, gender, phoneNumber, email, address FROM library.User";
        try (Connection conn = Service.getConnection()) {
            assert conn != null;
            try (Statement stmt = conn.createStatement();
                    ResultSet rs = stmt.executeQuery(query)) {
                while (rs.next()) {
                    User user = new User(
                            rs.getString("userId"),
                            rs.getString("name"),
                            rs.getString("identityNumber"),
                            rs.getDate("birth").toLocalDate(),
                            rs.getString("gender"),
                            rs.getString("phoneNumber"),
                            rs.getString("email"),
                            rs.getString("address")
                    );
                    userList.add(user);
                }
            }
            useridColumn.setCellValueFactory(new PropertyValueFactory<>("userId"));
            fullnameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
            identitynumberColumn.setCellValueFactory(new PropertyValueFactory<>("identityNumber"));
            birthColumn.setCellValueFactory(new PropertyValueFactory<>("birth"));
            userTableView.setItems(userList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void searchUser() {
        userList.clear();
        String query =
                "SELECT userId, name, identityNumber, birth, gender, phoneNumber, email, address FROM library.User WHERE "
                        + choiceBox.getValue() + " LIKE '%" + searchBar.getText() + "%'";
        try (Connection conn = Service.getConnection()) {
            assert conn != null;
            try (Statement stmt = conn.createStatement();
                    ResultSet rs = stmt.executeQuery(query)) {
                while (rs.next()) {
                    User user = new User(
                            rs.getString("userId"),
                            rs.getString("name"),
                            rs.getString("identityNumber"),
                            rs.getDate("birth").toLocalDate(),
                            rs.getString("gender"),
                            rs.getString("phoneNumber"),
                            rs.getString("email"),
                            rs.getString("address")
                    );
                    userList.add(user);
                }
            }
            useridColumn.setCellValueFactory(new PropertyValueFactory<>("userId"));
            fullnameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
            identitynumberColumn.setCellValueFactory(new PropertyValueFactory<>("identityNumber"));
            birthColumn.setCellValueFactory(new PropertyValueFactory<>("birth"));
            userTableView.setItems(userList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void showUser() {
        User user = userTableView.getSelectionModel().getSelectedItem();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/sourceCode/ShowUser.fxml"));
            Parent root = loader.load();
            ShowUserController showUserController = loader.getController();
            showUserController.setUser(user);
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("User Information");
            stage.initStyle(StageStyle.UNDECORATED);
            stage.centerOnScreen();
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void removeUser() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Remove User");
        alert.setHeaderText("Can't restore this user after removing");
        alert.setContentText("Do you want to remove this user ?");
        Optional<ButtonType> a = alert.showAndWait();
        if (a.isEmpty() || a.get() != ButtonType.OK) {
            return;
        }
        User user = userTableView.getSelectionModel().getSelectedItem();
        String query = "DELETE FROM library.user WHERE userId = ?";
        try (Connection connection = Service.getConnection()) {
            assert connection != null;
            try (PreparedStatement stmt = connection.prepareStatement(query)) {
                stmt.setString(1, user.getUserId());
                stmt.executeUpdate();
                userList.remove(user);
                System.out.println("User removed successfully");
            }
        } catch (SQLException e) {
            System.out.println("User removing failed");
            e.printStackTrace();
        }
    }

    public void editUser() {
        User user = userTableView.getSelectionModel().getSelectedItem();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/sourceCode/EditUser.fxml"));
            Parent root = loader.load();
            EditUserController editUserController = loader.getController();
            editUserController.setUser(user);
            editUserController.setUserController(this);
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Edit User");
            stage.initStyle(StageStyle.UNDECORATED);
            stage.centerOnScreen();
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void addUser() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/sourceCode/AddUser.fxml"));
            Parent root = loader.load();
            AddUserController addUserController = loader.getController();
            addUserController.setUserController(this);
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Add User");
            stage.initStyle(StageStyle.UNDECORATED);
            stage.centerOnScreen();
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

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
                        Objects.requireNonNull(getClass().getResource("/sourceCode/Login.fxml")));
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.centerOnScreen();
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
