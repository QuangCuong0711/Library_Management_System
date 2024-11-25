package sourceCode.AdminControllers;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableCell;
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

public class TicketController extends SwitchScene implements Initializable {

    private static final String selectAllQuery = """
            SELECT *,CASE\
                    WHEN returnedDate IS NOT NULL AND DATEDIFF(returnedDate, borrowedDate) <= 30 THEN 'Trả đúng hạn'
                    WHEN returnedDate IS NOT NULL AND DATEDIFF(returnedDate, borrowedDate) > 30 THEN 'Trả muộn'
                    WHEN returnedDate IS NULL AND DATEDIFF(CURDATE(), borrowedDate) <= 30 THEN 'Đang mượn'
                    WHEN returnedDate IS NULL AND DATEDIFF(CURDATE(), borrowedDate) > 30 THEN 'Quá hạn'
                    ELSE 'Không xác định'
                END AS status FROM library.Ticket""";
    private static final ObservableList<sourceCode.Models.Ticket> ticketList = FXCollections.observableArrayList();
    private static final String[] searchBy = {"Tất cả", "Mã người dùng", "Mã sách", "Ngày mượn",
            "Ngày trả", "Trạng thái"};
    @FXML
    public TableColumn<sourceCode.Models.Ticket, LocalDate> borrowedDateColumn;
    @FXML
    public TableColumn<sourceCode.Models.Ticket, LocalDate> returnedDateColumn;
    @FXML
    public TableColumn<sourceCode.Models.Ticket, String> statusColumn;
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
        choiceBox.setValue("Tìm kiếm theo");
        ticketTableView.setItems(ticketList);
        ticketIDColumn.setCellValueFactory(new PropertyValueFactory<>("ticketID"));
        uidColumn.setCellValueFactory(new PropertyValueFactory<>("userID"));
        isbnColumn.setCellValueFactory(new PropertyValueFactory<>("ISBN"));
        borrowedDateColumn.setCellValueFactory(new PropertyValueFactory<>("borrowedDate"));
        returnedDateColumn.setCellValueFactory(new PropertyValueFactory<>("returnedDate"));
        returnedDateColumn.setCellFactory(
                column -> new TableCell<sourceCode.Models.Ticket, LocalDate>() {
                    @Override
                    protected void updateItem(LocalDate item, boolean empty) {
                        super.updateItem(item, empty);
                        sourceCode.Models.Ticket ticket = getTableRow().getItem();
                        if (empty || ticket == null) {
                            setText(null);
                        } else if (item == null && ticket.getTicketID() != 0) {
                            setText("-");
                        } else if (item != null) {
                            setText(item.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
                        } else {
                            setText(null);
                        }
                    }
                });
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
                            rs.getString("ISBN"),
                            rs.getString("userId"),
                            rs.getDate("borrowedDate") != null ? rs.getDate("borrowedDate")
                                    .toLocalDate() : null,
                            rs.getDate("returnedDate") != null ? rs.getDate("returnedDate")
                                    .toLocalDate() : null,
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
        if (choiceBox.getValue().equals("Tất cả")) {
            selectTicket(selectAllQuery);
        } else if (choiceBox.getValue().equals("Mã người dùng")) {
            selectTicket(selectAllQuery + " WHERE userId LIKE '%" + searchBar.getText() + "%'");
        } else if (choiceBox.getValue().equals("Mã sách")) {
            selectTicket(selectAllQuery + " WHERE ISBN LIKE '%" + searchBar.getText() + "%'");
        } else if (choiceBox.getValue().equals("Ngày trả")) {
            selectTicket(
                    selectAllQuery + " WHERE returnedDate LIKE '%" + searchBar.getText() + "%'");
        } else if (choiceBox.getValue().equals("Ngày mượn")) {
            selectTicket(
                    selectAllQuery + " WHERE borrowedDate LIKE '%" + searchBar.getText() + "%'");
        } else if (choiceBox.getValue().equals("Trạng thái")) {
            selectTicket(selectAllQuery + " HAVING status LIKE '%" + searchBar.getText() + "%'");
        }
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
    public void showBook() throws IOException {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/sourceCode/AdminFXML/ShowBook.fxml"));
        Parent root = loader.load();
        ShowBook showBook = loader.getController();
        sourceCode.Models.Ticket selectedTicket = ticketTableView.getSelectionModel()
                .getSelectedItem();
        if (selectedTicket == null) {
            return;
        }
        String query = "SELECT * FROM library.Book WHERE ISBN = '" + selectedTicket.getISBN() + "';";
        try (Connection conn = Service.getConnection()) {
            assert conn != null;
            try (Statement stmt = conn.createStatement();
                    ResultSet rs = stmt.executeQuery(query)) {
                if (rs.next()) {
                    sourceCode.Models.Book book = new sourceCode.Models.Book(
                            rs.getString("ISBN"),
                            rs.getString("title"),
                            rs.getString("author"),
                            rs.getString("genre"),
                            rs.getString("publisher"),
                            rs.getString("publicationDate"),
                            rs.getString("language"),
                            rs.getInt("pageNumber"),
                            rs.getString("imageUrl"),
                            rs.getString("description"),
                            rs.getInt("quantity")
                    );
                    showBook.setBook(book);
                }
            }
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Book Information");
            stage.initStyle(StageStyle.UNDECORATED);
            stage.centerOnScreen();
            stage.show();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}