package sourceCode;

import java.util.Objects;
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
    @Override
    public void start(Stage primaryStage) {
//        LibraryManager libraryManager = new LibraryManager();
//        String query = "Việt Nam";
//        libraryManager.addBooksToLibrary(query);
//        primaryStage.close();
        try {
            // Load the Welcome.fxml file
            Parent root = FXMLLoader.load(
                    Objects.requireNonNull(this.getClass().getResource("Menu.fxml")));
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.setTitle("Library Management System");
            primaryStage.setResizable(false);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}