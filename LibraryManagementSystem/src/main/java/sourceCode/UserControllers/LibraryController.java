package sourceCode.UserControllers;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.ScaleTransition;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import javafx.util.Duration;
import sourceCode.Models.Book;
import sourceCode.Services.DatabaseOperation;
import sourceCode.Services.SwitchScene;
import sourceCode.UserControllers.BookViews.BookGridController;

public class LibraryController extends SwitchScene implements Initializable {

    private static final ObservableList<Book> databaseBookList = FXCollections.observableArrayList();
    private static final ObservableList<Book> recommendBookList = FXCollections.observableArrayList();
    private static final String[] searchBy = {"Tất cả", "ISBN", "Tiêu đề", "Tác giả",
            "Thể loại"};
    private final ExecutorService executor = Executors.newFixedThreadPool(3);
    public SplitPane mySplitPane;
    public ScrollPane myScrollPane;
    public TilePane myTilePane;
    public TextField searchBar;
    public ChoiceBox<String> choiceBox;
    public ListView<Book> recommendBookListView;
    @FXML
    private Button showRecommendBookButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        searchBar.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode().toString().equals("ENTER")) {
                searchBook();
            }
        });
        mySplitPane.setDividerPositions(0.465);
        myTilePane.setVisible(false);
        myScrollPane.setVisible(false);
        choiceBox.getItems().addAll(searchBy);
        choiceBox.setValue("Tất cả");
        initRecommendBookList();
    }

    public void initRecommendBookList() {
        String query = "SELECT * FROM library.Book LIMIT 10";
        recommendBookList.clear();
        DatabaseOperation.loadBookfromDatabase(query, recommendBookList);
        recommendBookListView.setItems(recommendBookList);
        recommendBookListView.setCellFactory(lv -> new ListCell<>() {
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
                                    "/sourceCode/UserFXML/BookGrid.fxml"));
                            Parent cell = loader.load();
                            BookGridController controller = loader.getController();
                            controller.setBook(book);
                            return cell;
                        }
                    };
                    loadCellTask.setOnSucceeded(event -> {
                        Parent graphic = loadCellTask.getValue();
                        FadeTransition fadeIn = new FadeTransition(Duration.millis(500), graphic);
                        fadeIn.setFromValue(0);
                        fadeIn.setToValue(1);
                        fadeIn.play();

                        setGraphic(graphic);
                    });
                    executor.submit(loadCellTask);
                }
            }
        });

        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(1), event -> {
                    int currentIndex = recommendBookListView.getFocusModel().getFocusedIndex();
                    int nextIndex = (currentIndex + 1) % recommendBookList.size();
                    ListCell<Book> currentCell = (ListCell<Book>) recommendBookListView.lookup(
                            ".list-cell:nth-child(" + (currentIndex + 1) + ")");
                    ListCell<Book> nextCell = (ListCell<Book>) recommendBookListView.lookup(
                            ".list-cell:nth-child(" + (nextIndex + 1) + ")");
                    if (currentCell != null && nextCell != null) {
                        TranslateTransition transition = new TranslateTransition(
                                Duration.millis(500), nextCell);
                        transition.setFromX(-50);
                        transition.setToX(0);
                        transition.play();
                    }
                    recommendBookListView.scrollTo(nextIndex);
                    recommendBookListView.getFocusModel().focus(nextIndex);
                })
        );
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    public void populateTilePane() {
        myTilePane.getChildren().clear();
        int batchSize = 15;

        for (int i = 0; i < databaseBookList.size(); i += batchSize) {
            int start = i;
            int end = Math.min(i + batchSize, databaseBookList.size());

            Task<Void> task = new Task<>() {
                @Override
                protected Void call() throws Exception {
                    for (int j = start; j < end; j++) {
                        Book book = databaseBookList.get(j);
                        FXMLLoader loader = new FXMLLoader(
                                getClass().getResource("/sourceCode/UserFXML/BookGrid.fxml"));
                        Pane bookPane = loader.load();
                        BookGridController controller = loader.getController();
                        controller.setBook(book);
                        Platform.runLater(() -> {
                            myTilePane.getChildren().add(bookPane);
                            FadeTransition fadeTransition = new FadeTransition(Duration.millis(500),
                                    bookPane);
                            fadeTransition.setFromValue(0);
                            fadeTransition.setToValue(1);
                            fadeTransition.play();
                        });
                        Thread.sleep(50);
                    }
                    return null;
                }
            };
            new Thread(task).start();
        }
    }


    public void selectBook(String query) {
        DatabaseOperation.loadBookfromDatabase(query, databaseBookList);
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
        myTilePane.setVisible(true);
        myScrollPane.setVisible(true);
        mySplitPane.setDividerPositions(0.9);
        recommendBookListView.setVisible(false);
        showRecommendBookButton.setText("Hiện gợi ý sách");
    }

    public void showRecommendedBook() {
        if (recommendBookListView.isVisible()) {
            recommendBookListView.setVisible(false);
            mySplitPane.setDividerPositions(0.9);
            showRecommendBookButton.setText("Hiện gợi ý sách");
        } else {
            recommendBookListView.setVisible(true);
            mySplitPane.setDividerPositions(0.465);
            showRecommendBookButton.setText("Ẩn gợi ý sách");
        }
    }
}