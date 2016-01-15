package Controllers;

import Controllers.views.edit.EditController;
import Controllers.views.newQuestion.NewController;
import Controllers.views.menu.MenuController;
import Controllers.views.testing.TestingController;
import Models.Database;
import Models.Questions;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Created by Kostya Nirchenko.
 *
 * @since 10.01.2016
 */
public class Main extends Application {

    private Stage primaryStage;
    private BorderPane rootLayout;

    public Main() {

    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Database.setConnection();
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Тестирование");
        initRootLayout();
        showTesting();
    }

    public void initRootLayout() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("views/menu/Menu.fxml"));
            rootLayout = (BorderPane) loader.load();
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            MenuController menuController = loader.getController();
            menuController.setMain(this);
            primaryStage.show();
        } catch (IOException e) {
            Database.throwingException(e);
        }
    }

    public void showTesting() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("views/testing/Testing.fxml"));
            AnchorPane testing = (AnchorPane) loader.load();
            rootLayout.setCenter(testing);
            TestingController testingController = loader.getController();
            testingController.setMain(this);
        } catch (IOException e) {
            Database.throwingException(e);
        }
    }

    public boolean newQuestion(Questions questions) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("views/newQuestion/new.fxml"));
            AnchorPane page = (AnchorPane) loader.load();
            Stage newStage = new Stage();
            newStage.setTitle("Добавление");
            newStage.initModality(Modality.WINDOW_MODAL);
            newStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            newStage.setScene(scene);
            NewController newController = loader.getController();
            newController.setNewStage(newStage);
            newController.setQuestions(questions);
            newStage.showAndWait();
            return newController.isOkClicked();
        } catch (IOException e) {
            Database.throwingException(e);
            return false;
        }
    }

    public boolean editQuestions(Questions questions, String editId) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("views/edit/edit.fxml"));
            AnchorPane pane = (AnchorPane) loader.load();
            Stage editStage = new Stage();
            editStage.setTitle("Редактирование");
            editStage.initModality(Modality.WINDOW_MODAL);
            editStage.initOwner(primaryStage);
            Scene scene = new Scene(pane);
            editStage.setScene(scene);
            EditController editController = loader.getController();
            editController.setEditStage(editStage);
            editController.setQuestions(questions);
            editController.setEditId(editId);
            editStage.showAndWait();
            return editController.isEditClicked();
        } catch (IOException e) {
            Database.throwingException(e);
            return false;
        }
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public void close() {
        primaryStage.close();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
