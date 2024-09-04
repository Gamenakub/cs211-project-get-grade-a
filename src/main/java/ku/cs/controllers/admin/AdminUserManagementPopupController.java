package ku.cs.controllers.admin;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import ku.cs.controllers.components.BasePopup;

public class AdminUserManagementPopupController extends BasePopup<Object> {
    @FXML private Label usernameLabel;
    @FXML private Label nameSurnameLabel;
    @FXML private AnchorPane anchorPane;

    public void initialize() {
        anchorPane.getStylesheets().add(getClass().getResource("/ku/cs/views/styles/main-style.css").toString());
    }

    @FXML
    public void onBanButton(){
        this.close();
    }

    @FXML
    public void onUnbanButton(){
        this.close();
    }
}
