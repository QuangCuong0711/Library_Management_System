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

public class BookGridController {

    @FXML
    private ImageView bookCover;
    @FXML
    private Text bookTitle;
    private Book book;

    public void setBook(Book book) {
        bookTitle.setText(book.getTitle());
        if (book.getImageUrl() != null && !book.getImageUrl().isEmpty()) {
            try {
                Image img = new Image(book.getImageUrl(), true);
                if (img.isError()) {
                    System.out.println("Image URL is invalid or image cannot be loaded: "
                            + book.getImageUrl());
                    bookCover.setImage(null);
                } else {
                    bookCover.setImage(img);
                }
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid image URL: " + book.getImageUrl());
                bookCover.setImage(null);
            }
        } else {
            bookCover.setImage(null);
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
