package Controllers.views.testing;

import Controllers.Main;
import Models.Database;
import Models.Questions;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.*;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
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
    public Label outLabel;
    public Label scoreLabel;
    public Label rightLabel;
    public Label wrongLabel;
    public Label firstAnswerLabel;
    public Label secondAnswerLabel;
    public Label thirdAnswerLabel;
    public Label fourthAnswerLabel;
    private List<Questions> questionsList = new ArrayList<>();
    public Button exitButton;
    private Main main;
    private ToggleGroup buttonsGroup = new ToggleGroup();
    private int score = 0;
    private int rightAnswers = 0;
    private int wrongAnswers = 0;
    private int iterate = 1;

    public void setMain(Main main) {
        this.main = main;
        firstAnswerButton.setToggleGroup(buttonsGroup);
        secondAnswerButton.setToggleGroup(buttonsGroup);
        thirdAnswerButton.setToggleGroup(buttonsGroup);
        fourthAnswerButton.setToggleGroup(buttonsGroup);
        questionLabel.setTextAlignment(TextAlignment.CENTER);
        if(!questionsList.isEmpty()) {
            questionLabel.setText(questionsList.get(0).getQuestion());
            firstAnswerButton.setText(questionsList.get(0).getFirstAnswer());
            secondAnswerButton.setText(questionsList.get(0).getSecondAnswer());
            thirdAnswerButton.setText(questionsList.get(0).getThirdAnswer());
            fourthAnswerButton.setText(questionsList.get(0).getFourthAnswer());
        } else {
            firstAnswerLabel.setVisible(false);
            secondAnswerLabel.setVisible(false);
            thirdAnswerLabel.setVisible(false);
            fourthAnswerLabel.setVisible(false);
            questionLabel.setVisible(false);
            firstAnswerButton.setVisible(false);
            secondAnswerButton.setVisible(false);
            thirdAnswerButton.setVisible(false);
            fourthAnswerButton.setVisible(false);
            answerButton.setVisible(false);
        }
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
        try {
            RadioButton answer = (RadioButton) buttonsGroup.getSelectedToggle();
            Database.setStatement();
            Database.setResultSet(Database.select("answers", "rightAnswer", "'" + answer.getText() + "'"));
            ResultSet answers = Database.getResultSet();
            if(answers.next()) {
                this.rightAnswers++;
                this.score++;
            } else {
                this.wrongAnswers++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if(iterate >= questionsList.size()) {
            firstAnswerLabel.setVisible(false);
            secondAnswerLabel.setVisible(false);
            thirdAnswerLabel.setVisible(false);
            fourthAnswerLabel.setVisible(false);
            questionLabel.setVisible(false);
            firstAnswerButton.setVisible(false);
            secondAnswerButton.setVisible(false);
            thirdAnswerButton.setVisible(false);
            fourthAnswerButton.setVisible(false);
            answerButton.setVisible(false);
            outLabel.setText("Время окончания тестирования: " + new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").format(System.currentTimeMillis()));
            try {
                Alert confirm = Database.showSimpleDialog(Alert.AlertType.INFORMATION,
                        "УРА!", "Вы ответили на все вопросы", "Дано правильных ответов: " + rightAnswers + "\n" +
                                "Дано неправильных ответов: " + wrongAnswers + "\n" +
                                "Получено баллов: " + score + "\n" +
                                "Имя компьютера: " + InetAddress.getLocalHost().getHostName() + "\n" + "IP: " + InetAddress.getLocalHost().getHostAddress(), true);
                Stage confirmStage = (Stage) confirm.getDialogPane().getScene().getWindow();
                confirmStage.getIcons().add(new javafx.scene.image.Image(Main.class.getResourceAsStream("views/images/confirm.png")));
                confirmStage.showAndWait();
                ImageIO.write(new Robot().createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize())), "png", new File(FileSystemView.getFileSystemView().getHomeDirectory(), new SimpleDateFormat("dd-MM-yyyy-HH-mm-ss").format(System.currentTimeMillis()) + ".png"));
            } catch (UnknownHostException e) {
                Database.throwingException(e);
            } catch (AWTException e) {

            } catch (IOException e) {

            }
        } else {
            questionLabel.setText(questionsList.get(iterate).getQuestion());
            firstAnswerButton.setText(questionsList.get(iterate).getFirstAnswer());
            secondAnswerButton.setText(questionsList.get(iterate).getSecondAnswer());
            thirdAnswerButton.setText(questionsList.get(iterate).getThirdAnswer());
            fourthAnswerButton.setText(questionsList.get(iterate).getFourthAnswer());
        }
        iterate++;
    }
}
