package sourceCode.UserControllers;

import static sourceCode.LoginController.imagedefault;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
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
import sourceCode.Services.DatabaseConnection;
import sourceCode.Services.SwitchScene;
import sourceCode.UserControllers.BookViews.BookCellController;
import sourceCode.UserControllers.Function.AddFeedback;

public class BookcaseController extends SwitchScene implements Initializable {

    private static final String selectAllQuery
            = "SELECT * FROM library.ticket t JOIN library.book b ON t.ISBN = b.ISBN "
            + "WHERE t.returnedDate IS NULL AND t.userID = ?";
    private final ExecutorService executor = Executors.newFixedThreadPool(3);
    private static final ObservableList<Book> bookList = FXCollections.observableArrayList();
    private static final String[] searchBy = {"Tất cả", "ISBN", "Tiêu đề", "Tác giả",
            "Thể loại"};
    @FXML
    public AnchorPane bookDetail;
    @FXML
    public Pane myPane;
    @FXML
    private TextField searchBar;
    @FXML
    private ChoiceBox<String> choiceBox;
    @FXML
    private SplitPane splitPane;
    @FXML
    private ListView<Book> bookListView;
    @FXML
    private ImageView bookImage;
    @FXML
    private Label bookTitle;
    @FXML
    private Label bookISBN;
    @FXML
    private Label bookAuthor;
    @FXML
    private Label bookPublisher;
    @FXML
    private Label bookPublicationDate;
    @FXML
    private Label bookGenre;
    @FXML
    private Label bookLanguage;
    @FXML
    private Label bookPageNumber;
    @FXML
    private Label bookDescription;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        splitPane.setDividerPositions(1);
        choiceBox.setValue("Tìm kiếm theo");
        choiceBox.getItems().addAll(searchBy);
        bookListView.setItems(bookList);
        bookListView.setCellFactory(lv -> new ListCell<>() {
            @Override
            protected void updateItem(Book book, boolean empty) {
                super.updateItem(book, empty);
                if (empty || book == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    Task<Parent> loadCellTask = new Task<>() {
                        @Override
                        protected Parent call() throws Exception {
                            FXMLLoader loader = new FXMLLoader(getClass().getResource(
                                    "/sourceCode/UserFXML/BookCell.fxml"));
                            Parent cell = loader.load();
                            BookCellController controller = loader.getController();
                            controller.setBook(book);
                            return cell;
                        }
                    };
                    loadCellTask.setOnSucceeded(event -> setGraphic(loadCellTask.getValue()));
                    executor.submit(loadCellTask);
                }
            }
        });
        bookListView.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        if (newValue.getImageUrl() != null) {
                            try{
                                Image image = new Image(newValue.getImageUrl());
                                if (image.isError()) {
                                    bookImage.setImage(imagedefault); // Gán ảnh mặc định
                                } else {
                                    bookImage.setImage(image); // Gán ảnh nếu tải thành công
                                }
                            } catch (Exception e) {
                                bookImage.setImage(imagedefault);
                            }
                        } else {
                            bookImage.setImage(imagedefault);
                        }
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
                loadBooksFromDatabase(selectAllQuery);
    }

    private void loadBooksFromDatabase(String query) {
        Task<ObservableList<Book>> loadBooksTask = new Task<>() {
            @Override
            protected ObservableList<Book> call() throws Exception {
                ObservableList<Book> books = FXCollections.observableArrayList();
                try (Connection conn = DatabaseConnection.getInstance().getConnection()) {
                    assert conn != null;
                    try (PreparedStatement stmt = conn.prepareStatement(query)) {
                        stmt.setString(1, sourceCode.LoginController.currentUserId);
                        try (ResultSet rs = stmt.executeQuery()) {
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
                                books.add(book);
                            }
                        }
                    }
                }
                return books;
            }
        };
        loadBooksTask.setOnSucceeded(event -> {
            bookList.setAll(loadBooksTask.getValue());
        });
        loadBooksTask.setOnFailed(event -> {
            System.err.println("Lỗi khi tải dữ liệu từ database: " + loadBooksTask.getException());
        });
        executor.submit(loadBooksTask);
    }

    public void searchBook() {
        String query = selectAllQuery;
        if (choiceBox.getValue().equals("ISBN")) {
            query += " AND b.ISBN LIKE '%" + searchBar.getText() + "%'";
        } else if (choiceBox.getValue().equals("Tiêu đề")) {
            query += " AND b.title LIKE '%" + searchBar.getText() + "%'";
        } else if (choiceBox.getValue().equals("Tác giả")) {
            query += " AND b.author LIKE '%" + searchBar.getText() + "%'";
        } else if (choiceBox.getValue().equals("Thể loại")) {
            query += " AND b.genre LIKE '%" + searchBar.getText() + "%'";
        }
        loadBooksFromDatabase(query); // Gọi lại hàm để tải dữ liệu mới
    }

    public void returnBook() {
        sourceCode.Models.Book selectedBook = bookListView.getSelectionModel().getSelectedItem();
        if (selectedBook == null) {
            System.out.println("Vui lòng chọn sách để trả!");
            return;
        }
        String currentUserID = sourceCode.LoginController.currentUserId;
        String returnticketQuery = """
                    UPDATE library.ticket
                    SET returnedDate = CURRENT_DATE
                    WHERE userId = ? AND ISBN = ? AND returnedDate IS NULL
                    LIMIT 1;
                """;
        String  updatequantityQuery = """
                    UPDATE library.book
                    SET quantity = quantity + 1
                    WHERE ISBN = ?;
                """;

        try (Connection conn = DatabaseConnection.getInstance().getConnection()) {
            try (PreparedStatement pstmt = conn.prepareStatement(returnticketQuery)) {
                pstmt.setString(1, currentUserID);
                pstmt.setString(2, selectedBook.getISBN());
                int rowsAffected = pstmt.executeUpdate();
                if (rowsAffected > 0) {
                    try (PreparedStatement pstmt1 = conn.prepareStatement(updatequantityQuery)) {
                        pstmt1.setString(1,selectedBook.getISBN());
                        rowsAffected = pstmt1.executeUpdate();
                        if (rowsAffected > 0) {
                            System.out.println("Trả sách thành công: " + selectedBook.getTitle());
                            loadBooksFromDatabase(selectAllQuery);
                        } else {
                            System.out.println("Không thể trả sách, vui lòng thử lại.");
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                        System.out.println("Lỗi kết nối cơ sở dữ liệu.");
                    }
                } else {
                    System.out.println("Không thể trả sách, vui lòng thử lại.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Lỗi kết nối cơ sở dữ liệu.");
        }
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