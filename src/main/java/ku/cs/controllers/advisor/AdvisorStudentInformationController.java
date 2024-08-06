package ku.cs.controllers.advisor;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import ku.cs.controllers.components.TableComponentController;
import ku.cs.controllers.components.TableRowController;
import ku.cs.services.FXRouter;

import java.io.IOException;

public class AdvisorStudentInformationController {
    @FXML
    private Pane tablePane;
    @FXML
    private Pane navBarPane;
    @FXML
    public void initialize() {
        navBarPane.getChildren().clear();
        FXMLLoader navBarFxmlLoader = new FXMLLoader(getClass().getResource("/ku/cs/views/components/advisor-navbar.fxml"));
        try {
            AnchorPane advisorNavBar = navBarFxmlLoader.load();
            navBarPane.getChildren().add(advisorNavBar);
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
            tableController.setDisplayRowCount(4);
            // สร้างหัว Table
            tableController.addTableHead(new Label("โปรไฟล์"),150);
            tableController.addTableHead(new Label("ชื่อ"),300);
            tableController.addTableHead(new Label("รหัสนิสิต"),200);
            tableController.addTableHead(new Label(""),200);



            for (int i=0;i<20;i++) {
                // โหลด tableRowFXML มา
                FXMLLoader tableRowFXMLLoader = new FXMLLoader(getClass().getResource("/ku/cs/views/components/table-row-component.fxml"));
                AnchorPane tableRowComponent = tableRowFXMLLoader.load();

                TableRowController tableRowController = tableRowFXMLLoader.getController();

                Circle profile = new Circle(20);
                Label name = new Label("นาย อนุสรณ์ศาสน์ ธีร์");
                Label studentId = new Label("6610401993\n" + "คณะวิทยาศาสตร์");
                Button action = new Button("ดูประวัติ");
                action.setOnAction(event -> {
                    try {
                        FXRouter.goTo("advisor-student-request-form-history");
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });

                tableRowController.addElement(profile);
                tableRowController.addElement(name);
                tableRowController.addElement(studentId);
                tableRowController.addElement(action);


                tableController.addTableRowControllerAndComponent(tableRowController, tableRowComponent);
            }

            tablePane.getChildren().add(table);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
