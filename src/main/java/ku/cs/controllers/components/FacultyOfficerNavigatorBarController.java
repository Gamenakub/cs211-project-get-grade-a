package ku.cs.controllers.components;

import javafx.event.ActionEvent;
import ku.cs.services.FXRouter;

import java.io.IOException;

public class FacultyOfficerNavigatorBarController {


    public void onRequestManagementButton(ActionEvent actionEvent) throws IOException {
        FXRouter.goTo("faculty-officer-request-management");
    }

    public void onRequestManagerSet(ActionEvent actionEvent) throws IOException {
        FXRouter.goTo("faculty-officer-request-approver-management");
    }
}
