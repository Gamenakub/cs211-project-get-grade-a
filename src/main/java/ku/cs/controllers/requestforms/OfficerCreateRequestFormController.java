package ku.cs.controllers.requestforms;

import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import ku.cs.services.FXRouter;

import java.io.IOException;

public class OfficerCreateRequestFormController {
    @FXML
    private AnchorPane anchorPane;

    @FXML
    public void initialize() {
        anchorPane.getStylesheets().add(getClass().getResource("/ku/cs/views/styles/main-style.css").toString());
    }


    @FXML
    protected void onCreateFormButton(){
        try {
            FXRouter.goTo("nisit");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
