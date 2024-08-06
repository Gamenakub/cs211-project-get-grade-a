package ku.cs.controllers;

import javafx.fxml.FXML;
import ku.cs.controllers.components.BasePopup;

public class UserChangePasswordPopupController extends BasePopup<Object> {
    @FXML
    public void onConfirmButton() {
        this.close();
    }
}
