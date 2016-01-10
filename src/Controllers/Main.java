package Controllers;

import Controllers.views.menu.MenuController;
import Controllers.views.testing.TestingController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
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
