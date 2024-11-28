package sourceCode.AdminControllers.Function;

import java.util.HashMap;
import java.util.Map;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import sourceCode.LoginController;
import sourceCode.Models.Book;

import static sourceCode.LoginController.imageCache;
import static sourceCode.LoginController.imagedefault;

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
            // Nếu ảnh đã có trong cache, sử dụng ảnh đó
            image.setImage(imageCache.get(imageUrl));
        } else {
            try {
                // Tải ảnh trực tiếp trong luồng chính
                Image img = new Image(imageUrl);
                if (!img.isError()) {
                    // Nếu tải thành công, lưu vào cache và hiển thị ảnh
                    imageCache.put(imageUrl, img);
                    image.setImage(img);
                    System.out.println("Image loaded and cached: " + imageUrl);
                } else {
                    // Nếu ảnh có lỗi, sử dụng ảnh mặc định
                    System.out.println("Image error, using default for URL: " + imageUrl);
                    image.setImage(imagedefault);
                }
            } catch (Exception e) {
                // Xử lý ngoại lệ và sử dụng ảnh mặc định
                System.out.println("Exception while loading image: " + imageUrl);
                e.printStackTrace();
                image.setImage(imagedefault);
            }
        }
    }

    public void confirmButtonOnAction(ActionEvent event) {
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.close();
    }
}
