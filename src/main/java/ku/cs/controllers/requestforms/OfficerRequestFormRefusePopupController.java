package ku.cs.controllers.requestforms;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import ku.cs.controllers.components.BasePopup;
import ku.cs.models.FormDataModel;

public class OfficerRequestFormRefusePopupController extends BasePopup<FormDataModel> {
    @FXML
    private AnchorPane anchorPane;

    @FXML
    public void initialize() {
        anchorPane.getStylesheets().add(getClass().getResource("/ku/cs/views/styles/main-style.css").toString());
    }

    @Override
    public void onPopupOpen() {
        super.onPopupOpen();
        anchorPane.getStylesheets().add(getClass().getResource("/ku/cs/views/styles/main-style.css").toString());
    }

    public void onConfirmationPage(ActionEvent actionEvent) {
        close();
    }

    public void onButtonBackPage(ActionEvent actionEvent) {
        back();
    }
}
