package sourceCode.UserControllers.BookViews;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import sourceCode.Models.Book;

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
        if (book.getImageUrl() != null && !book.getImageUrl().isEmpty()) {
            try {
                Image img = new Image(book.getImageUrl(), true);
                if (img.isError()) {
                    System.out.println("Image URL is invalid or image cannot be loaded: "
                            + book.getImageUrl());
                    bookImage.setImage(null);
                } else {
                    bookImage.setImage(img);
                }
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid image URL: " + book.getImageUrl());
                bookImage.setImage(null);
            }
        } else {
            bookImage.setImage(null);
        }
    }
}
