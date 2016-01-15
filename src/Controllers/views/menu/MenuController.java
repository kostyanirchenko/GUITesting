package Controllers.views.menu;

import Controllers.Main;
import Models.Database;
import Models.Questions;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.util.Pair;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

/**
 * Created by Kostya Nirchenko on 10.01.2016.
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
        dialog.setGraphic(new ImageView(this.getClass().getResource("logout.png").toString()));
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
                        loginflag = false;
                    }
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
    }

    public void addQuestionAction(ActionEvent actionEvent) {
        Questions newQuestion = new Questions();
        boolean addClicked = main.newQuestion(newQuestion);
        if(addClicked) {

            Database.showSimpleDialog(Alert.AlertType.INFORMATION, "Добавление данных", "Операция прошла успешно", "Данные успешно добавлены!");
        }
    }

    public void deleteQuestionAction(ActionEvent actionEvent) {
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
