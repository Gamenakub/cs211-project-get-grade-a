package ku.cs.controllers.requestforms;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import ku.cs.controllers.components.BasePopup;
import ku.cs.models.FormDataModel;
import ku.cs.services.Session;

public class OfficerRequestFormAcceptPopupController extends BasePopup<FormDataModel> {
    @FXML private Label requestAcceptLabel;
    @FXML private AnchorPane anchorPane;

    @FXML
    public void initialize() {
        Session.getSession().getThemeProvider().setTheme(anchorPane);
    }

    @Override
    public void onPopupOpen() {
        Session.getSession().getThemeProvider().setTheme(anchorPane);
        requestAcceptLabel.setText(getModel().getAcceptMessage());
    }
    @FXML
    public void onConfirmationButton() {
        close();
    }
}
