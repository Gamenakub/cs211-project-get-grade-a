package ku.cs.services.popup;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import ku.cs.controllers.components.BasePopup;
import ku.cs.controllers.components.tables.EventCallback;
import ku.cs.controllers.components.tables.EventCompatible;
import java.io.IOException;

public class PopupComponent<T> implements EventCompatible {
    private final String popupTitle;
    private final Window popupRoot;
    private final Stage stage;
    private final BasePopup<T> popupController;
    private final AnchorPane popupAnchorPane;

    public PopupComponent(T popupModel, String fxmlPath, String title, Window popupRoot) throws IOException {
        FXMLLoader myPopUp = new FXMLLoader(getClass().getResource(fxmlPath));
        stage = new Stage();
        popupAnchorPane = myPopUp.load();
        popupController = myPopUp.getController();
        popupController.setModel(popupModel);
        popupController.setPopupTitle(title);
        popupController.setPopupFxmlPath(fxmlPath);

        this.popupTitle = title;
        this.popupRoot = popupRoot;
    }

    public PopupComponent(T popupModel, String fxmlPath, Window popupRoot) throws IOException {
        this(popupModel, fxmlPath, "FormXpress", popupRoot);
    }


    public PopupComponent(String fxmlPath, String title, Window popupRoot) throws IOException {
        this(null, fxmlPath, title, popupRoot);
    }

    public PopupComponent(String fxmlPath, Window popupRoot) throws IOException {
        this(null, fxmlPath, "FormXpress", popupRoot);
    }

    public void show() {
        stage.setScene(new Scene(popupAnchorPane));
        stage.getIcons().add(new Image(PopupComponent.class.getResourceAsStream("/images/logo.png")));
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
        stage.show();
    }

    public BasePopup<T> getPopupController() {
        return popupController;
    }


    public void onEvent(String name, EventCallback callback) {
        popupController.addEventListener(name, callback);
    }

    @Override
    public void issueEvent(String eventName, Object eventData) {
        popupController.issueEvent(eventName, eventData);
    }

    @Override
    public void issueEvent(String eventName) {
        popupController.issueEvent(eventName);
    }

    @Override
    public void addEventListener(String eventName, EventCallback eventListener) {
        popupController.addEventListener(eventName, eventListener);
    }
}
