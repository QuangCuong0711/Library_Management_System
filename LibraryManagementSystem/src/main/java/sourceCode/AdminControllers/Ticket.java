package sourceCode.AdminControllers;

import javafx.application.Application;
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
import sourceCode.Models.Book;
import sourceCode.Services.Service;
import sourceCode.Services.SwitchScene;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class Ticket extends SwitchScene implements Initializable {

    private static final String selectAllQuery = "SELECT *,CASE\n" +
            "        WHEN returnDate IS NOT NULL AND DATEDIFF(returnDate, borrowDate) <= 7 THEN 'đã trả đúng hạn'\n" +
            "        WHEN returnDate IS NOT NULL AND DATEDIFF(returnDate, borrowDate) > 7 THEN 'trả muộn'\n" +
            "        WHEN returnDate IS NULL AND DATEDIFF(CURDATE(), borrowDate) <= 7 THEN 'chưa trả'\n" +
            "        WHEN returnDate IS NULL AND DATEDIFF(CURDATE(), borrowDate) > 7 THEN 'quá hạn'\n" +
            "        ELSE 'không xác định'\n" +
            "    END AS status FROM library.Ticket";
    private static final ObservableList<sourceCode.Models.Ticket> ticketList = FXCollections.observableArrayList();
    private static final String[] searchBy = {"userId", "bookId"};


    @FXML
    private TableView<sourceCode.Models.Ticket> ticketTableView;

    @FXML
    private ChoiceBox<String> choiceBox;

    @FXML
    private TextField searchBar;

    @FXML
    private TableColumn<sourceCode.Models.Ticket, Integer> ticketIDColumn;

    @FXML
    private TableColumn<sourceCode.Models.Ticket, String> uidColumn;

    @FXML
    private TableColumn<sourceCode.Models.Ticket, String> isbnColumn;

    @FXML
    private TableColumn<sourceCode.Models.Ticket, String> statusColumn;

    @FXML
    private TableColumn<sourceCode.Models.Ticket, Integer> quantityColumn;

    public void initialize(URL url, ResourceBundle resourceBundle) {
        choiceBox.getItems().addAll(searchBy);
        choiceBox.setValue("Bộ lọc");
        ticketTableView.setItems(ticketList);
        ticketIDColumn.setCellValueFactory(new PropertyValueFactory<>("ticketID"));
        uidColumn.setCellValueFactory(new PropertyValueFactory<>("userID"));
        isbnColumn.setCellValueFactory(new PropertyValueFactory<>("bookID"));
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
        selectTicket(selectAllQuery);
    }

    public void selectTicket(String query) {
        ticketList.clear();
        try (Connection conn = Service.getConnection()) {
            assert conn != null;
            try (Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(query)) {
                while (rs.next()) {
                    sourceCode.Models.Ticket ticket = new sourceCode.Models.Ticket(
                            rs.getInt("ticketId"),
                            rs.getString("bookId"),
                            rs.getString("userId"),
                            rs.getDate("borrowDate").toLocalDate(),
                            rs.getDate("returnDate").toLocalDate(),
                            rs.getInt("quantity"),
                            rs.getString("status")
                    );
                    ticketList.add(ticket);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void searchTicket() {
        ticketList.clear();
        String query = selectAllQuery + " WHERE " + choiceBox.getValue() + " LIKE '%" + searchBar.getText() + "%'";
        selectTicket(query);
    }

    public void showTicket() {
        sourceCode.Models.Ticket ticket = ticketTableView.getSelectionModel().getSelectedItem();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(
                    ""));
            Parent root = loader.load();
            ShowTicket showTicket = loader.getController();
            showTicket.setTicket(ticket);
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Ticket Information");
            stage.initStyle(StageStyle.UNDECORATED);
            stage.centerOnScreen();
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
