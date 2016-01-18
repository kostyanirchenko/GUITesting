package Controllers.views.edit;

import Models.Database;
import Models.Questions;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Created by Kinndzadza on 15.01.2016.
 */
public class EditController {
    public TextField questionField;
    public TextField firstAnswerField;
    public TextField secondAnswerField;
    public TextField thirdAnswerField;
    public TextField fourthAnswerField;
    public TextField rightAnswerField;
    public Button nextButton;
    public Button cancelButton;
    private String editId;
    private boolean editClicked = false;
    private Questions questions;
    private Stage editStage;

    public void setEditStage(Stage editStage) {
        this.editStage = editStage;
    }

    public void setQuestions(Questions questions) {
        this.questions = questions;
        questionField.setText(questions.getQuestion());
        firstAnswerField.setText(questions.getFirstAnswer());
        secondAnswerField.setText(questions.getSecondAnswer());
        thirdAnswerField.setText(questions.getThirdAnswer());
        fourthAnswerField.setText(questions.getFourthAnswer());
        rightAnswerField.setText(questions.getRightAnswer());
    }

    public void setEditId(String editId) {
        this.editId = editId;
    }

    public boolean isEditClicked() {
        return editClicked;
    }

    public void nextButtonAction(ActionEvent actionEvent) {
        try {
            String questionQuery = Database.setPreparedStatement("UPDATE",
                    "questions",
                    "questionText = " + "?",
                    "questionId", questions.getId());
            PreparedStatement questionStatement = Database.getPreparedStatement(questionQuery, false);
            questionStatement.setString(1, questionField.getText());
            questionStatement.executeUpdate();
            String answerQuery = Database.setPreparedStatement("UPDATE",
                    "answers",
                    "firstAnswer = " + "? " +
                    ", secondAnswer = " + "? " +
                    ", thirdAnswer = " + "? " +
                    ", fourthAnswer = " + "? " +
                    ", rightAnswer = " + "?",
                    "answersId", questions.getAnswerId());
            PreparedStatement answersStatement = Database.getPreparedStatement(answerQuery, false);
            answersStatement.setString(1, firstAnswerField.getText());
            answersStatement.setString(2, secondAnswerField.getText());
            answersStatement.setString(3, thirdAnswerField.getText());
            answersStatement.setString(4, fourthAnswerField.getText());
            answersStatement.setString(5, rightAnswerField.getText());
            answersStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        editClicked = true;
        editStage.close();
    }

    public void cancelButtonAction(ActionEvent actionEvent) {
        editStage.close();
    }
}
