package ku.cs.controllers.officer.department;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import ku.cs.controllers.components.TableComponentController;
import ku.cs.controllers.components.TableRowController;
import ku.cs.models.FormDataModel;
import ku.cs.services.PopupComponent;

import java.io.IOException;
import java.util.Objects;

public class DepartmentOfficerRequestManagementPageController {
    @FXML
    public TextField searchTextField;
    @FXML
    private Pane tablePane;
    @FXML
    private Pane navBarPane;
    @FXML
    private AnchorPane anchorPane;

    @FXML
    public void initialize() {
        navBarPane.getChildren().clear();
        navBarPane.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/ku/cs/views/styles/main-style.css")).toString());
        anchorPane.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/ku/cs/views/styles/main-style.css")).toString());
        tablePane.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/ku/cs/views/styles/main-style.css")).toString());

        FXMLLoader navBarFxmlLoader = new FXMLLoader(getClass().getResource("/ku/cs/views/components/department-officer-navbar.fxml"));
        try {
            AnchorPane adminNavbar = navBarFxmlLoader.load();
            navBarPane.getChildren().add(adminNavbar);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }




        tablePane.getChildren().clear();

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ku/cs/views/components/table-component.fxml"));
        try {
            AnchorPane table = fxmlLoader.load();
            TableComponentController tableController = fxmlLoader.getController();
            tableController.setHeadHeight(80);
            tableController.setRowHeight(50);
            tableController.setDisplayRowCount(5);
            // สร้างหัว Table
            tableController.addTableHead(new Label("เลขที่ใบคำร้อง"),150);
            tableController.addTableHead(new Label("รหัสนิสิต/คณะ"),150);
            tableController.addTableHead(new Label("หัวข้อเรื่อง"),150);
            tableController.addTableHead(new Label("แก้ไขล่าสุด"),150);
            tableController.addTableHead(new Label("สถานะ"),200);
            tableController.addTableHead(new Label(),110);


            for (int i=0;i<1;i++) {
                // โหลด tableRowFXML มา
                FXMLLoader tableRowFXMLLoader = new FXMLLoader(getClass().getResource("/ku/cs/views/components/table-row-component.fxml"));
                AnchorPane tableRowComponent = tableRowFXMLLoader.load();

                TableRowController tableRowController = tableRowFXMLLoader.getController();

                // สร้างแต่ละ Object ใน Column ไม่ต้องกังวลเรื่องขนาด เดี๋ยว table จัดให้ตรงกับ Head เอง
                Label requestNumber = new Label("กข-101");
                Label requestOwner = new Label("6610401993\nคณะวิทยาศาสตร์");
                Label topic = new Label("ขอเพิกถอนรายวิชาล่าช้า");
                Label date = new Label("10 ม.ค. 67");
                Label stage = new Label("อนุมัติโดยอาจารย์ที่ปรึกษา\nคำร้องส่งต่อให้หัวหน้าภาควิชา");
                Button actionButton = new Button("ดำเนินการ");

                actionButton.setOnAction(actionEvent -> {
                    PopupComponent<Object> requestActionPopup = new PopupComponent<>(new FormDataModel("Department","drop-late",requestOwner.getText(),topic.getText(),requestNumber.getText()), "/ku/cs/views/officer/officer-request-action-popup.fxml","request-action-popup",(tablePane.getScene().getWindow()));
                    requestActionPopup.show();
                });

                tableRowController.addElement(requestNumber);
                tableRowController.addElement(requestOwner);
                tableRowController.addElement(topic);
                tableRowController.addElement(date);
                tableRowController.addElement(stage);
                tableRowController.addElement(actionButton);


                // เพิ่ม row ไปใน table
                tableController.addTableRowControllerAndComponent(tableRowController, tableRowComponent);
            }

            for (int i=0;i<1;i++) {
                // โหลด tableRowFXML มา
                FXMLLoader tableRowFXMLLoader = new FXMLLoader(getClass().getResource("/ku/cs/views/components/table-row-component.fxml"));
                AnchorPane tableRowComponent = tableRowFXMLLoader.load();

                TableRowController tableRowController = tableRowFXMLLoader.getController();

                // สร้างแต่ละ Object ใน Column ไม่ต้องกังวลเรื่องขนาด เดี๋ยว table จัดให้ตรงกับ Head เอง
                Label requestNumber = new Label("กข-102");
                Label requestOwner = new Label("6610401993\nคณะวิทยาศาสตร์");
                Label topic = new Label("ขอเพิ่มรายวิชาล่าช้า");
                Label date = new Label("10 ม.ค. 67");
                Label stage = new Label("อนุมัติโดยอาจารย์ที่ปรึกษา\nคำร้องส่งต่อให้หัวหน้าภาควิชา");
                Button actionButton = new Button("ดำเนินการ");

                actionButton.setOnAction(actionEvent -> {
                    PopupComponent<Object> requestActionPopup = new PopupComponent<>(new FormDataModel("Department","add-late",requestOwner.getText(),topic.getText(),requestNumber.getText()), "/ku/cs/views/officer/officer-request-action-popup.fxml","request-action-popup",(tablePane.getScene().getWindow()));
                    requestActionPopup.show();
                });

                tableRowController.addElement(requestNumber);
                tableRowController.addElement(requestOwner);
                tableRowController.addElement(topic);
                tableRowController.addElement(date);
                tableRowController.addElement(stage);
                tableRowController.addElement(actionButton);


                // เพิ่ม row ไปใน table
                tableController.addTableRowControllerAndComponent(tableRowController, tableRowComponent);
            }

            for (int i=0;i<1;i++) {
                // โหลด tableRowFXML มา
                FXMLLoader tableRowFXMLLoader = new FXMLLoader(getClass().getResource("/ku/cs/views/components/table-row-component.fxml"));
                AnchorPane tableRowComponent = tableRowFXMLLoader.load();

                TableRowController tableRowController = tableRowFXMLLoader.getController();

                // สร้างแต่ละ Object ใน Column ไม่ต้องกังวลเรื่องขนาด เดี๋ยว table จัดให้ตรงกับ Head เอง
                Label requestNumber = new Label("กข-103");
                Label requestOwner = new Label("6610401993\nคณะวิทยาศาสตร์");
                Label topic = new Label("ขอลาเรียน");
                Label date = new Label("10 ม.ค. 67");
                Label stage = new Label("อนุมัติโดยอาจารย์ที่ปรึกษา\nคำร้องส่งต่อให้หัวหน้าภาควิชา");
                Button actionButton = new Button("ดำเนินการ");

                actionButton.setOnAction(actionEvent -> {
                    PopupComponent<Object> requestActionPopup = new PopupComponent<>(new FormDataModel("Department","absence",requestOwner.getText(),topic.getText(),requestNumber.getText()), "/ku/cs/views/officer/officer-request-action-popup.fxml","request-action-popup",(tablePane.getScene().getWindow()));
                    requestActionPopup.show();
                });

                tableRowController.addElement(requestNumber);
                tableRowController.addElement(requestOwner);
                tableRowController.addElement(topic);
                tableRowController.addElement(date);
                tableRowController.addElement(stage);
                tableRowController.addElement(actionButton);


                // เพิ่ม row ไปใน table
                tableController.addTableRowControllerAndComponent(tableRowController, tableRowComponent);
            }

            for (int i=0;i<20;i++) {
                // โหลด tableRowFXML มา
                FXMLLoader tableRowFXMLLoader = new FXMLLoader(getClass().getResource("/ku/cs/views/components/table-row-component.fxml"));
                AnchorPane tableRowComponent = tableRowFXMLLoader.load();

                TableRowController tableRowController = tableRowFXMLLoader.getController();

                // สร้างแต่ละ Object ใน Column ไม่ต้องกังวลเรื่องขนาด เดี๋ยว table จัดให้ตรงกับ Head เอง
                Label requestNumber = new Label("กข-104");
                Label requestOwner = new Label("6610401993\nคณะวิทยาศาสตร์");
                Label topic = new Label("ขอเพิ่มรายวิชา");
                Label date = new Label("10 ม.ค. 67");
                Label stage = new Label("อนุมัติโดยอาจารย์ที่ปรึกษา\nคำร้องส่งต่อให้หัวหน้าภาควิชา");
                Button actionButton = new Button("ดำเนินการ");

                actionButton.setOnAction(actionEvent -> {
                    PopupComponent<Object> requestActionPopup = new PopupComponent<>(new FormDataModel("Department","add-join",requestOwner.getText(),topic.getText(),requestNumber.getText()), "/ku/cs/views/officer/officer-request-action-popup.fxml","request-action-popup",(tablePane.getScene().getWindow()));
                    requestActionPopup.show();
                });

                tableRowController.addElement(requestNumber);
                tableRowController.addElement(requestOwner);
                tableRowController.addElement(topic);
                tableRowController.addElement(date);
                tableRowController.addElement(stage);
                tableRowController.addElement(actionButton);


                // เพิ่ม row ไปใน table
                tableController.addTableRowControllerAndComponent(tableRowController, tableRowComponent);
            }

            tablePane.getChildren().add(table);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void onDataSelectDropdownClick(ActionEvent actionEvent) {
    }

    public void onSearchButtonClick(ActionEvent actionEvent) {
    }
}
