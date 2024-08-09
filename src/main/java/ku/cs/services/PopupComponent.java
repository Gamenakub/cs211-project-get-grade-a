package ku.cs.services;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import ku.cs.controllers.components.BasePopup;

import java.io.IOException;

public class PopupComponent<T> {
    private String fxmlPath;
    private String popupTitle;
    private BasePopup<T> popupController;
    private AnchorPane popupAnchorPane;
    private Window popupRoot;
    private static Stage stage = new Stage();

    public PopupComponent(T popupModel, String fxmlPath, String title, Window popupRoot) {
        this.fxmlPath = fxmlPath;

        FXMLLoader myPopUp = new FXMLLoader(getClass().getResource(fxmlPath));

        try {
            popupAnchorPane = myPopUp.load();
            popupController = myPopUp.getController();
            popupController.setModel(popupModel);
            popupController.setPopupTitle(title);
            popupController.setPopupFxmlPath(fxmlPath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        this.popupTitle = title;
        this.popupRoot = popupRoot;
    }

    public T getPopupModel() {
        return popupController.getModel();
    }

    public void show(){
        stage.setScene(new Scene(popupAnchorPane));
        stage.setTitle(popupTitle);

        if (!stage.getModality().equals(Modality.WINDOW_MODAL)) {
            stage.initModality(Modality.WINDOW_MODAL);
        }

        if (stage.getOwner() == null) {
            stage.initOwner(popupRoot);
        }


        popupController.onPopupOpen();
        popupController.setStage(stage);
        popupController.setPopupRoot(popupRoot);
        stage.show();
    }

    public void close(){

    }

    public BasePopup<T> getPopupController() {
        return popupController;
    }



}
