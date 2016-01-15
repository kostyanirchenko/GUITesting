package Controllers.views.edit;

import Models.Questions;
import javafx.stage.Stage;

/**
 * Created by Kinndzadza on 15.01.2016.
 */
public class EditController {
    private String editId;
    private boolean editClick = false;

    public void setEditStage(Stage editStage) {
    }

    public void setQuestions(Questions questions) {
    }

    public void setEditId(String editId) {
        this.editId = editId;
    }

    public boolean isEditClicked() {
        return editClick;
    }
}
