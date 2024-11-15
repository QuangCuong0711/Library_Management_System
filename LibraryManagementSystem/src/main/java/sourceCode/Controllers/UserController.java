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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import sourceCode.Models.Service;
import sourceCode.Models.User;

public class UserController implements Initializable {

    ObservableList<User> userList = FXCollections.observableArrayList();
    @FXML
    private Button menuButton;
    @FXML
    private Button documentButton;
    @FXML
    private Button userButton;
    @FXML
    private Button ticketButton;
    @FXML
    private Button logoutButton;
    @FXML
    private Button addUserButton;
    @FXML
    private Button removeUserButton;
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
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        String query = "SELECT userId, name, identityNumber, birth FROM library.User";
        try (Connection conn = Service.getConnection()) {
            assert conn != null;
            try (Statement stmt = conn.createStatement();
                    ResultSet rs = stmt.executeQuery(query)) {
                while (rs.next()) {
                    User user = new User(
                            rs.getString("name"),
                            rs.getString("userId"),
                            rs.getString("identityNumber"),
                            rs.getString("birth"),
                            null,
                            null,
                            null,
                            null
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

    public void addUser(ActionEvent event) {
        User newUser = new User();
        String query = "INSERT INTO library.user (userId, name, identityNumber, birth, gender, phoneNumber, email, address) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = Service.getConnection()) {
            assert connection != null;
            try (PreparedStatement stmt = connection.prepareStatement(query)) {
                stmt.setString(1, newUser.getUserId());
                stmt.setString(2, newUser.getName());
                stmt.setString(3, newUser.getIdentityNumber());
                stmt.setString(4, newUser.getBirth());
                stmt.setString(5, newUser.getGender());
                stmt.setString(6, newUser.getPhoneNumber());
                stmt.setString(7, newUser.getEmail());
                stmt.setString(8, newUser.getAddress());
                stmt.executeUpdate();
                System.out.println("User added successfully");
            }
        } catch (SQLException e) {
            System.out.println("User adding failed");
            e.printStackTrace();
        }
    }

    public void removeUser(ActionEvent event) {
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
