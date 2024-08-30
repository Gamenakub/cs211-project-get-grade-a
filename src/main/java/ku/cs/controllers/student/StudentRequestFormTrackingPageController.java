package ku.cs.controllers.student;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import ku.cs.controllers.components.TableComponentController;
import ku.cs.controllers.components.TableRowController;
import ku.cs.services.PopupComponent;

import java.io.IOException;

public class StudentRequestFormTrackingPageController {
    @FXML private AnchorPane anchorPane;
    @FXML private Pane tablePane;
    @FXML private Pane navBarPane;
    @FXML private TextField textFieldSearchBar;
    @FXML private Circle searchButton;


    @FXML public void initialize() throws IOException {
        anchorPane.getStylesheets().add(getClass().getResource("/ku/cs/views/styles/main-style.css").toString());
        navBarPane.getChildren().clear();
        FXMLLoader navBarFxmlLoader = new FXMLLoader(getClass().getResource("/ku/cs/views/components/student-navbar.fxml"));
        try {
            AnchorPane studentNavbar = navBarFxmlLoader.load();
            navBarPane.getChildren().add(studentNavbar);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Image searchIcon = new Image(getClass().getResource("/images/search-icon.png").toString());
        searchButton.setFill(new ImagePattern(searchIcon));

        tablePane.getStylesheets().add(getClass().getResource("/ku/cs/views/styles/main-style.css").toString());
        tablePane.getChildren().clear();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ku/cs/views/components/table-component.fxml"));
        try {
            AnchorPane table = fxmlLoader.load();
            TableComponentController tableController = fxmlLoader.getController();
            tableController.setHeadHeight(80);
            tableController.setRowHeight(60);
            tableController.setDisplayRowCount(5);
            // สร้างหัว Table
            tableController.addTableHead(new Label("เลขที่ใบคำร้อง"),150);
            tableController.addTableHead(new Label("รหัสนิสิต/คณะ"),150);
            tableController.addTableHead(new Label("หัวข้อเรื่อง"),150);
            tableController.addTableHead(new Label("แก้ไขล่าสุดเมื่อ"),150);
            tableController.addTableHead(new Label("สถานะ"),150);
            tableController.addTableHead(new Label(""),150);


            for (int i=0;i<20;i++) {
                // โหลด tableRowFXML มา
                FXMLLoader tableRowFXMLLoader = new FXMLLoader(getClass().getResource("/ku/cs/views/components/table-row-component.fxml"));
                AnchorPane tableRowComponent = tableRowFXMLLoader.load();

                TableRowController tableRowController = tableRowFXMLLoader.getController();

                // สร้างแต่ละ Object ใน Column ไม่ต้องกังวลเรื่องขนาด เดี๋ยว table จัดให้ตรงกับ Head เอง
                Label requestFormId = new Label("กขค-"+i);
                Label studentIdNFaculty = new Label("6610405832\n" + "วิทยาศาสตร์");
                Label requestFormTitle = new Label("ใบคำร้องลงทะเบียนล่าช่า");
                Label date = new Label("28/8/2567");
                Label stage = new Label("ใบคำร้องใหม่\nคำร้องส่งต่อให้\nอาจารย์ที่ปรึกษา");
                Button detail = new Button("รายละเอียด");
                detail.getStyleClass().add("DetailButton");
                detail.setOnAction(event -> {
                    PopupComponent<Object> popup = new PopupComponent<>(new Object(), "/ku/cs/views/request-forms/student-late-enroll-request-form-popup-page1.fxml","absence",((Node)event.getSource()).getScene().getWindow());
                    popup.show(); // รอทำการสงค่า reference ไปใน Popup
                });

                tableRowController.addElement(requestFormId);
                tableRowController.addElement(studentIdNFaculty);
                tableRowController.addElement(requestFormTitle);
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
