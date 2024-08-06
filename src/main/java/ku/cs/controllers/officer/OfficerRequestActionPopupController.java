package ku.cs.controllers.officer;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import ku.cs.controllers.components.BasePopup;
import ku.cs.models.FormDataModel;

import java.util.Objects;

public class OfficerRequestActionPopupController extends BasePopup<FormDataModel> {
    @FXML
    private Label topicLabel;
    @FXML
    private Label numberLabel;
    @FXML
    private Label nisitIdLabel;

    @Override
    public void onPopupOpen() {
        super.onPopupOpen();
        setNisitIdLabel(getModel().getNisitId());
        setTopicLabel(getModel().getTopic());
        setNumberLabel(getModel().getNumber());
    }

    public void onRequestDetailViewAndOperate(ActionEvent actionEvent) {
        //changeScene(getModel(), "/ku/cs/views/formCheckingPopup/officer-form-add-late-popup-page1.fxml","form");
        if (Objects.equals(getModel().getFormType(), "add-join")){
            changeScene(getModel(), "/ku/cs/views/request-forms/officer-form-add-join-popup-page1.fxml","form");
        }
        else if (Objects.equals(getModel().getFormType(), "add-late")){
            changeScene(getModel(), "/ku/cs/views/request-forms/officer-form-add-late-popup-page1.fxml","form");
        }
        else if (Objects.equals(getModel().getFormType(), "drop-late")){
            changeScene(getModel(), "/ku/cs/views/request-forms/officer-form-drop-late-popup-page1.fxml","form");
        }
        else if (Objects.equals(getModel().getFormType(), "absence")){
            changeScene(getModel(), "/ku/cs/views/request-forms/officer-form-absence-popup-page1.fxml","form");
        }
    }

    public void setNisitIdLabel(String nisitId) {
        nisitIdLabel.setText(String.format("รหัสนิสิต %s", nisitId));
    }
    public void setNumberLabel(String number) {
        numberLabel.setText(String.format("เลขที่ใบคำร้อง %s", number));
    }
    public void setTopicLabel(String topic) {
        topicLabel.setText(String.format("เรื่อง %s", topic));
    }

    public void onDownloadRequest(ActionEvent actionEvent) {
    }
}
