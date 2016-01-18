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
                String rightAnswerString = "";
                int rightAnswerId = Integer.parseInt(rightAnswer.getText());
                switch (rightAnswerId) {
                    case 1: rightAnswerString = firstAnswerField.getText();
                        break;
                    case 2: rightAnswerString = secondAnswerField.getText();
                        break;
                    case 3: rightAnswerString = thirdAnswerField.getText();
                        break;
                    case 4: rightAnswerString = fourthAnswerField.getText();
                        break;
                }
                String answersQuery = Database.setPreparedStatement("INSERT",
                        "answers",
                        "?, ?, ?, ?, ?, ?, ?",
                        null,
                        null);
                PreparedStatement answersStatement = Database.getPreparedStatement(answersQuery, false);
                answersStatement.setInt(1, 0);
                answersStatement.setString(2, firstAnswerField.getText());
                answersStatement.setString(3, secondAnswerField.getText());
                answersStatement.setString(4, thirdAnswerField.getText());
                answersStatement.setString(5, fourthAnswerField.getText());
                answersStatement.setString(6, rightAnswerString);
                answersStatement.setInt(7, generatedKey);
                answersStatement.executeUpdate();
                questions.setId(Integer.toString(generatedKey));
                questions.setFirstAnswer(firstAnswerField.getText());
                questions.setSecondAnswer(secondAnswerField.getText());
                questions.setThirdAnswer(thirdAnswerField.getText());
                questions.setFourthAnswer(fourthAnswerField.getText());
                questions.setRightAnswer(rightAnswerString);
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