package sourceCode.UserControllers;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import sourceCode.Models.Book;
import sourceCode.Services.DatabaseConnection;
import sourceCode.Services.SwitchScene;
import sourceCode.UserControllers.BookViews.BookGridController;

public class LibraryController extends SwitchScene implements Initializable {

    private static final ObservableList<Book> bookList = FXCollections.observableArrayList();
    private static final String[] searchBy = {"Tất cả", "ISBN", "Tiêu đề", "Tác giả",
            "Thể loại"};
    public SplitPane mySplitPane;
    public ScrollPane myScrollPane;
    public TilePane myTilePane;
    public TextField searchBar;
    public ChoiceBox<String> choiceBox;
    public ListView<Book> recommendedBookListView;
    private boolean isSearching;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        isSearching = false;
        mySplitPane.setDividerPositions(0.465);
        myTilePane.setVisible(false);
        myScrollPane.setVisible(false);
        choiceBox.getItems().addAll(searchBy);
        choiceBox.setValue("Tất cả");
    }

    private void populateTilePane() {
        myTilePane.getChildren().clear();
        for (Book book : bookList) {
            try {
                FXMLLoader loader = new FXMLLoader(
                        getClass().getResource("/sourceCode/UserFXML/BookGrid.fxml"));
                Pane bookPane = loader.load();
                BookGridController controller = loader.getController();
                controller.setBook(book);
                myTilePane.getChildren().add(bookPane);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void selectBook(String query) {
        bookList.clear();
        try (Connection conn = DatabaseConnection.getInstance().getConnection()) {
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
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
                        bookList.add(book);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void searchBook() {
        if (choiceBox.getValue().equals("Tất cả")) {
            selectBook(
                    "SELECT * FROM library.Book WHERE ISBN LIKE '%" + searchBar.getText()
                            + "%' OR title LIKE '%" + searchBar.getText() + "%' OR author LIKE '%"
                            + searchBar.getText() + "%' OR genre LIKE '%" + searchBar.getText()
                            + "%'");
        } else if (choiceBox.getValue().equals("ISBN")) {
            selectBook(
                    "SELECT * FROM library.Book WHERE ISBN LIKE '%" + searchBar.getText() + "%'");
        } else if (choiceBox.getValue().equals("Tiêu đề")) {
            selectBook(
                    "SELECT * FROM library.Book WHERE title LIKE '%" + searchBar.getText() + "%'");
        } else if (choiceBox.getValue().equals("Tác giả")) {
            selectBook(
                    "SELECT * FROM library.Book WHERE author LIKE '%" + searchBar.getText() + "%'");
        } else if (choiceBox.getValue().equals("Thể loại")) {
            selectBook(
                    "SELECT * FROM library.Book WHERE genre LIKE '%" + searchBar.getText() + "%'");
        }
        populateTilePane();
        if (isSearching) {
            myTilePane.setVisible(false);
            myScrollPane.setVisible(false);
            mySplitPane.setDividerPositions(0.465);
            isSearching = false;
        } else {
            myTilePane.setVisible(true);
            myScrollPane.setVisible(true);
            mySplitPane.setDividerPositions(0.9);
            isSearching = true;
        }
    }

    public void showRecommendedBook() {
        myTilePane.setVisible(false);
        mySplitPane.setDividerPositions(0.465);
        isSearching = false;
    }
}