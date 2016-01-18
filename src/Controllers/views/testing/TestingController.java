package Controllers.views.testing;

import Controllers.Main;
import Models.Database;
import Models.Questions;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.text.TextAlignment;

import java.sql.ResultSet;
import java.sql.SQLException;
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
    public Label questionLabel;
    public Button answerButton;
    private List<Questions> questionsList = new ArrayList<>();
    public Button exitButton;
    private Main main;
    private ToggleGroup buttonsGroup = new ToggleGroup();
    private int score = 0;
    private int rightAnswers = 0;
    private int wrongAnswers = 0;

    public void setMain(Main main) {
        this.main = main;
        firstAnswerButton.setToggleGroup(buttonsGroup);
        secondAnswerButton.setToggleGroup(buttonsGroup);
        thirdAnswerButton.setToggleGroup(buttonsGroup);
        fourthAnswerButton.setToggleGroup(buttonsGroup);
        questionLabel.setTextAlignment(TextAlignment.CENTER);
        questionLabel.setText(questionsList.get(0).getQuestion());
        firstAnswerButton.setText(questionsList.get(0).getFirstAnswer());
        secondAnswerButton.setText(questionsList.get(0).getSecondAnswer());
        thirdAnswerButton.setText(questionsList.get(0).getThirdAnswer());
        fourthAnswerButton.setText(questionsList.get(0).getFourthAnswer());
        System.out.println(questionsList.get(0).getAnswerId());
    }

    public void exitAction(ActionEvent actionEvent) {
        Database.closeConnection();
        Database.close();
        main.close();
    }

    public void setQuestionsList(List<Questions> questionsList) {
        this.questionsList = questionsList;
    }

    public void answerButtonAction(ActionEvent actionEvent) {
        /*try {
//            for(Questions q : questionsList) {
                Database.setStatement();
//                Database.setResultSet(Database.select("answers", "rightAnswer", q.getRightAnswer()));
                ResultSet answer = Database.getResultSet();
                System.out.println(questionsList.get(0).getRightAnswer());
//            }
        } catch (SQLException e) {
            e.printStackTrace();
        }*/
        System.out.println(questionsList.get(0).getRightAnswer());
    }
}
