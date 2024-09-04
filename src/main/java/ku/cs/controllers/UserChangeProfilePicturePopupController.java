package ku.cs.controllers;

import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import ku.cs.controllers.components.BasePopup;

public class UserChangeProfilePicturePopupController extends BasePopup<Object> {
    @FXML private AnchorPane anchorPane;

    public void initialize() {
        anchorPane.getStylesheets().add(getClass().getResource("/ku/cs/views/styles/main-style.css").toString());
    }

    @FXML
    public void onSaveButton() {
        this.close();
    }
}
