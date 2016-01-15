package Controllers.views.newQuestion;

import Models.Database;
import Models.Questions;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
    public TextField rightAnswer;
    private Stage stage;
    private Questions questions;

    private boolean okClicked = false;

    public boolean isOkClicked() {
        return okClicked;
    }

    public void setNewStage(Stage stage) {
        this.stage = stage;
    }

    public void setQuestions(Questions questions) {
        this.questions = questions;
    }

    public void okAction(ActionEvent actionEvent) {
        try {
            String query = Database.setPreparedStatement("INSERT",
                    "questions",
                    "?, ?",
                    null,
                    null);
            PreparedStatement statement = Database.getPreparedStatement(query, true);
            statement.setInt(1, 0);
            statement.setString(2, questionField.getText());
            statement.executeUpdate();
            int generatedKey = -1;
            ResultSet resultSet = statement.getGeneratedKeys();
            if(resultSet.next()) {
                generatedKey = resultSet.getInt(1);
            }
            resultSet.close();
            String [] answers = {firstAnswerField.getText(),
                    secondAnswerField.getText(),
                    thirdAnswerField.getText(),
                    fourthAnswerField.getText()};
            int [] answersId = new int[4];
            for(int i = 0; i < 5; i++) {
                String answerQuery = Database.setPreparedStatement("INSERT",
                        "answers",
                        "?, ?, ?",
                        null,
                        null);
                PreparedStatement answersStatement = Database.getPreparedStatement(answerQuery, true);
                answersStatement.setInt(1, 0);
                answersStatement.setString(2, answers[i]);
                answersStatement.setInt(3, Integer.parseInt(rightAnswer.getText()));
                answersStatement.executeUpdate();
                ResultSet answerRS = answersStatement.getGeneratedKeys();
                if(answerRS.next()) {
                    answersId[i] = answerRS.getInt(1);
                }
                answerRS.close();
                String questionsAnswerQuery = Database.setPreparedStatement("INSERT",
                        "questions_answer",
                        "?, ?",
                        null,
                        null);
                PreparedStatement qaStatement = Database.getPreparedStatement(questionsAnswerQuery, false);
                qaStatement.setInt(1, generatedKey);
                qaStatement.setInt(2, answersId[i]);
                qaStatement.executeUpdate();
            }
        } catch (SQLException e) {
            Database.throwingException(e);
        }
        okClicked = true;
        stage.close();
    }

    public void cancelAction(ActionEvent actionEvent) {
        stage.close();
    }
}
