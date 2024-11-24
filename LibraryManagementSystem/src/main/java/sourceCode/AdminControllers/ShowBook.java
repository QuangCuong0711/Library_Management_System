package sourceCode.AdminControllers;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import sourceCode.Models.Book;

public class ShowBook {

    public ImageView image;
    public Label title;
    public Label ISBN;
    public Label author;
    public Label publisher;
    public Label date;
    public Label genre;
    public Label language;
    public Label pageNumber;
    public Label description;

    public void setBook(Book book) {
        title.setText(book.getTitle());
        ISBN.setText(book.getISBN());
        author.setText(book.getAuthor());
        genre.setText(book.getGenre());
        publisher.setText(book.getPublisher());
        date.setText(book.getPublicationDate());
        language.setText(book.getLanguage());
        pageNumber.setText(String.valueOf(book.getPageNumber()));
        image.setImage(new Image(book.getImageUrl()));
        description.setText(book.getDescription());
    }

    public void confirmButtonOnAction(ActionEvent event) {
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.close();
    }
}
