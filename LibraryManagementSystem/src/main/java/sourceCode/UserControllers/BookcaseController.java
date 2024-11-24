package sourceCode.UserControllers;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import sourceCode.Models.Book;
import sourceCode.Services.Service;
import sourceCode.Services.SwitchScene;

public class BookcaseController extends SwitchScene implements Initializable {

    private static final String selectAllQuery
            = "SELECT * FROM library.ticket t JOIN library.book b ON t.ISBN = b.ISBN "
            + "WHERE t.returnedDate IS NULL AND t.userID = 'U001'";
    private static final ObservableList<Book> bookList = FXCollections.observableArrayList();
    private static final String[] searchBy = {"Tất cả", "ISBN", "Tiêu đề", "Tác giả",
            "Thể loại"};
    public TextField searchBar;
    public ChoiceBox<String> choiceBox;
    public SplitPane splitPane;
    public ListView<Book> bookListView;
    public AnchorPane bookDetail;
    public Pane myPane;
    public ImageView bookImage;
    public Label bookTitle;
    public Label bookISBN;
    public Label bookAuthor;
    public Label bookPublisher;
    public Label bookPublicationDate;
    public Label bookGenre;
    public Label bookLanguage;
    public Label bookPageNumber;
    public Label bookDescription;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        splitPane.setDividerPositions(1);
        choiceBox.setValue("Tìm kiếm theo");
        choiceBox.getItems().addAll(searchBy);
        bookListView.setItems(bookList);
        selectBook(selectAllQuery);
        bookListView.setCellFactory(lv -> new ListCell<Book>() {
            @Override
            protected void updateItem(Book book, boolean empty) {
                super.updateItem(book, empty);
                if (empty || book == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource(
                                "/sourceCode/UserFXML/BookCell.fxml"));
                        setGraphic(loader.load());
                        BookCellController controller = loader.getController();
                        controller.setBook(book);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        bookListView.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        bookImage.setImage(new Image(newValue.getImageUrl()));
                        bookTitle.setText(newValue.getTitle());
                        bookISBN.setText("ISBN: " + newValue.getISBN());
                        bookAuthor.setText("Tác giả: " + newValue.getAuthor());
                        bookPublisher.setText("Nhà xuất bản: " + newValue.getPublisher());
                        bookPublicationDate.setText(
                                "Ngày xuất bản: " + newValue.getPublicationDate());
                        bookGenre.setText("Thể loại: " + newValue.getGenre());
                        bookLanguage.setText("Ngôn ngữ: " + newValue.getLanguage());
                        bookPageNumber.setText("Số trang: " + newValue.getPageNumber());
                        bookDescription.setText("Mô tả" + '\n' + newValue.getDescription());
                        splitPane.setDividerPositions(0.6);
                    }
                });
    }

    public void selectBook(String query) {
        bookList.clear();
        try (Connection conn = Service.getConnection()) {
            assert conn != null;
            try (Statement stmt = conn.createStatement();
                    ResultSet rs = stmt.executeQuery(query)) {
                while (rs.next()) {
                    Book book = new Book(
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
                    bookList.add(book);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void searchBook() {
        if (choiceBox.getValue().equals("Tất cả")) {
            selectBook(selectAllQuery);
        } else if (choiceBox.getValue().equals("ISBN")) {
            selectBook(selectAllQuery + " AND b.ISBN LIKE '%" + searchBar.getText() + "%'");
        } else if (choiceBox.getValue().equals("Tiêu đề")) {
            selectBook(selectAllQuery + " AND b.title LIKE '%" + searchBar.getText() + "%'");
        } else if (choiceBox.getValue().equals("Tác giả")) {
            selectBook(selectAllQuery + " AND b.author LIKE '%" + searchBar.getText() + "%'");
        } else if (choiceBox.getValue().equals("Thể loại")) {
            selectBook(selectAllQuery + " AND b.genre LIKE '%" + searchBar.getText() + "%'");
        }
    }

    public void returnBook() {

    }

    public void sendFeedback() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(
                    "/sourceCode/UserFXML/AddFeedback.fxml"));
            Parent root = loader.load();
            AddFeedback controller = loader.getController();
            controller.setISBN(bookISBN.getText());
            System.out.println(controller.getISBN());
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Add Feedback");
            stage.initStyle(StageStyle.UNDECORATED);
            stage.centerOnScreen();
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
