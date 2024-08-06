package ku.cs.controllers.admin;

import javafx.fxml.FXML;
import ku.cs.controllers.components.BasePopup;

public class AdminUserManagementPopupController extends BasePopup<Object> {
    @FXML
    public void onBanButton(){
        this.close();
    }

    @FXML
    public void onUnbanButton(){
        this.close();
    }
}
