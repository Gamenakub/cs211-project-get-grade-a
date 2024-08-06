package ku.cs.controllers.admin;

import javafx.fxml.FXML;
import ku.cs.controllers.components.BasePopup;

public class AdminFacultyOfficerManagementPopupController extends BasePopup<Object> {
    @FXML
    public void onCancelButton(){
        this.close();
    }

    @FXML
    public void onConfirmButton(){
        this.close();
    }
}
