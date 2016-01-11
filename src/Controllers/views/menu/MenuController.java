package Controllers.views.menu;

import Controllers.Main;
import Models.Database;
import javafx.event.ActionEvent;
import org.controlsfx.dialog.Dialogs;

/**
 * Created by Kostya Nirchenko on 10.01.2016.
 */
public class MenuController {

    private Main main;

    public void setMain(Main main) {
        this.main = main;
    }

    public void exitAction(ActionEvent actionEvent) {
        Database.closeConnection();
        Database.close();
        main.close();
    }

    public void adminLoginAction(ActionEvent actionEvent) {
    }

    public void aboutAction(ActionEvent actionEvent) {
        Dialogs.create()
                .title("Автономное тестирование студентов")
                .masthead("О программе")
                .message("Программа создана студентом 3-го курса " +
                        "Экономико-технологического техникума Херсонского национального технического университета " +
                        "Нырченко Константином Сергеевичем.\n" +
                        "В программе использованы технологии JDBC, MySQL, JavaFX \n" +
                        "Вебсайт: http://vk.com/ettkntu \n" +
                        "Приятного использования \n" +
                        "© Нырченко К.С 2016")
                .showInformation();
    }
}
