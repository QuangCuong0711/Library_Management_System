package sourceCode.AdminControllers.Function;

import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import sourceCode.Models.Book;
import java.util.HashMap;
import java.util.Map;

public class ShowBook {

    @FXML
    private ImageView image;
    @FXML
    private Label title;
    @FXML
    private Label ISBN;
    @FXML
    private Label author;
    @FXML
    private Label publisher;
    @FXML
    private Label date;
    @FXML
    private Label genre;
    @FXML
    private Label language;
    @FXML
    private Label pageNumber;
    @FXML
    private TextArea description;

    private static final Map<String, Image> imageCache = new HashMap<>();

    public void setBook(Book book) {
        title.setText(book.getTitle());
        ISBN.setText(book.getISBN());
        author.setText(book.getAuthor());
        genre.setText(book.getGenre());
        publisher.setText(book.getPublisher());
        date.setText(book.getPublicationDate());
        language.setText(book.getLanguage());
        pageNumber.setText(String.valueOf(book.getPageNumber()));
        description.setText(book.getDescription());
        if (book.getImageUrl() != null && !book.getImageUrl().isEmpty()) {
            loadImageWithCache(book.getImageUrl());
        } else {
            image.setImage(null);
        }
    }

    private void loadImageWithCache(String imageUrl) {
        if (imageCache.containsKey(imageUrl)) {
            image.setImage(imageCache.get(imageUrl));
        } else {
            Task<Image> loadImageTask = new Task<>() {
                @Override
                protected Image call() {
                    try {
                        Image img = new Image(imageUrl, true);
                        if (img.isError()) {
                            System.out.println("Lỗi tải ảnh : " + imageUrl);
                            return null;
                        }
                        return img;
                    } catch (Exception e) {
                        System.out.println("Lỗi tải ảnh từ URL: " + imageUrl);
                        e.printStackTrace();
                        return null;
                    }
                }
            };

            loadImageTask.setOnSucceeded(event -> {
                Image loadedImage = loadImageTask.getValue();
                if (loadedImage != null) {
                    image.setImage(loadedImage);
                    imageCache.put(imageUrl, loadedImage);
                    System.out.println("Image loaded and cached: " + imageUrl);
                } else {
                    System.out.println(" Sử dụng ảnh mặc định ");
                    image.setImage(null);
                }
            });

            loadImageTask.setOnFailed(event -> {
                System.out.println("Image loading task failed for URL: " + imageUrl);
                image.setImage(null);
            });

            Thread loadImageThread = new Thread(loadImageTask);
            loadImageThread.setDaemon(true);
            loadImageThread.start();
        }
    }

    public void confirmButtonOnAction(ActionEvent event) {
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.close();
    }
}
