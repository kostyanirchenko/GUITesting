package Controllers.views.menu;

import Controllers.Main;

import Models.Database;
import Models.Questions;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Created by Kostya Nirchenko.
 *
 * @since 10.01.2016
 */
public class MenuController {

    public MenuItem adminLoginButton;
    public Menu edit;
    public MenuBar menu;
    public MenuItem editQuestionButton;
    public MenuItem addQuestionButton;
    public MenuItem deleteQuestionButton;
    public MenuItem adminExitButton;
    private Main main;
    private boolean loginflag = false;

    public void setMain(Main main) {
        this.main = main;
    }

    public void exitAction(ActionEvent actionEvent) {
        Database.closeConnection();
        Database.close();
        main.close();
    }

    public void adminLoginAction(ActionEvent actionEvent) {
        Dialog<Pair<String, String>> dialog = new Dialog<>();
        dialog.setTitle("Вход администратора");
        dialog.setHeaderText("Пожалуйста, выполните вход администратора");
        dialog.setGraphic(new ImageView(this.getClass().getResource("login.png").toString()));
        ButtonType loginButtonType = new ButtonType("Вход", ButtonBar.ButtonData.OK_DONE);
        ButtonType cancelButtonType = new ButtonType("Назад", ButtonBar.ButtonData.CANCEL_CLOSE);
        dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, cancelButtonType);
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));
        TextField username = new TextField();
        username.setPromptText("Введите ваш логин");
        PasswordField password = new PasswordField();
        password.setPromptText("Введите ваш пароль");
        grid.add(new Label("Логин :"), 0, 0);
        grid.add(username, 1, 0);
        grid.add(new Label("Пароль :"), 0, 1);
        grid.add(password, 1, 1);
        Node loginButton = dialog.getDialogPane().lookupButton(loginButtonType);
        loginButton.setDisable(true);
        username.textProperty().addListener((observable, oldValue, newValue) -> {
            loginButton.setDisable(newValue.trim().isEmpty());
        });
        dialog.getDialogPane().setContent(grid);
        Platform.runLater(() -> username.requestFocus());
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == loginButtonType) {
                return new Pair<>(username.getText(), password.getText());
            }
            return null;
        });
        Optional<Pair<String, String>> result = dialog.showAndWait();
        result.ifPresent(usernamePassword -> {
            try {
                Database.setStatement();
                Database.setResultSet(Database.select("users", "username", "'" +
                        usernamePassword.getKey() + "' and password = '" + usernamePassword.getValue() + "'"));
                ResultSet adminLogin = Database.getResultSet();
                if(adminLogin.next()) {
                    if(adminLogin.getString("groupId").equals("1")) {
                        loginflag = true;
                        adminLoginButton.setDisable(true);
                    } else {
                        Alert logErr = Database.showSimpleDialog(Alert.AlertType.ERROR, "Ошибка", null, "Пользователь не находится в группе \"Администраторы\" ", true);
                        Stage logErrStage = (Stage) logErr.getDialogPane().getScene().getWindow();
                        logErrStage.getIcons().add(new Image(Main.class.getResourceAsStream("views/images/error.png")));
                        logErr.showAndWait();
                    }
                } else {
                    Alert passErr = Database.showSimpleDialog(Alert.AlertType.ERROR, "Ошибка", null, "Неверное имя пользователя или пароль, попробуйте еще раз", true);
                    Stage passErrStage = (Stage) passErr.getDialogPane().getScene().getWindow();
                    passErrStage.getIcons().add(new Image(Main.class.getResourceAsStream("views/images/error.png")));
                    passErrStage.showAndWait();
                    loginflag = false;
                }
                adminLogin.close();
            } catch (SQLException e) {
                Database.throwingException(e);
            }
        });
    }

    public void aboutAction(ActionEvent actionEvent) {
        Alert info = new Alert(Alert.AlertType.INFORMATION);
        info.setTitle("Автономное тестирование студентов");
        info.setHeaderText("О программе");
        info.setContentText("Программа создана студентом 3-го курса " +
                "Экономико-технологического техникума Херсонского национального технического университета " +
                "Нырченко Константином Сергеевичем. \n" +
                "В программе использованы технологии JDBC, MySQL, JavaFX \n" +
                "Вебсайт: http://vk.com/ettkntu \n" +
                "Приятного использования \n" +
                "© Нырченко К.С 2016");
        info.showAndWait();
    }

    public void editQuestionAction(ActionEvent actionEvent) {
        TextInputDialog askDialog = new TextInputDialog();
        askDialog.setTitle("Выбор вопроса");
        askDialog.setHeaderText("Укажите номер вопроса");
        askDialog.setContentText("Пожалуйста, введите в поле номер вопроса, который вы хотите редактировать.");
        Optional<String> result = askDialog.showAndWait();
        if(result.isPresent()) {
            try {
                Database.setStatement();
                Database.setResultSet(Database.select("questions", "questionId", "'" + result.get() + "'"));
                ResultSet selectResult = Database.getResultSet();
                String editId = "";
                String questionText = "";
                if(selectResult.next()) {
                    editId = selectResult.getString(1);
                    questionText = selectResult.getString(2);
                } else {
                    Alert err = Database.showSimpleDialog(Alert.AlertType.ERROR, "Ошибка", "Ошибка базы данных",
                            "Вопрос, под указанным вами номером не существует, попытайтесь указать другой.", true);
                    Stage errStage = (Stage) err.getDialogPane().getScene().getWindow();
                    errStage.getIcons().add(new Image(Main.class.getResourceAsStream("views/images/error.png")));
                    errStage.showAndWait();
                    return;
                }
                selectResult.close();
                Database.setStatement();
                Database.setResultSet(Database.select("answers", "questionId", "'" + editId + "'"));
                ResultSet answers = Database.getResultSet();
                String answersId = "";
                String firstAnswer = "";
                String secondAnswer = "";
                String thirdAnswer = "";
                String fourthAnswer = "";
                String rightAnswer = "";
                if(answers.next()) {
                    answersId = answers.getString("answersId");
                    firstAnswer = answers.getString("firstAnswer");
                    secondAnswer = answers.getString("secondAnswer");
                    thirdAnswer = answers.getString("thirdAnswer");
                    fourthAnswer = answers.getString("fourthAnswer");
                    rightAnswer = answers.getString("rightAnswer");
                }
                Questions editQuestion = new Questions(editId,
                        questionText,
                        answersId,
                        firstAnswer,
                        secondAnswer,
                        thirdAnswer,
                        fourthAnswer,
                        rightAnswer);
                answers.close();
                boolean editClicked = main.editQuestions(editQuestion, editId);
                if(editClicked) {
                    Alert confirm = Database.showSimpleDialog(Alert.AlertType.INFORMATION, "Изменение данных", "Операция прошла успешно",
                            "Данные успешно изменены!", true);
                    Stage confirmStage = (Stage) confirm.getDialogPane().getScene().getWindow();
                    confirmStage.getIcons().add(new Image(Main.class.getResourceAsStream("views/images/confirm.png")));
                    confirmStage.showAndWait();
                }
            } catch (SQLException e) {
                Database.throwingException(e);
            }
        }
    }

    public void addQuestionAction(ActionEvent actionEvent) {
        Questions newQuestion = new Questions();
        boolean addClicked = main.newQuestion(newQuestion);
        if(addClicked) {
            Alert confirm = Database.showSimpleDialog(Alert.AlertType.INFORMATION, "Добавление данных", "Операция прошла успешно", "Данные успешно добавлены!", true);
            Stage confirmStage = (Stage) confirm.getDialogPane().getScene().getWindow();
            confirmStage.getIcons().add(new Image(Main.class.getResourceAsStream("views/images/confirm.png")));
            confirmStage.showAndWait();
        }
    }

    public void deleteQuestionAction(ActionEvent actionEvent) {
        List<Questions> questionsList = new ArrayList<>();
        try {
            Database.setStatement();
            Database.setResultSet(Database.select("questions", "", ""));
            ResultSet questionsResultSet = Database.getResultSet();
            while(questionsResultSet.next()) {
                Database.setStatement();
                Database.setResultSet(Database.select("answers", "questionId", questionsResultSet.getString(1)));
                ResultSet answersResultSet = Database.getResultSet();
                while(answersResultSet.next()) {
                    questionsList.add(new Questions(
                            questionsResultSet.getString("questionId"),
                            questionsResultSet.getString("questionText"),
                            answersResultSet.getString("answersId"),
                            answersResultSet.getString("firstAnswer"),
                            answersResultSet.getString("secondAnswer"),
                            answersResultSet.getString("thirdAnswer"),
                            answersResultSet.getString("fourthAnswer"),
                            answersResultSet.getString("rightAnswer")
                    ));
                }
                answersResultSet.close();
            }
            questionsResultSet.close();
            List<String> questionsTextList = new ArrayList<>();
            for(Questions questions : questionsList) {
                questionsTextList.add(questions.getQuestion());
            }
            ChoiceDialog<String> choiceDeleteQuestionsDialog = new ChoiceDialog<>("", questionsTextList);
            choiceDeleteQuestionsDialog.setTitle("Удаление");
            choiceDeleteQuestionsDialog.setHeaderText("Удаление вопроса из базы данных");
            choiceDeleteQuestionsDialog.setContentText("Выберите вопрос, который необходимо удалить из базы данных");
            Optional<String> result = choiceDeleteQuestionsDialog.showAndWait();
            result.ifPresent(text -> {
                for(Questions questions : questionsList) {
                    if(result.get().equals(questions.getQuestion())) {
                        try {
                            String questionQuery = Database.setPreparedStatement("DELETE", "questions", null, "questionId", "?");
                            PreparedStatement questionStatement = Database.getPreparedStatement(questionQuery, false);
                            questionStatement.setInt(1, Integer.parseInt(questions.getId()));
                            questionStatement.executeUpdate();
                            String answerQuery = Database.setPreparedStatement("DELETE", "answers", null, "answersId", "?");
                            PreparedStatement answerStatement = Database.getPreparedStatement(answerQuery, false);
                            answerStatement.setInt(1, Integer.parseInt(questions.getAnswerId()));
                            answerStatement.executeUpdate();
                            Alert confirm = Database.showSimpleDialog(Alert.AlertType.INFORMATION, "Удалено", "Успешно", "Удаление выбраного вопросо успешно выполнено", true);
                            Stage confirmStage = (Stage) confirm.getDialogPane().getScene().getWindow();
                            confirmStage.getIcons().add(new Image(Main.class.getResourceAsStream("views/images/confirm.png")));
                            confirmStage.showAndWait();
                        } catch (SQLException e) {
                            Database.throwingException(e);
                        }
                    }
                }
            });
        } catch (SQLException e) {
            Database.throwingException(e);
        }
        questionsList.clear();
    }

    public void adminExitAction(ActionEvent actionEvent) {
        loginflag = false;
        adminLoginButton.setDisable(false);
    }

    private boolean checkLogin() {
        return loginflag;
    }

    public void menuValidationAction(Event event) {
        if(!checkLogin()) {
            editQuestionButton.setDisable(true);
            addQuestionButton.setDisable(true);
            deleteQuestionButton.setDisable(true);
            adminExitButton.setDisable(true);
        } else {
            editQuestionButton.setDisable(false);
            addQuestionButton.setDisable(false);
            deleteQuestionButton.setDisable(false);
            adminExitButton.setDisable(false);
        }
    }
}
