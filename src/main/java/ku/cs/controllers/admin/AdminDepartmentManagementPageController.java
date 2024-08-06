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

public class AdminDepartmentManagementPageController {
    @FXML
    private Pane navBarPane;

    @FXML
    private Pane tablePane;

    @FXML
    private Circle searchButton;

    @FXML
    public void initialize() {
        navBarPane.getChildren().clear();
        FXMLLoader navBarFxmlLoader = new FXMLLoader(getClass().getResource("/ku/cs/views/components/admin-navbar.fxml"));
        try {
            AnchorPane adminNavbar = navBarFxmlLoader.load();
            navBarPane.getChildren().add(adminNavbar);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Image searchIcon = new Image(getClass().getResource("/images/search-icon.png").toString(), false);
        searchButton.setFill(new ImagePattern(searchIcon));

        tablePane.getChildren().clear();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ku/cs/views/components/table-component.fxml"));
        try {
            AnchorPane table = fxmlLoader.load();
            TableComponentController tableController = fxmlLoader.getController();
            tableController.setHeadHeight(40);
            tableController.setRowHeight(40);
            tableController.setDisplayRowCount(8);
            // สร้างหัว Table
            tableController.addTableHead(new Label("ชื่อภาควิชา"),200);
            tableController.addTableHead(new Label("คณะที่สังกัด"),200);
            tableController.addTableHead(new Label("รหัสของภาควิชา"),100);
            tableController.addTableHead(new Label(""),100);


            for (int i=0;i<20;i++) {
                // โหลด tableRowFXML มา
                FXMLLoader tableRowFXMLLoader = new FXMLLoader(getClass().getResource("/ku/cs/views/components/table-row-component.fxml"));
                AnchorPane tableRowComponent = tableRowFXMLLoader.load();

                TableRowController tableRowController = tableRowFXMLLoader.getController();

                // สร้างแต่ละ Object ใน Column ไม่ต้องกังวลเรื่องขนาด เดี๋ยว table จัดให้ตรงกับ Head เอง
                Label department = new Label("วิทยาการคอมพิวเตอร์");
                Label faculty = new Label("วิทยาศาสตร์");
                Label advisorId = new Label("D14");
                Button editButton = new Button("แก้ไขข้อมูล");

                editButton.setOnAction(actionEvent -> {
                    PopupComponent<Object> requestActionPopup = new PopupComponent<>(new Object(), "/ku/cs/views/admin/admin-department-management-popup.fxml","admin-department-management-popup",(tablePane.getScene().getWindow()));
                    requestActionPopup.show();
                });

                tableRowController.addElement(department);
                tableRowController.addElement(faculty);
                tableRowController.addElement(advisorId);
                tableRowController.addElement(editButton);

                // เพิ่ม row ไปใน table
                tableController.addTableRowControllerAndComponent(tableRowController, tableRowComponent);
            }

            tablePane.getChildren().add(table);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void onAddDepartmentButton() {
        PopupComponent<Object> popup = new PopupComponent<>(new Object(), "/ku/cs/views/admin/admin-department-management-popup.fxml","admin-department-management-popup",navBarPane.getScene().getWindow());
        popup.show();
    }
}
