package sourceCode.AdminControllers;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import sourceCode.AdminControllers.Function.AddUser;
import sourceCode.AdminControllers.Function.EditUser;
import sourceCode.AdminControllers.Function.ShowUser;
import sourceCode.Models.User;
import sourceCode.Services.DatabaseConnection;
import sourceCode.Services.SwitchScene;

public class UserController extends SwitchScene implements Initializable {

    private static final String selectAllQuery = "SELECT userId, name, identityNumber, birth, gender, phoneNumber, email, address, password FROM library.User";
    private static final ObservableList<User> userList = FXCollections.observableArrayList();
    private static final String[] searchBy = {"Tất cả", "Mã người dùng", "Họ và tên", "Số CCCD",
            "Ngày sinh"};
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
        choiceBox.getItems().addAll(searchBy);
        choiceBox.setValue("Tìm kiếm theo");
        userTableView.setItems(userList);
        useridColumn.setCellValueFactory(new PropertyValueFactory<>("userId"));
        fullnameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        identitynumberColumn.setCellValueFactory(new PropertyValueFactory<>("identityNumber"));
        birthColumn.setCellValueFactory(new PropertyValueFactory<>("birth"));
        selectUser(selectAllQuery);
    }

    public void selectUser(String query) {
        userList.clear();
        Runnable task = () -> {
            try (Connection connection = DatabaseConnection.getInstance().getConnection()) {
                assert connection != null;
                try (Statement stmt = connection.createStatement();
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
                                rs.getString("address"),
                                rs.getString("password")
                        );
                        userList.add(user);
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        };
        Thread thread = new Thread(task);
        thread.setDaemon(true);
        thread.start();
    }

    public void searchUser() {
        userList.clear();
        if (choiceBox.getValue().equals("Tất cả")) {
            selectUser(selectAllQuery);
        } else if (choiceBox.getValue().equals("Mã người dùng")) {
            selectUser(selectAllQuery + " WHERE userId LIKE '%" + searchBar.getText() + "%'");
        } else if (choiceBox.getValue().equals("Họ và tên")) {
            selectUser(selectAllQuery + " WHERE name LIKE '%" + searchBar.getText() + "%'");
        } else if (choiceBox.getValue().equals("Số CCCD")) {
            selectUser(
                    selectAllQuery + " WHERE identityNumber LIKE '%" + searchBar.getText() + "%'");
        } else if (choiceBox.getValue().equals("Ngày sinh")) {
            selectUser(selectAllQuery + " WHERE birth LIKE '%" + searchBar.getText() + "%'");
        }
    }

    public void showUser() {
        User user = userTableView.getSelectionModel().getSelectedItem();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(
                    "/sourceCode/AdminFXML/ShowUser.fxml"));
            Parent root = loader.load();
            ShowUser showUser = loader.getController();
            showUser.setUser(user);
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
        if (user == null) {
            System.out.println("No user selected");
            return;
        }
        String query = "DELETE FROM library.user WHERE userId = ?";
        try (Connection connection = DatabaseConnection.getInstance().getConnection()) {
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
            FXMLLoader loader = new FXMLLoader(getClass().getResource(
                    "/sourceCode/AdminFXML/EditUser.fxml"));
            Parent root = loader.load();
            EditUser editUser = loader.getController();
            editUser.setUser(user);
            editUser.setUserController(this);
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
            FXMLLoader loader = new FXMLLoader(getClass().getResource(
                    "/sourceCode/AdminFXML/AddUser.fxml"));
            Parent root = loader.load();
            AddUser addUser = loader.getController();
            addUser.setUserController(this);
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
}
