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
import sourceCode.Services.Service;
import sourceCode.Services.SwitchScene;

public class User extends SwitchScene implements Initializable {

    private static final String selectAllQuery = "SELECT userId, name, identityNumber, birth, gender, phoneNumber, email, address, password FROM library.User";
    private static final ObservableList<sourceCode.Models.User> userList = FXCollections.observableArrayList();
    private static final String[] searchBy = {"UserID", "Name", "IdentityNumber"};
    @FXML
    private TableView<sourceCode.Models.User> userTableView;
    @FXML
    private TableColumn<sourceCode.Models.User, String> useridColumn;
    @FXML
    private TableColumn<sourceCode.Models.User, String> fullnameColumn;
    @FXML
    private TableColumn<sourceCode.Models.User, String> identitynumberColumn;
    @FXML
    private TableColumn<sourceCode.Models.User, String> birthColumn;
    @FXML
    private ChoiceBox<String> choiceBox;
    @FXML
    private TextField searchBar;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        choiceBox.getItems().addAll(searchBy);
        choiceBox.setValue("Search by");
        userTableView.setItems(userList);
        useridColumn.setCellValueFactory(new PropertyValueFactory<>("userId"));
        fullnameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        identitynumberColumn.setCellValueFactory(new PropertyValueFactory<>("identityNumber"));
        birthColumn.setCellValueFactory(new PropertyValueFactory<>("birth"));
        selectUser(selectAllQuery);
    }

    public void selectUser(String query) {
        userList.clear();
        try (Connection conn = Service.getConnection()) {
            assert conn != null;
            try (Statement stmt = conn.createStatement();
                    ResultSet rs = stmt.executeQuery(query)) {
                while (rs.next()) {
                    sourceCode.Models.User user = new sourceCode.Models.User(
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
    }

    public void searchUser() {
        userList.clear();
        String query =
                "SELECT userId, name, identityNumber, birth, gender, phoneNumber, email, address, password FROM library.User WHERE "
                        + choiceBox.getValue() + " LIKE '%" + searchBar.getText() + "%'";
        selectUser(query);
    }

    public void showUser() {
        sourceCode.Models.User user = userTableView.getSelectionModel().getSelectedItem();
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
        sourceCode.Models.User user = userTableView.getSelectionModel().getSelectedItem();
        if (user == null) {
            System.out.println("No user selected");
            return;
        }
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
        sourceCode.Models.User user = userTableView.getSelectionModel().getSelectedItem();
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
