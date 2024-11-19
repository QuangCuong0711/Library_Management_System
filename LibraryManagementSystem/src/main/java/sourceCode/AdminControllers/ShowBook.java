package sourceCode.AdminControllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import sourceCode.Models.Book;

public class ShowBook {

    @FXML
    private Label title;
    @FXML
    private Label ISBN;
    @FXML
    private Label author;
    @FXML
    private Label genre;
    @FXML
    private Label publisher;
    @FXML
    private Label publicationDate;
    @FXML
    private Label language;
    @FXML
    private Label pageNumber;
    @FXML
    private Label imageUrl;
    @FXML
    private Label description;
    @FXML
    private Label quantity;

    public void setBook(Book book) {
        title.setText(book.getTitle());
        ISBN.setText(book.getISBN());
        author.setText(book.getAuthor());
        genre.setText(book.getGenre());
        publisher.setText(book.getPublisher());
        publicationDate.setText(book.getPublicationDate());
        language.setText(book.getLanguage());
        pageNumber.setText(String.valueOf(book.getPageNumber()));
        imageUrl.setText(book.getImageUrl());
        description.setText(book.getDescription());
        quantity.setText(String.valueOf(book.getQuantity()));
    }

    public void confirmButtonOnAction(ActionEvent event) {
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.close();
    }
}
