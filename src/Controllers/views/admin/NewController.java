package Controllers.views.admin;

import Models.Questions;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * Created by Kinndzadza on 11.01.2016.
 */
public class NewController {
    public TextField questionField;
    public TextField firstAnswerField;
    public TextField secondAnswerField;
    public TextField thirdAnswerField;
    public TextField fourthAnswerField;
    public Button okButton;
    public Button cancelButton;
    private Stage stage;
    private Questions questions;

    public void okAction(ActionEvent actionEvent) {
    }

    public void cancelAction(ActionEvent actionEvent) {

    }

    public void setNewStage(Stage stage) {
        this.stage = stage;
    }

    public void setQuestions(Questions questions) {
        this.questions = questions;
    }
}
