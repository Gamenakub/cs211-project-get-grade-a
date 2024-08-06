package ku.cs.controllers.components;

import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import ku.cs.services.FXRouter;
import ku.cs.services.PopupComponent;

import java.io.IOException;

public class StudentNavigatorBarController {
    @FXML private AnchorPane popupAnchorPane;


    @FXML public void onRequestFormTrackingButton(){
        try {
            FXRouter.goTo("student");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML public void onCreateRequestFormButton(){
        PopupComponent<Object> popup = new PopupComponent<>(new Object(), "/ku/cs/views/request-forms/student-create-request-form-popup.fxml","create form",popupAnchorPane.getScene().getWindow());
        popup.show();
    }

}
