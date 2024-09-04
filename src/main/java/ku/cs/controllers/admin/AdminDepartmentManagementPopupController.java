package ku.cs.controllers.admin;

import javafx.fxml.FXML;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import ku.cs.controllers.components.BasePopup;

public class AdminDepartmentManagementPopupController extends BasePopup<Object> {
    @FXML private TextField departmentNameTextField;
    @FXML private MenuButton facultyMenuButton;
    @FXML private TextField departmentIdTextField;
    @FXML private AnchorPane anchorPane;

    public void initialize() {
        anchorPane.getStylesheets().add(getClass().getResource("/ku/cs/views/styles/main-style.css").toString());
    }

    @FXML
    public void onCancelButton(){
        this.close();
    }

    @FXML
    public void onConfirmButton(){
        this.close();
    }
}
