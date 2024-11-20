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
import sourceCode.Models.User;
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
    private static final String[] searchBy = {"userId", "bookId"};

    @FXML
    private TableView<sourceCode.Models.Feedback> feedBackTableView;

    @FXML
    private TableColumn<sourceCode.Models.Feedback, Integer> feedbackidColumn;

    @FXML
    private TableColumn<sourceCode.Models.Feedback, String> uidColumn;

    @FXML
    private TableColumn<sourceCode.Models.Feedback, String> isbnColumn;

    @FXML
    private TableColumn<sourceCode.Models.Feedback, Integer> ratingColumn;

    @FXML
    private TableColumn<sourceCode.Models.Feedback, String> commentColumn;

    @FXML
    private TableColumn<sourceCode.Models.Feedback, String> dateColumn;

    @FXML
    private ChoiceBox<String> choiceBox;

    @FXML
    private TextField searchBar;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        choiceBox.getItems().addAll(searchBy);
        choiceBox.setValue("Bộ lọc");
        feedBackTableView.setItems(feedBackList);
        feedbackidColumn.setCellValueFactory( new PropertyValueFactory<>("feedbackID"));
        uidColumn.setCellValueFactory( new PropertyValueFactory<>("userID"));
        isbnColumn.setCellValueFactory( new PropertyValueFactory<>("ISBN"));
        ratingColumn.setCellValueFactory( new PropertyValueFactory<>("rating"));
        commentColumn.setCellValueFactory( new PropertyValueFactory<>("comment"));
        dateColumn.setCellValueFactory( new PropertyValueFactory<>("date"));
        selectFeedBack(selectAllQuery);
    }

    public void selectFeedBack(String query) {
        feedBackList.clear();
        try (Connection conn = Service.getConnection()) {
            assert conn != null;
            try (Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(query)) {
                while (rs.next()) {
                    sourceCode.Models.Feedback feedback = new sourceCode.Models.Feedback(
                            rs.getInt("feedbackId"),
                            rs.getString("userId"),
                            rs.getString("bookId"),
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

    public void searchFeedBack() {
        feedBackList.clear();
        String query = "SELECT * FROM library.Feedback WHERE " + choiceBox.getValue() + " LIKE '%" +
                searchBar.getText() + "%'";
        selectFeedBack(query);
    }

    public void showFeedback() {
        sourceCode.Models.Feedback feedback = feedBackTableView.getSelectionModel().getSelectedItem();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(
                    ""));
            Parent root = loader.load();
            ShowFeedBack showFeedBack = loader.getController();
            showFeedBack.setFeedback(feedback);
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("FeedBack Information");
            stage.initStyle(StageStyle.UNDECORATED);
            stage.centerOnScreen();
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
