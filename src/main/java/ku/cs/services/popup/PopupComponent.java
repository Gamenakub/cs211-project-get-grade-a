package ku.cs.services.popup;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import ku.cs.controllers.components.BasePopup;
import ku.cs.controllers.components.tables.EventCallback;

import java.io.IOException;

public class PopupComponent<T> {
    private String fxmlPath;
    private String popupTitle;
    private BasePopup<T> popupController;
    private AnchorPane popupAnchorPane;
    private Window popupRoot;
    private Stage stage = new Stage();
    private PopupCallback callback;

    public PopupComponent(T popupModel, String fxmlPath, String title, Window popupRoot) {
        this.fxmlPath = fxmlPath;
        // default callback
        this.callback = (eventName, eventData) -> {return;};

        FXMLLoader myPopUp = new FXMLLoader(getClass().getResource(fxmlPath));

        try {
            popupAnchorPane = myPopUp.load();
            popupController = myPopUp.getController();
            popupController.setModel(popupModel);
            popupController.setPopupCallback(callback);
            popupController.setPopupTitle(title);
            popupController.setPopupFxmlPath(fxmlPath);
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
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
        stage.setResizable(false);

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
        callback.onEvent("close", null);
    }

    public BasePopup<T> getPopupController() {
        return popupController;
    }

    public void callEvent(PopupCallback callback){

    }

    public PopupCallback getCallback(){
        return callback;
    }

    @Deprecated
    public void onEvent(PopupCallback callback){
        popupController.setPopupCallback(callback);
    }
    public void onEvent(String name,EventCallback callback){
        popupController.addEventListener(name,callback);
    }
}
