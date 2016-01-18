package Controllers.views.testing;

import Controllers.Main;
import Models.Database;
import Models.Questions;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kostya Nirchenko.
 *
 * @since 10.01.2016
 */
public class TestingController {

    public RadioButton firstAnswerButton;
    public RadioButton secondAnswerButton;
    public RadioButton thirdAnswerButton;
    public RadioButton fourthAnswerButton;
    private List<Questions> questionsList = new ArrayList<>();
    public Button exitButton;
    private Main main;
    private ToggleGroup buttonsGroup = new ToggleGroup();

    public void setMain(Main main) {
        this.main = main;
        firstAnswerButton.setToggleGroup(buttonsGroup);
        secondAnswerButton.setToggleGroup(buttonsGroup);
        thirdAnswerButton.setToggleGroup(buttonsGroup);
        fourthAnswerButton.setToggleGroup(buttonsGroup);
    }

    public void exitAction(ActionEvent actionEvent) {
        Database.closeConnection();
        Database.close();
        main.close();
    }

    public void setQuestionsList(List<Questions> questionsList) {
        this.questionsList = questionsList;
    }
}
