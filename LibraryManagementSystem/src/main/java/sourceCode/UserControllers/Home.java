package sourceCode.UserControllers;

import javafx.scene.layout.Pane;
import sourceCode.Services.SwitchScene;

public class Home extends SwitchScene {


    public Pane recommendBook;

    public void turnOff() {
        recommendBook.setVisible(false);
    }

    public void turnOn() {
        recommendBook.setVisible(true);
    }
}
