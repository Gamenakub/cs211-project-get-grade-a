package ku.cs.controllers.components;

import javafx.event.ActionEvent;
import ku.cs.services.FXRouter;

import java.io.IOException;

public class DepartmentOfficerNavigatorBarController {
    public void onRequestManagementButton(ActionEvent actionEvent) throws IOException {
        FXRouter.goTo("department-officer-request-management");
    }

    public void onRequestManagerSet(ActionEvent actionEvent) throws IOException {
        FXRouter.goTo("department-officer-request-approver-management");
    }

    public void onNisitSet(ActionEvent actionEvent) throws IOException {
        FXRouter.goTo("department-officer-student-management");
    }
}
