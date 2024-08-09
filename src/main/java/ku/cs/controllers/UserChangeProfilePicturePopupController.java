package ku.cs.controllers;

import javafx.fxml.FXML;
import ku.cs.controllers.components.BasePopup;

public class UserChangeProfilePicturePopupController extends BasePopup<Object> {
    @FXML
    public void onSaveButton() {
        this.close();
    }
}
