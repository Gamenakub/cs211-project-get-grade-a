package ku.cs.controllers.officer.department;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import ku.cs.controllers.components.BasePopup;

public class DepartmentOfficerStudentModifyPopupController extends BasePopup<Object> {
    @FXML
    private AnchorPane anchorPane;

    @FXML
    public void initialize() {
        anchorPane.getStylesheets().add(getClass().getResource("/ku/cs/views/styles/main-style.css").toString());
    }
    public void onCancel(ActionEvent actionEvent) {
        close();
    }

    public void onConfirm(ActionEvent actionEvent) {
        close();
    }
}
