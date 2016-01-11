package Controllers.views.testing;

import Controllers.Main;
import Models.Database;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import org.controlsfx.dialog.Dialogs;

/**
 * Created by Kostya Nirchenko on 10.01.2016.
 */
public class TestingController {

    public Button testButton;
    public Button exitButton;
    private Main main;

    public void setMain(Main main) {
        this.main = main;
    }

    public void testAction(ActionEvent actionEvent) {
        Dialogs.create()
                .title("База данных персональной библиотеки")
                .masthead("О программе")
                .message("Программа создана студентом 3-го курса " +
                        "Экономико-технологического техникума Херсонского национального технического университета " +
                        "Нырченко Константином Сергеевичем как задание по учебной практике.\n" +
                        "В программе использованы технологии JDBC, MySQL, JavaFX \n" +
                        "Вебсайт: http://vk.com/ettkntu \n" +
                        "Приятного использования \n" +
                        "© Нырченко К.С 2015")
                .showInformation();
    }

    public void exitAction(ActionEvent actionEvent) {
        Database.closeConnection();
        Database.close();
        main.close();
    }
}
