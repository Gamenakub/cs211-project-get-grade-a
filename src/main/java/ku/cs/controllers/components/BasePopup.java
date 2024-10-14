package ku.cs.controllers.components;


import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import ku.cs.controllers.components.tables.EventCallback;
import ku.cs.controllers.components.tables.EventCompatible;
import ku.cs.services.AlertService;
import ku.cs.services.popup.PopupHistory;
import ku.cs.services.popup.PopupHistoryList;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public abstract class BasePopup<T> implements EventCompatible {
    private Object model;

    private HashMap<String, ArrayList<EventCallback>> eventListeners = new HashMap<>();
    private String popupTitle;
    private String popupFxmlPath;
    private Stage stage;
    private PopupHistoryList historyList = new PopupHistoryList();

    public BasePopup() {
        this.model = null;
    }

    public final void setEventListeners(HashMap<String, ArrayList<EventCallback>> eventListeners) {
        this.eventListeners = eventListeners;
    }

    public final void setPopupTitle(String popupTitle) {
        this.popupTitle = popupTitle;
    }

    public final void setPopupFxmlPath(String popupFxmlPath) {
        this.popupFxmlPath = popupFxmlPath;
    }

    public final boolean hasModel() {
        return model != null;
    }

    public final T getModel() {
        if (!hasModel()) {
            throw new NullPointerException("Trying to get model but model is not set");
        }
        return (T) model;
    }

    public final void setModel(Object model) {
        this.model = model;
    }

    public abstract void onPopupOpen();

    public final void setHistoryList(PopupHistoryList historyList) {
        this.historyList = historyList;
    }

    public final void changeScene(Object popupModel, String fxmlPath, String title) {
        if (hasModel()) {
            issueEvent("change-page", getModel());
        } else {
            issueEvent("change-page");
        }
        AnchorPane popupAnchorPane = null;
        FXMLLoader myPopUp = new FXMLLoader(getClass().getResource(fxmlPath));
        historyList.addHistory(new PopupHistory(popupFxmlPath, popupTitle));
        BasePopup<T> popupController;
        try {
            popupAnchorPane = myPopUp.load();
        } catch (IOException e) {
            AlertService.showError("เกิดข้อผิดพลาดในขณะอ่านไฟล์โปรแกรม กรุณาตรวจสอบความสมบูรณ์ของไฟล์โปรแกรมของท่าน");
        }

        popupController = myPopUp.getController();
        popupController.setStage(stage);
        popupController.setModel(popupModel);

        popupController.setEventListeners(eventListeners);

        popupController.setHistoryList(historyList);
        popupController.setPopupTitle(title);
        popupController.setPopupFxmlPath(fxmlPath);


        this.setModel(popupModel);
        popupController.onPopupOpen();
        this.stage.setScene(new Scene(popupAnchorPane));

        this.stage.centerOnScreen();
        this.stage.show();
    }

    protected final void changeScene(Object popupModel, String fxmlPath) {
        changeScene(popupModel, fxmlPath, "FormXpress");
    }

    protected final void changeScene(String fxmlPath, String title) {
        changeScene(null, fxmlPath, title);
    }

    public final void setStage(Stage stage) {
        this.stage = stage;

        this.stage.setOnCloseRequest(event -> {
            if (hasModel()) {
                issueEvent("close", getModel());
            } else {
                issueEvent("close");
            }
        });
    }

    protected final void back() {
        PopupHistory prevPopup = historyList.pop();
        if (hasModel()) {
            changeScene(getModel(), prevPopup.getFxmlPath(), prevPopup.getTitle());
        } else {
            changeScene(prevPopup.getFxmlPath(), prevPopup.getTitle());
        }
        historyList.pop();
    }

    protected final void back(Object model) {
        PopupHistory prevPopup = historyList.pop();
        if (hasModel()) {
            changeScene(model, prevPopup.getFxmlPath(), prevPopup.getTitle());
        } else {
            changeScene(prevPopup.getFxmlPath(), prevPopup.getTitle());
        }
        historyList.pop();
    }

    protected final void close() {
        if (hasModel()) {
            issueEvent("close", getModel());
        } else {
            issueEvent("close");
        }
        stage.close();
    }

    @Override
    public final void issueEvent(String eventName, Object eventData) {
        if (eventListeners.containsKey(eventName)) {
            for (EventCallback callback : eventListeners.get(eventName)) {
                callback.onEvent(eventData);
            }
        }
    }

    @Override
    public final void issueEvent(String eventName) {
        if (eventListeners.containsKey(eventName)) {
            for (EventCallback callback : eventListeners.get(eventName)) {
                callback.onEvent(null);
            }
        }
    }

    @Override
    public final void addEventListener(String eventName, EventCallback callback) {
        if (!eventListeners.containsKey(eventName)) {
            eventListeners.put(eventName, new ArrayList<>());
        }
        eventListeners.get(eventName).add(callback);
    }
}
