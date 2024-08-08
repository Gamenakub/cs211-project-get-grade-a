package ku.cs.controllers.student;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import ku.cs.controllers.components.TableComponentController;
import ku.cs.controllers.components.TableRowController;
import ku.cs.services.PopupComponent;

import java.io.IOException;

public class StudentRequestFormTrackingPageController {
    @FXML private Pane tablePane;
    @FXML private Pane navBarPane;


    @FXML public void initialize() throws IOException {
        navBarPane.getChildren().clear();
        FXMLLoader navBarFxmlLoader = new FXMLLoader(getClass().getResource("/ku/cs/views/components/student-navbar.fxml"));
        try {
            AnchorPane nisitNavbar = navBarFxmlLoader.load();
            navBarPane.getChildren().add(nisitNavbar);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        tablePane.getChildren().clear();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ku/cs/views/components/table-component.fxml"));
        try {
            AnchorPane table = fxmlLoader.load();
            TableComponentController tableController = fxmlLoader.getController();
            tableController.setHeadHeight(80);
            tableController.setRowHeight(60);
            tableController.setDisplayRowCount(5);
            // สร้างหัว Table
            tableController.addTableHead(new Label(""),130);
            tableController.addTableHead(new Label("เลขที่ใบคำร้อง"),130);
            tableController.addTableHead(new Label("รหัสนิสิต/คณะ"),130);
            tableController.addTableHead(new Label("หัวข้อเรื่อง"),130);
            tableController.addTableHead(new Label("แก้ไขล่าสุดเมื่อ"),130);
            tableController.addTableHead(new Label("สถานะ"),130);
            tableController.addTableHead(new Label(""),130);


            for (int i=0;i<20;i++) {
                // โหลด tableRowFXML มา
                FXMLLoader tableRowFXMLLoader = new FXMLLoader(getClass().getResource("/ku/cs/views/components/table-row-component.fxml"));
                AnchorPane tableRowComponent = tableRowFXMLLoader.load();

                TableRowController tableRowController = tableRowFXMLLoader.getController();

                // สร้างแต่ละ Object ใน Column ไม่ต้องกังวลเรื่องขนาด เดี๋ยว table จัดให้ตรงกับ Head เอง
                Circle profile = new Circle();
                profile.setRadius(20);

                Label username = new Label("กขค-123");
                Label name = new Label("6610405832\n" + "วิทยาศาสตร์");
                Label role = new Label("ขอลงทะเบียนเรียนล่าช้า");
                Label date = new Label("12/12/2512");
                Label stage = new Label("ใบคำร้องใหม่\nคำร้องส่งต่อให้\nอาจารย์ที่ปรึกษา");
                Button detail = new Button("รายละเอียด");
                detail.setOnAction(event -> {
                    PopupComponent<Object> popup = new PopupComponent<>(new Object(), "/ku/cs/views/request-forms/student-late-enroll-request-form-popup-page1.fxml","absence",((Node)event.getSource()).getScene().getWindow());
                    popup.show();
                });

                tableRowController.addElement(profile);
                tableRowController.addElement(username);
                tableRowController.addElement(name);
                tableRowController.addElement(role);
                tableRowController.addElement(date);
                tableRowController.addElement(stage);
                tableRowController.addElement(detail);


                // เพิ่ม row ไปใน table
                tableController.addTableRowControllerAndComponent(tableRowController, tableRowComponent);
            }

            tablePane.getChildren().add(table);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML public void onCreateFormButton(){
        PopupComponent<Object> popup = new PopupComponent<>(new Object(), "/ku/cs/views/request-forms/student-create-request-form-popup.fxml","create form",navBarPane.getScene().getWindow());
        popup.show();
    }
}
