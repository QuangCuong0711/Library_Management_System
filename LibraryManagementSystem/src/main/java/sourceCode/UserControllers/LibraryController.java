package sourceCode.UserControllers;

import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import sourceCode.Models.Book;
import sourceCode.Services.SwitchScene;

public class LibraryController extends SwitchScene {
    public TextField searchBar;
    public ChoiceBox<String> choiceBox;
    public ListView<Book> recommendedBookListView;
    public ListView<Book> bookListView;

}
