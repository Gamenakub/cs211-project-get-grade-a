package ku.cs.controllers.requestforms;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import ku.cs.controllers.components.BasePopup;
import ku.cs.models.FormDataModel;

import java.util.Objects;

public class OfficerRequestFormDropLatePopupPage3Controller extends BasePopup<FormDataModel> {
    @FXML
    protected void onButtonToPage1() {
        changeScene(getModel(), "/ku/cs/views/request-forms/officer-form-drop-late-popup-page1.fxml","form");
    }
    @FXML protected void onButtonToPage2() {
        changeScene(getModel(), "/ku/cs/views/request-forms/officer-form-drop-late-popup-page2.fxml","form");
    }

    @FXML
    private AnchorPane anchorPane;

    @FXML
    public void initialize() {
        anchorPane.getStylesheets().add(getClass().getResource("/ku/cs/views/styles/main-style.css").toString());
    }

    public void onConfirmationPage(ActionEvent actionEvent) {
        if (Objects.equals(getModel().getFormHolder(), "Department")){
            changeScene(getModel(), "/ku/cs/views/request-forms/department-officer-form-confirmation-popup.fxml","form");
        }
        else{
            changeScene(getModel(), "/ku/cs/views/request-forms/faculty-officer-form-confirmation-popup.fxml","form");
        }
    }
}
