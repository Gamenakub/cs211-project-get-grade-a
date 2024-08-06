package ku.cs.controllers.requestforms;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import ku.cs.controllers.components.BasePopup;
import ku.cs.models.FormDataModel;

public class OfficerRequestFormAcceptPopupController extends BasePopup<FormDataModel> {
    @FXML
    private Label requestAcceptLabel;

    @Override
    public void onPopupOpen() {
        super.onPopupOpen();
        requestAcceptLabel.setText(getModel().getAcceptMessage());
    }

    public void onConfirmationPage(ActionEvent actionEvent) {
        close();
    }
}
