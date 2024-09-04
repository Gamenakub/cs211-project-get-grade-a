package ku.cs.controllers.advisor;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import ku.cs.controllers.components.TableComponentController;
import ku.cs.controllers.components.TableRowController;
import ku.cs.services.PopupComponent;

import java.io.IOException;

public class AdvisorRequestFormController {
    @FXML
    private Pane tablePane;
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private Pane navBarPane;
    @FXML
    private TextField searchTextField;

    @FXML
    public void initialize() {
        navBarPane.getChildren().clear();
        tablePane.getChildren().clear();
        anchorPane.getStylesheets().add(getClass().getResource("/ku/cs/views/styles/main-style.css").toString());
        navBarPane.getStylesheets().add(getClass().getResource("/ku/cs/views/styles/main-style.css").toString());
        tablePane.getStylesheets().add(getClass().getResource("/ku/cs/views/styles/main-style.css").toString());

        FXMLLoader navBarFxmlLoader = new FXMLLoader(getClass().getResource("/ku/cs/views/components/advisor-navbar.fxml"));
        try {
            AnchorPane advisorNavBar = navBarFxmlLoader.load();
            navBarPane.getChildren().add(advisorNavBar);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ku/cs/views/components/table-component.fxml"));
        try {
            AnchorPane table = fxmlLoader.load();
            TableComponentController tableController = fxmlLoader.getController();
            tableController.setHeadHeight(80);
            tableController.setRowHeight(60);
            tableController.setDisplayRowCount(4);
            // สร้างหัว Table
            tableController.addTableHead(new Label("เลขที่ใบคำร้อง"),150);
            tableController.addTableHead(new Label("รหัสนิสิต/คณะ"),150);
            tableController.addTableHead(new Label("หัวข้อเรื่อง"),150);
            tableController.addTableHead(new Label("แก้ไขล่าสุด"),150);
            tableController.addTableHead(new Label("สถานะ"),150);
            tableController.addTableHead(new Label(""),150);


            for (int i=0;i<20;i++) {
                // โหลด tableRowFXML มา
                FXMLLoader tableRowFXMLLoader = new FXMLLoader(getClass().getResource("/ku/cs/views/components/table-row-component.fxml"));
                AnchorPane tableRowComponent = tableRowFXMLLoader.load();

                TableRowController tableRowController = tableRowFXMLLoader.getController();

                // สร้างแต่ละ Object ใน Column ไม่ต้องกังวลเรื่องขนาด เดี๋ยว table จัดให้ตรงกับ Head เอง
                Label number = new Label("กข-123");
                Label studentId = new Label("6610401993\n" + "คณะวิทยาศาสตร์");
                Label topic = new Label("ขอลาออก");
                Label latestModify = new Label("12/12/2566");
                Label status = new Label("ใบคำร้องใหม่\nคำร้องส่งต่อให้\nอาจารย์ที่ปรึกษา");
                Button action = new Button("ดำเนินการ");
                action.setOnAction(event -> {
                    PopupComponent<Object> popup = new PopupComponent<>(new Object(), "/ku/cs/views/request-forms/advisor-request-form-operation-popup.fxml","absence",((Node)event.getSource()).getScene().getWindow());
                    popup.show();
                });
                tableRowController.addElement(number);
                tableRowController.addElement(studentId);
                tableRowController.addElement(topic);
                tableRowController.addElement(latestModify);
                tableRowController.addElement(status);
                tableRowController.addElement(action);



                // เพิ่ม row ไปใน table
                tableController.addTableRowControllerAndComponent(tableRowController, tableRowComponent);
            }

            tablePane.getChildren().add(table);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void onOldToNewFilterClick(){}

    @FXML
    void onNewToOldFilterClick(){}





}

