package ku.cs.controllers.requestforms;

import javafx.fxml.FXML;
import ku.cs.services.FXRouter;

import java.io.IOException;

public class OfficerCreateRequestFormController {
    @FXML
    protected void onCreateFormButton(){
        try {
            FXRouter.goTo("nisit");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
