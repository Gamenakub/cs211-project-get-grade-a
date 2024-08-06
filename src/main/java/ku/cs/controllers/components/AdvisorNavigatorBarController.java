package ku.cs.controllers.components;

import javafx.fxml.FXML;
import ku.cs.services.FXRouter;

import java.io.IOException;

public class AdvisorNavigatorBarController {
    @FXML
    protected void onNisitListPageButtonClick() {
        try {
            FXRouter.goTo("advisor-student-information");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    protected void onFormManagePageButtonClick() {
        try {
            FXRouter.goTo("advisor-request-form");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
