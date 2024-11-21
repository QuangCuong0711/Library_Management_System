package sourceCode.AdminControllers;

import java.time.LocalDate;
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

public class Ticket extends SwitchScene implements Initializable {

    private static final String selectAllQuery = """
            SELECT *,CASE\
                    WHEN returnDate IS NOT NULL AND DATEDIFF(returnDate, borrowDate) <= 30 THEN 'On time'
                    WHEN returnDate IS NOT NULL AND DATEDIFF(returnDate, borrowDate) > 30 THEN 'Late'
                    WHEN returnDate IS NULL AND DATEDIFF(CURDATE(), borrowDate) <= 30 THEN 'Borrowing'
                    WHEN returnDate IS NULL AND DATEDIFF(CURDATE(), borrowDate) > 30 THEN 'Overdue'
                    ELSE 'Unknown'
                END AS status FROM library.Ticket""";
    private static final ObservableList<sourceCode.Models.Ticket> ticketList = FXCollections.observableArrayList();
    private static final String[] searchBy = {"UserID", "ISBN"};
    @FXML
    public TableColumn<sourceCode.Models.Ticket, LocalDate> borrowDateColumn;
    @FXML
    public TableColumn<sourceCode.Models.Ticket, LocalDate> returnDateColumn;
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
    private TableColumn<sourceCode.Models.Ticket, Integer> quantityColumn;

    public void initialize(URL url, ResourceBundle resourceBundle) {
        choiceBox.getItems().addAll(searchBy);
        choiceBox.setValue("Bộ lọc");
        ticketTableView.setItems(ticketList);
        ticketIDColumn.setCellValueFactory(new PropertyValueFactory<>("ticketID"));
        uidColumn.setCellValueFactory(new PropertyValueFactory<>("userID"));
        isbnColumn.setCellValueFactory(new PropertyValueFactory<>("ISBN"));
        borrowDateColumn.setCellValueFactory(new PropertyValueFactory<>("borrowDate"));
        returnDateColumn.setCellValueFactory(new PropertyValueFactory<>("returnDate"));
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
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
                            rs.getString("ISBN"),
                            rs.getString("userId"),
                            rs.getDate("borrowDate") != null ? rs.getDate("borrowDate").toLocalDate() : null,
                            rs.getDate("returnDate") != null ? rs.getDate("returnDate").toLocalDate() : null,
                            rs.getInt("quantity")
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
        String query =
                selectAllQuery + " WHERE " + choiceBox.getValue() + " LIKE '%" + searchBar.getText()
                        + "%'";
        selectTicket(query);
    }

    public void showUser() throws IOException {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/sourceCode/AdminFXML/ShowUser.fxml"));
        Parent root = loader.load();
        ShowUser showUser = loader.getController();
        sourceCode.Models.Ticket selectedTicket = ticketTableView.getSelectionModel()
                .getSelectedItem();
        if (selectedTicket == null) {
            return;
        }
        String userID = selectedTicket.getUserID();
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