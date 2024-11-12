package sourceCode;

import java.sql.SQLException;
import java.util.Objects;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import sourceCode.Models.Management;

public class Start extends Application {


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws SQLException {
        Management management = new Management();
        management.searchBook("cpp");
        try {
            Parent root = FXMLLoader.load(
                    Objects.requireNonNull(this.getClass().getResource("Welcome.fxml")));
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