package sourceCode.UserControllers.BookViews;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import sourceCode.Models.Book;

import static sourceCode.LoginController.imagedefault;

public class BookCellController {

    @FXML
    private ImageView bookImage;
    @FXML
    private Label titleLabel;
    @FXML
    private Label authorLabel;
    @FXML
    private Label isbnLabel;
    @FXML
    private Label descriptionLabel;

    public void setBook(Book book) {
        titleLabel.setText(book.getTitle());
        authorLabel.setText("Author: " + book.getAuthor());
        isbnLabel.setText("ISBN: " + book.getISBN());
        descriptionLabel.setText("Description:" + '\n' + book.getDescription());
        if (book.getImageUrl() != null) {
            try{
                Image image = new Image(book.getImageUrl());
                if (image.isError()) {
                    bookImage.setImage(imagedefault); // Gán ảnh mặc định
                } else {
                    bookImage.setImage(image); // Gán ảnh nếu tải thành công
                }
            } catch (Exception e) {
                bookImage.setImage(imagedefault);

            }
        }
    }
}
