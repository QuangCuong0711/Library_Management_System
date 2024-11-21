package sourceCode.AdminControllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import sourceCode.Services.Service;
import sourceCode.Services.SwitchScene;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class Feedback extends SwitchScene implements Initializable {

    private static final String selectAllQuery = "SELECT * FROM library.Feedback";
    private static final ObservableList<sourceCode.Models.Feedback> feedBackList = FXCollections.observableArrayList();
    private static final String[] searchBy = {"UserID", "ISBN", "Rating"};
    public TableColumn<Feedback, Integer> feedbackidColumn;
    public TableColumn<Feedback, String> uidColumn;
    public TableColumn<Feedback, String> isbnColumn;
    public TableColumn<Feedback, Integer> ratingColumn;
    public TableColumn<Feedback, String> dateColumn;
    public TableColumn<Feedback, String> commentColumn;
    @FXML
    private TableView<sourceCode.Models.Feedback> feedbackTableView;
    @FXML
    private ChoiceBox<String> choiceBox;
    @FXML
    private TextField searchBar;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        choiceBox.getItems().addAll(searchBy);
        choiceBox.setValue("Bộ lọc");
        feedbackTableView.setItems(feedBackList);
        feedbackidColumn.setCellValueFactory(new PropertyValueFactory<>("feedbackID"));
        uidColumn.setCellValueFactory(new PropertyValueFactory<>("userID"));
        isbnColumn.setCellValueFactory(new PropertyValueFactory<>("ISBN"));
        ratingColumn.setCellValueFactory(new PropertyValueFactory<>("rating"));
        commentColumn.setCellValueFactory(new PropertyValueFactory<>("comment"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        selectFeedback(selectAllQuery);
    }

    public void selectFeedback(String query) {
        feedBackList.clear();
        try (Connection conn = Service.getConnection()) {
            assert conn != null;
            try (Statement stmt = conn.createStatement();
                    ResultSet rs = stmt.executeQuery(query)) {
                while (rs.next()) {
                    sourceCode.Models.Feedback feedback = new sourceCode.Models.Feedback(
                            rs.getInt("feedbackId"),
                            rs.getString("userId"),
                            rs.getString("ISBN"),
                            rs.getString("comment"),
                            rs.getInt("rating"),
                            rs.getDate("date").toLocalDate()
                    );
                    feedBackList.add(feedback);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void searchFeedback() {
        feedBackList.clear();
        String query = "SELECT * FROM library.Feedback WHERE " + choiceBox.getValue() + " LIKE '%" +
                searchBar.getText() + "%'";
        selectFeedback(query);
    }

    public void showUser() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/sourceCode/AdminFXML/ShowUser.fxml"));
        Parent root = loader.load();
        ShowUser showUser = loader.getController();
        sourceCode.Models.Feedback selectedFeedback = feedbackTableView.getSelectionModel().getSelectedItem();
        if (selectedFeedback == null) {
            return;
        }
        String userID = selectedFeedback.getUserID();
        String query = "SELECT * FROM library.User WHERE userId = '" + userID + "';";
        try (Connection conn = Service.getConnection()) {
            assert conn != null;
            try (Statement stmt = conn.createStatement();
                    ResultSet rs = stmt.executeQuery(query)) {
                if (rs.next()) {
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
                    showUser.setUser(user);
                }
            }
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("User Information");
            stage.initStyle(StageStyle.UNDECORATED);
            stage.centerOnScreen();
            stage.show();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}