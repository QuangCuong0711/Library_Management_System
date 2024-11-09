package sourceCode;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sourceCode.Models.LibraryManager;

public class Start extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    // Chạy chương trình
    @Override
    public void start(Stage primaryStage) {
//        LibraryManager libraryManager = new LibraryManager();
//        String query = "Việt Nam";
//        libraryManager.addBooksToLibrary(query);
//        primaryStage.close();
        try {
            Parent root = FXMLLoader.load(this.getClass().getResource("Welcome.fxml"));
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}