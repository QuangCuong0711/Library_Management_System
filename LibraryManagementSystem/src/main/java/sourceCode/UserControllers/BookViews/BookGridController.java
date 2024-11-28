package sourceCode.UserControllers.BookViews;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import sourceCode.Models.Book;

import static sourceCode.LoginController.imageCache;
import static sourceCode.LoginController.imagedefault;

public class BookGridController {

    @FXML
    private ImageView bookCover;
    @FXML
    private Text bookTitle;
    private Book book;

    public void setBook(Book book) {
        bookTitle.setText(book.getTitle());
        if (book.getImageUrl() != null) {
//            try{
//                Image image = new Image(book.getImageUrl());
//                if (image.isError()) {
//                    bookCover.setImage(imagedefault); // Gán ảnh mặc định
//                } else {
//                    bookCover.setImage(image); // Gán ảnh nếu tải thành công
//                }
//            } catch (Exception e) {
//                bookCover.setImage(imagedefault);
//
//            }
            if (imageCache.containsKey(book.getImageUrl())) {
                // Nếu ảnh đã có trong cache, sử dụng ảnh đó
                bookCover.setImage(imageCache.get(book.getImageUrl()));
            } else {
                try {
                    // Tải ảnh trực tiếp trong luồng chính
                    Image img = new Image(book.getImageUrl());
                    if (!img.isError()) {
                        // Nếu tải thành công, lưu vào cache và hiển thị ảnh
                        imageCache.put(book.getImageUrl(), img);
                        bookCover.setImage(img);
                        System.out.println("Image loaded and cached: " + book.getImageUrl());
                    } else {
                        // Nếu ảnh có lỗi, sử dụng ảnh mặc định
                        System.out.println("Image error, using default for URL: " + book.getImageUrl());
                        bookCover.setImage(imagedefault);
                    }
                } catch (Exception e) {
                    // Xử lý ngoại lệ và sử dụng ảnh mặc định
                    System.out.println("Exception while loading image: " + book.getImageUrl());
                    e.printStackTrace();
                    bookCover.setImage(imagedefault);
                }
            }
        }
        this.book = book;
    }

    public void showBook() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(
                    "/sourceCode/UserFXML/BookDetail.fxml"));
            Parent root = loader.load();
            BookDetailController bookDetailController = loader.getController();
            bookDetailController.setBook(book);
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Book Detail");
            stage.initStyle(StageStyle.UNDECORATED);
            stage.centerOnScreen();
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
