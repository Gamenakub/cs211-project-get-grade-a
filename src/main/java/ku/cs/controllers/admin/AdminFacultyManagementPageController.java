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

public class AdminFacultyManagementPageController {
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

        Image searchIcon = new Image(getClass().getResource("/images/search-icon.png").toString(), false);
        searchButton.setFill(new ImagePattern(searchIcon));

        tablePane.getChildren().clear();
        tablePane.getStylesheets().add(getClass().getResource("/ku/cs/views/styles/main-style.css").toString());
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ku/cs/views/components/table-component.fxml"));
        try {
            AnchorPane table = fxmlLoader.load();
            TableComponentController tableController = fxmlLoader.getController();
            tableController.setHeadHeight(40);
            tableController.setRowHeight(40);
            tableController.setDisplayRowCount(8);
            // สร้างหัว Table
            tableController.addTableHead(new Label("ชื่อคณะ"),500);
            tableController.addTableHead(new Label("รหัสคณะ"),300);
            tableController.addTableHead(new Label(""),100);


            for (int i=0;i<20;i++) {
                // โหลด tableRowFXML มา
                FXMLLoader tableRowFXMLLoader = new FXMLLoader(getClass().getResource("/ku/cs/views/components/table-row-component.fxml"));
                AnchorPane tableRowComponent = tableRowFXMLLoader.load();

                TableRowController tableRowController = tableRowFXMLLoader.getController();

                // สร้างแต่ละ Object ใน Column ไม่ต้องกังวลเรื่องขนาด เดี๋ยว table จัดให้ตรงกับ Head เอง
                Label facultyName = new Label("วิทยาศาสตร์");
                Label facultyId = new Label("D");
                Button editButton = new Button("แก้ไขข้อมูล");

                editButton.setOnAction(actionEvent -> {
                    PopupComponent<Object> requestActionPopup = new PopupComponent<>(new Object(), "/ku/cs/views/admin/admin-faculty-management-popup.fxml","admin-faculty-management-popup",(tablePane.getScene().getWindow()));
                    requestActionPopup.show();
                });

                tableRowController.addElement(facultyName);
                tableRowController.addElement(facultyId);
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
    public void onAddFacultyButton() {
        PopupComponent<Object> popup = new PopupComponent<>(new Object(), "/ku/cs/views/admin/admin-faculty-management-popup.fxml","admin-faculty-management-popup",navBarPane.getScene().getWindow());
        popup.show();
    }
}
