package ku.cs.controllers.components;


import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.Window;
import ku.cs.services.PopupHistory;
import ku.cs.services.PopupHistoryList;

import java.io.IOException;

public class BasePopup<T> {
    private T model;

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

    public void setModel(T model){
        this.model = model;
    }
    public T getModel(){
        return model;
    }


    public void onPopupOpen() {

    }

    public void setHistoryList(PopupHistoryList historyList) {
        this.historyList = historyList;
    }

    public void changeScene(T popupModel, String fxmlPath, String title){
        AnchorPane popupAnchorPane;
        FXMLLoader myPopUp = new FXMLLoader(getClass().getResource(fxmlPath));
        historyList.addHistory(new PopupHistory(popupFxmlPath,popupTitle));
        try {
            popupAnchorPane = myPopUp.load();
            BasePopup<T> popupController = myPopUp.getController();
            popupController.setStage(stage);
            popupController.setModel(popupModel);
            popupController.setHistoryList(historyList);
            popupController.setPopupTitle(title);
            popupController.setPopupFxmlPath(fxmlPath);


            this.setModel(popupModel);

            popupController.onPopupOpen();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        //Stage oldStage = this.stage;
        //this.stage = new Stage();
        this.stage.setScene(new Scene(popupAnchorPane));
        this.stage.centerOnScreen();

        //this.stage.initOwner(popupRoot);
        //oldStage.close();
        //System.out.println("Old: " + oldStage);
        this.stage.show();

    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void back(){
        PopupHistory prevPopup = historyList.pop();
        changeScene(getModel(),prevPopup.getFxmlPath(),prevPopup.getTitle());
    }

    public void close(){
        stage.close();
    }

    public void setPopupRoot(Window popupRoot) {
        this.popupRoot = popupRoot;
    }
}
