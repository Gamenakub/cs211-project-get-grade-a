package ku.cs.controllers.admin;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import ku.cs.controllers.components.TableComponentController;
import ku.cs.controllers.components.TableRowController;
import ku.cs.services.PopupComponent;

import java.io.IOException;

public class AdminUserManagementPageController {
    @FXML private Pane navBarPane;
    @FXML private Pane tablePane;
    @FXML private Circle searchButton;
    @FXML private AnchorPane anchorPane;

    @FXML
    public void initialize() {
        anchorPane.getStylesheets().add(getClass().getResource("/ku/cs/views/styles/main-style.css").toString());
        navBarPane.getChildren().clear();
        navBarPane.getStylesheets().add(getClass().getResource("/ku/cs/views/styles/main-style.css").toString());
        FXMLLoader navBarFxmlLoader = new FXMLLoader(getClass().getResource("/ku/cs/views/components/admin-navbar.fxml"));
        try {
            AnchorPane adminNavbar = navBarFxmlLoader.load();
            navBarPane.getChildren().add(adminNavbar);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Image searchIcon = new Image(getClass().getResource("/images/search-icon.png").toString());
        searchButton.setFill(new ImagePattern(searchIcon));

        tablePane.getChildren().clear();
        tablePane.getStylesheets().add(getClass().getResource("/ku/cs/views/styles/main-style.css").toString());
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ku/cs/views/components/table-component.fxml"));
        try {
            AnchorPane table = fxmlLoader.load();
            TableComponentController tableController = fxmlLoader.getController();
            tableController.setHeadHeight(40);
            tableController.setRowHeight(60);
            tableController.setDisplayRowCount(5);
            // สร้างหัว Table
            tableController.addTableHead(new Label("โปรไฟล์"),80);
            tableController.addTableHead(new Label("ชื่อผู้ใช้"),120);
            tableController.addTableHead(new Label("ชื่อ"),180);
            tableController.addTableHead(new Label("บทบาท"),140);
            tableController.addTableHead(new Label("วันเวลาที่ใช้งาน"),160);
            tableController.addTableHead(new Label("สถานะ"),120);
            tableController.addTableHead(new Label(""),100);


            for (int i=0;i<20;i++) {
                // โหลด tableRowFXML มา
                FXMLLoader tableRowFXMLLoader = new FXMLLoader(getClass().getResource("/ku/cs/views/components/table-row-component.fxml"));
                AnchorPane tableRowComponent = tableRowFXMLLoader.load();

                TableRowController tableRowController = tableRowFXMLLoader.getController();

                // สร้างแต่ละ Object ใน Column ไม่ต้องกังวลเรื่องขนาด เดี๋ยว table จัดให้ตรงกับ Head เอง
                Circle profile = new Circle();
                profile.setRadius(20);

                Label username = new Label("fscijirat");
                Label name = new Label("จิรัฏฐ์ ค่องสกุล");
                Label role = new Label("อาจารย์ที่ปรึกษา");
                Label date = new Label("12/12/2512 15:10:52");
                Label status = new Label("ปกติ");
                Button permissionManagementButton = new Button("จัดการสิทธิ์");

                permissionManagementButton.setOnAction(actionEvent -> {
                    PopupComponent<Object> requestActionPopup = new PopupComponent<>(new Object(), "/ku/cs/views/admin/admin-user-management-popup.fxml","admin-user-management-popup",(tablePane.getScene().getWindow()));
                    requestActionPopup.show();
                });

                tableRowController.addElement(profile);
                tableRowController.addElement(username);
                tableRowController.addElement(name);
                tableRowController.addElement(role);
                tableRowController.addElement(date);
                tableRowController.addElement(status);
                tableRowController.addElement(permissionManagementButton);


                // เพิ่ม row ไปใน table
                tableController.addTableRowControllerAndComponent(tableRowController, tableRowComponent);
            }

            tablePane.getChildren().add(table);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
