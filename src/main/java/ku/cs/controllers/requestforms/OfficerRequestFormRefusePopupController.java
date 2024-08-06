package ku.cs.controllers.requestforms;

import javafx.event.ActionEvent;
import ku.cs.controllers.components.BasePopup;
import ku.cs.models.FormDataModel;

public class OfficerRequestFormRefusePopupController extends BasePopup<FormDataModel> {
    public void onConfirmationPage(ActionEvent actionEvent) {
        close();
    }

    public void onButtonBackPage(ActionEvent actionEvent) {
        back();
    }
}
