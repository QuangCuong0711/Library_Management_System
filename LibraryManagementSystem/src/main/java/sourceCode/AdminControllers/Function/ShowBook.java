package sourceCode.AdminControllers.Function;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import sourceCode.Models.Book;

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
        if(book.getImageUrl() != null && !book.getImageUrl().isEmpty()) {
            try {
                Image img = new Image(book.getImageUrl(), true);
                if (img.isError()) {
                    System.out.println("Image URL is invalid or image cannot be loaded: " + book.getImageUrl());
                    image.setImage(null);
                } else {
                    image.setImage(img);
                }
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid image URL: " + book.getImageUrl());
                image.setImage(null);
            }
        } else {
            image.setImage(null);
        }
        description.setText(book.getDescription());
    }

    public void confirmButtonOnAction(ActionEvent event) {
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.close();
    }
}
