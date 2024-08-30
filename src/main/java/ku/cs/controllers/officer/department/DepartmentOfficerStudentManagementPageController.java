package ku.cs.controllers.officer.department;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import ku.cs.controllers.components.TableComponentController;
import ku.cs.controllers.components.TableRowController;
import ku.cs.services.PopupComponent;

import java.io.IOException;

public class DepartmentOfficerStudentManagementPageController {
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
        anchorPane.getStylesheets().add(getClass().getResource("/ku/cs/views/styles/main-style.css").toString());
        navBarPane.getStylesheets().add(getClass().getResource("/ku/cs/views/styles/main-style.css").toString());
        tablePane.getStylesheets().add(getClass().getResource("/ku/cs/views/styles/main-style.css").toString());
        FXMLLoader navBarFxmlLoader = new FXMLLoader(getClass().getResource("/ku/cs/views/components/department-officer-navbar.fxml"));
        try {
            AnchorPane adminNavbar = navBarFxmlLoader.load();
            navBarPane.getChildren().clear();
            navBarPane.getChildren().add(adminNavbar);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        tablePane.getChildren().clear();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ku/cs/views/components/table-component.fxml"));
        try {
            AnchorPane table = fxmlLoader.load();
            TableComponentController tableController = fxmlLoader.getController();
            tableController.setHeadHeight(40);
            tableController.setRowHeight(60);
            tableController.setDisplayRowCount(5);
            // สร้างหัว Table
            tableController.addTableHead(new Label("โปรไฟล์"),80);
            tableController.addTableHead(new Label("ชื่อ"),200);
            tableController.addTableHead(new Label("ชื่อผู้ใช้"),160);
            tableController.addTableHead(new Label("รหัสนิสิต"),140);
            tableController.addTableHead(new Label("อีเมล"),100);
            tableController.addTableHead(new Label("อาจารย์ที่ปรึกษา"),100);
            tableController.addTableHead(new Label(""),130);


            for (int i=0;i<20;i++) {
                // โหลด tableRowFXML มา
                FXMLLoader tableRowFXMLLoader = new FXMLLoader(getClass().getResource("/ku/cs/views/components/table-row-component.fxml"));
                AnchorPane tableRowComponent = tableRowFXMLLoader.load();

                TableRowController tableRowController = tableRowFXMLLoader.getController();

                // สร้างแต่ละ Object ใน Column ไม่ต้องกังวลเรื่องขนาด เดี๋ยว table จัดให้ตรงกับ Head เอง
                Circle profile = new Circle();
                profile.setRadius(20);

                Label name = new Label("ฐาพล ชินกรสกุล");
                Label username = new Label("username");
                Label startPassword = new Label("6610401993");
                Label faculty = new Label("thaphon.c@ku.th");
                Label department = new Label("ชาคริด วัชโรภาช");
                Button modifyButton = new Button("แก้ไข");

                modifyButton.setOnAction(actionEvent -> {
                    PopupComponent<Object> editPopup = new PopupComponent<>(new Object(), "/ku/cs/views/officer/department/department-officer-student-modify-popup.fxml","modify",navBarPane.getScene().getWindow());
                    editPopup.show();
                });

                tableRowController.addElement(profile);
                tableRowController.addElement(name);
                tableRowController.addElement(username);
                tableRowController.addElement(startPassword);
                tableRowController.addElement(faculty);
                tableRowController.addElement(department);
                tableRowController.addElement(modifyButton);



                // เพิ่ม row ไปใน table
                tableController.addTableRowControllerAndComponent(tableRowController, tableRowComponent);
            }

            tablePane.getChildren().add(table);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void onAddNisit(ActionEvent actionEvent) {
        PopupComponent<Object> editPopup = new PopupComponent<>(new Object(), "/ku/cs/views/officer/department/department-officer-student-create-popup.fxml","modify",navBarPane.getScene().getWindow());
        editPopup.show();
    }

    public void onSearchButtonClick(ActionEvent actionEvent) {
    }
}
