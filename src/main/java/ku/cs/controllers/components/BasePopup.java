package ku.cs.controllers.components;


import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.Window;
import ku.cs.controllers.components.tables.EventCallback;
import ku.cs.controllers.components.tables.EventCompatible;
import ku.cs.services.popup.PopupCallback;
import ku.cs.services.popup.PopupHistory;
import ku.cs.services.popup.PopupHistoryList;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class BasePopup<T> implements EventCompatible {
    private Object model;
    private PopupCallback callback;

    private HashMap<String, ArrayList<EventCallback>> eventListeners = new HashMap<>();

    public void setEventListeners(HashMap<String, ArrayList<EventCallback>> eventListeners) {
        this.eventListeners = eventListeners;
    }

    public String getPopupTitle() {
        return popupTitle;
    }

    public void setPopupTitle(String popupTitle) {
        this.popupTitle = popupTitle;
    }

    public String getPopupFxmlPath() {
        return popupFxmlPath;
    }

    public void setPopupFxmlPath(String popupFxmlPath) {
        this.popupFxmlPath = popupFxmlPath;
    }

    private String popupTitle;
    private String popupFxmlPath;
    private Stage stage;
    private Window popupRoot;
    private PopupHistoryList historyList = new PopupHistoryList();

    public void setModel(Object model){
        this.model = model;
    }
    public T getModel(){
        return (T)model;
    }


    public void onPopupOpen() {

    }

    public void setHistoryList(PopupHistoryList historyList) {
        this.historyList = historyList;
    }

    public void changeScene(Object popupModel, String fxmlPath, String title){
        callback.onEvent("change-page",getModel());
        AnchorPane popupAnchorPane;
        FXMLLoader myPopUp = new FXMLLoader(getClass().getResource(fxmlPath));
        historyList.addHistory(new PopupHistory(popupFxmlPath,popupTitle));
        BasePopup<T> popupController;
        try{
            popupAnchorPane = myPopUp.load();
        }
        catch (IOException e) {
            throw new RuntimeException("error on loading popup: "+e);
        }

        popupController = myPopUp.getController();
        popupController.setStage(stage);
        popupController.setModel(popupModel);
        popupController.setPopupCallback(callback);

        popupController.setEventListeners(eventListeners);

        popupController.setHistoryList(historyList);
        popupController.setPopupTitle(title);
        popupController.setPopupFxmlPath(fxmlPath);


        this.setModel(popupModel);
        popupController.onPopupOpen();
        //Stage oldStage = this.stage;
        //this.stage = new Stage();
        this.stage.setScene(new Scene(popupAnchorPane));
        this.stage.centerOnScreen();
//        this.stage.setOnCloseRequest(event -> {
//            if (callback != null) {
//                callback.onEvent("close", null);
//            }
//        });


        //this.stage.initOwner(popupRoot);
        //oldStage.close();
        //System.out.println("Old: " + oldStage);
        this.stage.show();

    }

    public void setStage(Stage stage) {
        this.stage = stage;
        this.stage.setOnCloseRequest(event -> {
            if (callback != null) {
                callback.onEvent("close", getModel());
                issueEvent("close", getModel());
            }
        });
    }

    public void back(){
        PopupHistory prevPopup = historyList.pop();
        changeScene(getModel(),prevPopup.getFxmlPath(),prevPopup.getTitle());
    }

    public void close(){
        callback.onEvent("close",getModel());
        issueEvent("close", getModel());
        stage.close();
    }

    public void setPopupRoot(Window popupRoot) {
        this.popupRoot = popupRoot;
    }

    public void setPopupCallback(PopupCallback callback) {
        this.callback = callback;
    }

    @Override
    public void issueEvent(String eventName, Object eventData) {
        if (eventListeners.containsKey(eventName)) {
            for (EventCallback callback : eventListeners.get(eventName)) {
                callback.onEvent(eventData);
            }
        }
    }

    @Override
    public void issueEvent(String eventName) {
        if (eventListeners.containsKey(eventName)) {
            for (EventCallback callback : eventListeners.get(eventName)) {
                callback.onEvent(null);
            }
        }
    }

    @Override
    public void addEventListener(String eventName, EventCallback callback) {
        if (!eventListeners.containsKey(eventName)) {
            eventListeners.put(eventName, new ArrayList<>());
        }
        eventListeners.get(eventName).add(callback);
    }
}
