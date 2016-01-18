package Controllers.views.testing;

import Controllers.Main;
import Models.Database;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;

/**
 * Created by Kostya Nirchenko.
 *
 * @since 10.01.2016
 */
public class TestingController {

    public Button exitButton;
    private Main main;

    public void setMain(Main main) {
        this.main = main;
    }

    public void exitAction(ActionEvent actionEvent) {
        Database.closeConnection();
        Database.close();
        main.close();
    }
}
